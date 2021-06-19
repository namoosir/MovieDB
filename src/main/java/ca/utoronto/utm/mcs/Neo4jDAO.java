package ca.utoronto.utm.mcs;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.neo4j.driver.Result;
import org.neo4j.driver.types.Path;
import org.neo4j.driver.types.Path.Segment;
import org.json.*;
import static org.neo4j.driver.Values.parameters;
import java.util.ArrayList;

public class Neo4jDAO {
    private Driver driver;

    public Neo4jDAO(Driver driver) {
        this.driver = driver;
    }

    public void addActor(String name, String id) throws StatusException {
        Session session = null;
        try {
            session = driver.session();
        } catch (Exception e) {
            throw new StatusException(500);
        }

        if (!actorIDExist(id, session)) {
            session.run("CREATE (a:actor {id:$x, Name:$y});", parameters("x", id, "y", name));
        } else {
            session.close();
            throw new StatusException(400);
        }
        session.close();
    }

    public void addMovie(String name, String id) throws StatusException {
        Session session = null;
        try {
            session = driver.session();
        } catch (Exception e) {
            throw new StatusException(500);
        }

        if (!movieIDExist(id, session)) {
            session.run("CREATE (a:movie {id:$x, Name:$y});", parameters("x", id, "y", name));
        } else {
            session.close();
            throw new StatusException(400);
        }
        session.close();
    }

    public void addRelationship(String actorId, String movieId) throws StatusException {

        Session session = null;
        try {
            session = driver.session();
        } catch (Exception e) {
            throw new StatusException(500);
        }

        if(!actorIDExist(actorId, session) || !movieIDExist(movieId, session)){
            session.close();
            throw new StatusException(404);
        }

        if (!relationshipExist(actorId, movieId, session)) {
            session.run("MATCH (a:actor), (m:movie) WHERE a.id = $x AND m.id = $y CREATE (a)-[r:ACTED_IN]->(m);", 
            parameters("x", actorId, "y", movieId));
        } else{
            session.close();
            throw new StatusException(400);
        }
        session.close();   

    }

    public String getActor(String id) throws StatusException {
        Session session = null;
        ArrayList<String> sMovies = new ArrayList<String>();
        JSONObject jsonBody = new JSONObject();

        try {
            session = driver.session();
        } catch (Exception e){
            throw new StatusException(200);
        }

        if(!actorIDExist(id, session))  {
            session.close();
            throw new StatusException(404);
        }

        Result actor = session.run("MATCH (n:actor) WHERE (n.id = $x) RETURN (n);",
                parameters("x", id));
        
        String sActorId = actor.next().get("n").get("id").asString();

        actor = session.run("MATCH (n:actor) WHERE (n.id = $x) RETURN (n);",
                parameters("x", id));

        String sActorName = actor.next().get("n").get("Name").asString();
        
        Result movies = session.run("MATCH (:actor {id: $x})-->(movie) RETURN movie.id;",
                parameters("x", id));

        try{
            while (movies.hasNext()) {
                sMovies.add(movies.next().get("movie.id").asString());                 
            }
            jsonBody.put("actorId", sActorId);
            jsonBody.put("name", sActorName);
            jsonBody.put("movies", sMovies);       
        } catch (Exception e){
            throw new StatusException(400);
        }
        
        session.close(); 
        return jsonBody.toString(); 
    }

    public String hasRelationship(String actorId, String movieId) throws StatusException {
        Session session = null;
        JSONObject jsonBody = new JSONObject(); 
        
        try {
            session = driver.session();
        } catch (Exception e) {
            throw new StatusException(500);
        }

        if(!actorIDExist(actorId, session) || !movieIDExist(movieId, session))  {
            session.close();
            throw new StatusException(404);
        }
          
        boolean hasRelationship = relationshipExist(actorId, movieId, session); 

        Result actor = session.run("MATCH (n:actor) WHERE (n.id = $x) RETURN (n);",
            parameters("x", actorId));

        Result movie = session.run("MATCH (n:movie) WHERE (n.id = $x) RETURN (n);",
            parameters("x", movieId));

        String sMovie = movie.next().get("n").get("id").asString();
        String sActor = actor.next().get("n").get("id").asString();
        
        try{
            jsonBody.put("actorId", sActor);
            jsonBody.put("movieId", sMovie);
            jsonBody.put("hasRelationship", hasRelationship);
        } catch (Exception e) {
            throw new StatusException(400);
        }

        return jsonBody.toString();            
    }

    public String computeBaconNumber(String id) throws StatusException {
        Session session = null;
        JSONObject jsonBody = new JSONObject();
        int num = 0;

        if (id == "nm0000102"){
            try {
                jsonBody.put("baconNumber", 0);
                return jsonBody.toString();
            } catch (Exception e) {
                throw new StatusException(400);
            }
        }

        try {
            session = driver.session();
        } catch (Exception e){
            throw new StatusException(200);
        }

        if(!actorIDExist(id, session))  {
            session.close();
            throw new StatusException(404);
        }
        
        Result result = session.run("MATCH p=shortestPath((bacon:actor {id:$y})-[*]-(n:actor {id:$x})) RETURN length(p)",
                parameters("x",id , "y", "nm0000102"));

        if (result.list().isEmpty()) throw new StatusException(404);

        result = session.run("MATCH p=shortestPath((bacon:actor {id:$y})-[*]-(n:actor {id:$x})) RETURN length(p)",
                parameters("x",id , "y", "nm0000102"));
        num = result.next().get("length(p)").asInt();
        try{
            jsonBody.put("baconNumber", num);
            
        } catch (Exception e){
            throw new StatusException(400);
        }

        session.close(); 
        return jsonBody.toString();
    }

    public String computeBaconPath(String id) throws StatusException {        
        Session session = null;
        ArrayList<String> path = new ArrayList<String>();
        JSONObject jsonBody = new JSONObject();

        if (id == "nm0000102"){
            try {
                jsonBody.put("baconPath", "nm0000102");
                return jsonBody.toString();
            } catch (Exception e) {
                throw new StatusException(400);
            }
        }   

        try {
            session = driver.session();
        } catch (Exception e){
            throw new StatusException(200);
        }

        if(!actorIDExist(id, session))  {
            session.close();
            throw new StatusException(404);
        }
        
        Result result = session.run("MATCH p=shortestPath((bacon:actor {id:$y})-[*]-(n:actor {id:$x})) RETURN p",
         parameters("x",id, "y", "nm0000102"));  
         
         
        if (result.list().isEmpty()) throw new StatusException(404);
        result = session.run("MATCH p=shortestPath((bacon:actor {id:$y})-[*]-(n:actor {id:$x})) RETURN p",
         parameters("x",id, "y", "nm0000102"));  
        Path a = result.next().get("p").asPath();

        Segment hi = null;
        for (Segment segment : a) {    
            path.add(0, segment.start().get("id").asString());  
            hi = segment;      
        }
        path.add(0, hi.end().get("id").asString());


         try{
            jsonBody.put("baconPath", path);     
        } catch (Exception e){
            System.out.printf("Server started on port2\n");

            throw new StatusException(400);
        }

        session.close(); 
        return jsonBody.toString(); 
    }

    private boolean actorIDExist(String id, Session session) {
        Result results = session.run("MATCH (n:actor) WHERE (n.id = $x) RETURN (n);",
                parameters("x", id));
        return !results.list().isEmpty();
    }

    private boolean movieIDExist(String id, Session session) {

        Result results = session.run("MATCH (n:movie) WHERE (n.id = $x) RETURN (n);",
                parameters("x", id));
        return !results.list().isEmpty();
    }

    private boolean relationshipExist(String actorId, String movieId, Session session) { 
         org.neo4j.driver.Result results =
            session.run("MATCH (m:movie {id: $x})<-[:ACTED_IN]-(actor) WHERE (actor.id = $y) RETURN (actor);",
             parameters( "x",movieId, "y",actorId));
 
        return !results.list().isEmpty();      
    }
}
