package ca.utoronto.utm.mcs;

import ca.utoronto.utm.mcs.Exception400;
import ca.utoronto.utm.mcs.Exception404;
import ca.utoronto.utm.mcs.Exception500;

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


    }

    public void addMovie(String name, String id) throws StatusException {

    }

    public void addRelationship(String actorId, String movieId) throws StatusException {

    }
    // all the
    // doesActorIDExist
    // doesMovieIDExist
    // doesRelaExist

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
        return false;
    }

    private boolean doesMovieIDExist(String id, Session session) {

        return false;
    }

    private boolean doesRelationshipExist(String actorId, String movieId) {
        return false;
    }

}

/*
 * public static String uriDb = "bolt://localhost:7687"; public static String
 * uriUser ="http://localhost:8080"; public static Driver driver =
 * GraphDatabase.driver(uriDb, AuthTokens.basic("neo4j","1234"));
 */