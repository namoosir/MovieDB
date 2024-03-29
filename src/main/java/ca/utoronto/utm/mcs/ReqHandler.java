package ca.utoronto.utm.mcs;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URI;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


public class ReqHandler implements HttpHandler {

    public Neo4jDAO database;

    @Inject
    public ReqHandler(Neo4jDAO database) {
        this.database = database;
    }

    /**
     * Calls the correct handler for the API
    @throws IOException
    @param database instance of Neo4jDAO object storing the database
    */ 
    @Override
    public void handle(HttpExchange exchange) throws IOException {
       URI path = exchange.getRequestURI();

        switch (path.toString()) {
           case "/api/v1/addActor":
               AddActor addActor = new AddActor(database, exchange);
               addActor.handle();
               break;
           case "/api/v1/addMovie":
               AddMovie addMovie = new AddMovie(database, exchange);
               addMovie.handle();
               break;
           case "/api/v1/addRelationship":
               AddRelationship addRelationship = new AddRelationship(database, exchange);
               addRelationship.handle();
               break;
           case "/api/v1/getActor":
               GetActor getActor = new GetActor(database, exchange);
               getActor.handle();
               break;
           case "/api/v1/hasRelationship":
               HasRelationship hasRelationship = new HasRelationship(database, exchange);
               hasRelationship.handle();
               break;
           case "/api/v1/computeBaconNumber":
               ComputeBaconNumber computeBaconNumber = new ComputeBaconNumber(database, exchange);
               computeBaconNumber.handle();
               break;
           case "/api/v1/computeBaconPath":
               ComputeBaconPath computeBaconPath = new ComputeBaconPath(database, exchange);
               computeBaconPath.handle();
               break;
           default:
       } 
    }
}