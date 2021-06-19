package ca.utoronto.utm.mcs;

import java.io.OutputStream;
import org.json.*;
import com.sun.net.httpserver.HttpExchange;

/**
 * PUT /api/v1/getActor
    @throws JSONException
    @param database instance of Neo4jDAO object storing the database
    @param exchange instance of HttpExchange storing the request information
    @return:
    - 200 OK: Actor added successfully
    - 400 BAD REQUEST: If name/actorId are improperly formatted
    - 500 INTERNAL SERVER ERROR: If an error occurred during a database operation  
 */ 
public class GetActor extends Get{

    static String[] fields = {"actorId"};
    String response;
    
    public GetActor(Neo4jDAO database, HttpExchange exchange)  {
        super(database, exchange, fields);
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
            response = database.getActor(input[0]);
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
