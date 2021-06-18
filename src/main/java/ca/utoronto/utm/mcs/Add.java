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

public class Add {
    Neo4jDAO database;
    HttpExchange exchange;
    private String field1;
    private String field2;
    public boolean failed = false;

    public Add(Neo4jDAO database, HttpExchange exchange, String field1, String field2) {
        this.database = database;
        this.exchange = exchange;
        this.field1 = field1;
        this.field2 = field2;
    }
    
    public void addHandle(){
        try {
            if (!exchange.getRequestMethod().equals("PUT")) {
                sendStatusCode(400);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }  
    }

    String[] addHandlePut() throws JSONException{
        String body;
        JSONObject jsonBody = null; 

        try {
            body = Utils.convert(exchange.getRequestBody());
            jsonBody = new JSONObject(body);
        } catch (Exception e) {
            sendStatusCode(400);
            return null;
        }

        if (!(jsonBody.has(field1) && jsonBody.has(field2))){
            sendStatusCode(400);
            return null;
        }
        
        String[] s = {jsonBody.getString(field1), jsonBody.getString(field2)};
        return s;
    }

    void sendStatusCode(int code){
        failed = true;
        try {
            exchange.sendResponseHeaders(code, -1);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}