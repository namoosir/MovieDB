package ca.utoronto.utm.mcs;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URI;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class ReqHandler implements HttpHandler {

    // TODO Complete This Class
    private Neo4jDAO database;

    @Inject
    public ReqHandler(Neo4jDAO database) {
        this.database = database;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
       URI path = exchange.getRequestURI();
      // System.out.println(path.toString()); //returns /api/v1/asdasdasd

       String[] baseName = path.toString().split("/");

       switch (baseName[baseName.length - 1]) {
           case "addActor":
               AddActor addActor = new AddActor(database, exchange);
               addActor.handle();
               break;
           case "addMovie":
               AddMovie addMovie = new AddMovie(database, exchange);
               addMovie.handle();
               break;
           case "addRelationship":
               AddRelationship addRelationship = new AddRelationship(database, exchange);
               addRelationship.handle();
               break;
           case "getActor":
               GetActor getActor = new GetActor(database, exchange);
               getActor.handle();
               break;
           case "hasRelationship":
               HasRelationship hasRelationship = new HasRelationship(database, exchange);
               hasRelationship.handle();
               break;
           case "computeBaconNumber":
               ComputeBaconNumber computeBaconNumber = new ComputeBaconNumber(database, exchange);
               computeBaconNumber.handle();
               break;
           case "computeBaconPath":
               ComputeBaconPath computeBaconPath = new ComputeBaconPath(database, exchange);
               computeBaconPath.handle();
               break;
           default:
               // code block
               // error
       }
    }
}