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

public class GetActor {

    private Neo4jDAO database;
    private HttpExchange exchange;
    
    public GetActor(Neo4jDAO database, HttpExchange exchange) {
        this.database = database;
        this.exchange = exchange;
    }

    public void handle() {
        try {
            if (exchange.getRequestMethod().equals("GET")) {
                handleGet();
            } else {
                sendStatusCode(404);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void handleGet() throws IOException, JSONException {
        String body;
        JSONObject jsonBody = null; 
        String actorName;
        String actorId;

        try {
            body = Utils.convert(exchange.getRequestBody());
            jsonBody = new JSONObject(body);
        } catch (Exception e) {
            sendStatusCode(400);
            return;
        }

        if (!(jsonBody.has("actorId"))){
            sendStatusCode(400);
            return;
        }

        actorId = jsonBody.getString("actorId");

        try{
            database.getActor(actorId);
        } catch(StatusException e){
            sendStatusCode(e.getStatus());
            return;
        }
    }
    

    private void sendStatusCode(int code) throws IOException {
        exchange.sendResponseHeaders(code, -1);
    }
}
