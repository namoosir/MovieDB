package ca.utoronto.utm.mcs;

import java.io.OutputStream;
import org.json.*;
import com.sun.net.httpserver.HttpExchange;

public class HasRelationship extends Get{

    static String[] field = {"actorId", "movieId"};
    String response;
    
    public HasRelationship(Neo4jDAO database, HttpExchange exchange)  {
        super(database, exchange, field);
    }

    public void handle() {
        try {
            super.getHandle();
            if(super.failed) return;
            handleGet();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void handleGet() throws JSONException {
        String input[];

        input = super.getHandleGet();
        
        if(super.failed) return;

        try{
            response = database.hasRelationship(input[0], input[1]);
        } catch(StatusException e){
            super.sendStatusCode(e.getStatus());
            return;
        }
        
        try{
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }catch(Exception e){
            super.sendStatusCode(400);
            return;
        }
        
    }
     
}
