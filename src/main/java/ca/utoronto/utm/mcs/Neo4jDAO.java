package ca.utoronto.utm.mcs;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Record;
import org.neo4j.driver.Session;
import org.neo4j.driver.Result;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.Transaction;
import static org.neo4j.driver.Values.parameters;

public class Neo4jDAO {
    // TODO Complete This Class
    private Driver driver;
    // private String uriUser;

    public Neo4jDAO(Driver driver) {
        this.driver = driver;
        // this.uriUser ="http://localhost:8080";
    }

    /*
     * public String geturiUser(){ return uriUser; }
     */

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

        if (!doesMovieIDExist(id, session)) {
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

        if(!actorIDExist(actorId, session) || !doesMovieIDExist(movieId, session)){
            session.close();
            throw new StatusException(404);
        }

        if (!doesRelationshipExist(actorId, movieId, session)) {
            session.run("MATCH (a:actor), (m:movie) WHERE a.id = $x AND m.id = $y CREATE (a)-[r:ACTED_IN]->(m);", 
            parameters("x", actorId, "y", movieId));
        } else{
            session.close();
            throw new StatusException(400);
        }
        session.close();   

    }

    public String getActor(String Id) throws StatusException {
        return null;
    }

    public String hasRelationship(String movieId, String actorId) throws StatusException {
        return null;
    }

    public String computeBaconNumber(String id) throws StatusException {
        return null;
    }

    public String computeBacomPath(String id) throws StatusException {
        return null;
    }

    private boolean actorIDExist(String id, Session session) {
        // Transaction tx = session.beginTransaction();
        // org.neo4j.driver.Result results = tx.run("MATCH (n) WHERE (n.id = $x) RETURN
        // (n)", parameters( "x", id)); */
        org.neo4j.driver.Result results = session.run("MATCH (n:actor) WHERE (n.id = $x) RETURN (n);",
                parameters("x", id));
        // tx.close();
        return !results.list().isEmpty();
    }

    private boolean doesMovieIDExist(String id, Session session) {

        org.neo4j.driver.Result results = session.run("MATCH (n:movie) WHERE (n.id = $x) RETURN (n);",
                parameters("x", id));
        return !results.list().isEmpty();
    }

    private boolean doesRelationshipExist(String actorId, String movieId, Session session) { //does not work
        
        org.neo4j.driver.Result results =
            session.run("MATCH (m:movie {id: $x})<-[:ACTED_IN]-(actor) WHERE (actor.id = $y); RETURN (actor);",
             parameters( "x",movieId, "y",actorId));
 
        return !results.list().isEmpty();
         
    }

    


}

/*
 * public static String uriDb = "bolt://localhost:7687"; public static String
 * uriUser ="http://localhost:8080"; public static Driver driver =
 * GraphDatabase.driver(uriDb, AuthTokens.basic("neo4j","1234"));
 */