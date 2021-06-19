package ca.utoronto.utm.mcs;

import java.io.OutputStream;
import org.json.*;
import com.sun.net.httpserver.HttpExchange;


public class ComputeBaconNumber extends Get{

    static String[] fields = {"actorId"};
    String response = "";

    public ComputeBaconNumber(Neo4jDAO database, HttpExchange exchange) {
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
            response = database.computeBaconNumber(input[0]);
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
