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

public class HasRelationship {

    private Neo4jDAO database;
    private HttpExchange exchange;

    public HasRelationship(Neo4jDAO database, HttpExchange exchange) {
        this.database = database;
        this.exchange = exchange;
    }

    public void handle() {
        try {
            if (exchange.getRequestMethod().equals("PUT")) {
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
        String actorId;
        String movieId;

        try {
            body = Utils.convert(exchange.getRequestBody());
            jsonBody = new JSONObject(body);
        } catch (Exception e) {
            sendStatusCode(400);
            return;
        }

        if (!(jsonBody.has("actorId") || jsonBody.has("movieId"))) {
            sendStatusCode(400);
            return;
        }

        actorId = jsonBody.getString("actorId");
        movieId = jsonBody.getString("movieId");

        try {
            database.addRelationship(actorId, movieId);
        } catch (StatusException e) {
            sendStatusCode(e.getStatus());
            return;
        }

        sendStatusCode(200);
    }

    private void sendStatusCode(int code) throws IOException {
        exchange.sendResponseHeaders(code, -1);
    }    
}
