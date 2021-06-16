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
    public ReqHandler(Neo4jDAO database){
        this.database = database;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
       URI path = exchange.getRequestURI();
       System.out.println(path.toString()); //returns /api/v1/asdasdasd

       String[] baseName = path.toString().split("-");

       switch(baseName[baseName.length-1]) {
        case "/api/v1/addActor":
          // code block
          break;
        case "/api/v1/addMovie":
          // code block
          break;
        case "/api/v1/addRelationship":
          // code block
          break;
        case "/api/v1/getActor":
          // code block
          break;
        case "/api/v1/hasRelationship":
          // code block
          break;
        case "/api/v1/computeBaconNumber":
          // code block
          break;
        case "/api/v1/computeBaconPath":
          // code block
          break;    
        default:
          // code block
      }
    }
}