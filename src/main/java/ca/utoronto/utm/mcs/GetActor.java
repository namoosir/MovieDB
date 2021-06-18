package ca.utoronto.utm.mcs;

import java.util.List;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Long;
import org.json.*;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Record;
import org.neo4j.driver.Session;
import org.neo4j.driver.Result;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.Transaction;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.time.Instant;

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
