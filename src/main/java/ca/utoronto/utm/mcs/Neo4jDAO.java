package ca.utoronto.utm.mcs;

import ca.utoronto.utm.mcs.Exception400;
import ca.utoronto.utm.mcs.Exception404;
import ca.utoronto.utm.mcs.Exception500;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
 

// All your database transactions or queries should 
// go in this class
public class Neo4jDAO {
    // TODO Complete This Class
    private Driver driver;
    //private String uriUser;

    public Neo4jDAO(Driver driver){
        this.driver = driver;
        //this.uriUser ="http://localhost:8080";
    }

  /*   public String geturiUser(){
        return uriUser;
    } */
    
    public void addActor(String name, String id) throws Exception400, Exception500{

    }

    public void addMovie(String name, String id) throws Exception400, Exception500{
        
    }

    public void addRelationship(String actorId, String movieId) throws Exception400, Exception500, Exception404{
        
    }
    //all the 
    //doesActorIDExist
    //doesMovieIDExist
    //doesRelaExist

    public String getActor(String Id) {
        return null;
    }

    public String hasRelationship(String movieId, String actorId) {
        return null;
    }

    public String computeBaconNumber(String id) {
        return null;
    }

    public String computeBacomPath(String id) {
        return null;
    }

    private boolean doesActorIDExist(String id) {
        return false;
    }

    private boolean doesMovieIDExist(String id) {
        return false;
    }

    private boolean doesRelationshipExist(String actorId, String movieId) {
        return false;
    }

    

}

/*     public static String uriDb = "bolt://localhost:7687";
    public static String uriUser ="http://localhost:8080";
    public static Driver driver = GraphDatabase.driver(uriDb, AuthTokens.basic("neo4j","1234")); */