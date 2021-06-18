package ca.utoronto.utm.mcs;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URI;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.OutputStream;
import org.json.*;



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

/*        String response = "";
       JSONObject jsonBody = new JSONObject();  
       //exchange.sendResponseHeaders(200, -1);
       try{
        jsonBody.put("id", "123");
        response = jsonBody.toString();}
       catch (Exception e){
        System.out.printf("Server started on 1\n");
       }
       System.out.printf("Server started on 2 %s\n", response);
       try{
 
        System.out.printf("Server started on 3 %s\n", response);
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
        exchange.sendResponseHeaders(400, -1);
    }catch(Exception e){
        exchange.sendResponseHeaders(400, -1);
        return;
    } */

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
               // code block
               // error
       } 
    }
}