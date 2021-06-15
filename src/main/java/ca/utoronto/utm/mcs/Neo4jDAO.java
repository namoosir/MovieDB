package ca.utoronto.utm.mcs;

import org.neo4j.driver;
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
    private String uriUser;

    public Neo4jDAO(Driver driver){
        this.driver = driver;
        this.uriUser ="http://localhost:8080";
    }

    public String geturiUser(){
        return uriUser;
    }
    
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

    }

    public String hasRelationship(String movieId, String actorId) {

    }

    public String computeBaconNumber(String id) {

    }

    public String computeBacomPath(String id) {
        
    }

    private boolean doesActorIDExist(string id) {
        
    }

    private boolean doesMovieIDExist(string id) {

    }

    private boolean doesRelationshipExist(string actorId, string movieId) {

    }

    

}

/*     public static String uriDb = "bolt://localhost:7687";
    public static String uriUser ="http://localhost:8080";
    public static Driver driver = GraphDatabase.driver(uriDb, AuthTokens.basic("neo4j","1234")); */