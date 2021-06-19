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

public class Get {

    Neo4jDAO database;
    HttpExchange exchange;
    private String[] fields; //actorid, movieid
    public boolean failed = false;

    public Get(Neo4jDAO database, HttpExchange exchange, String[] fields) {
        this.database = database;
        this.exchange = exchange;
        this.fields = fields;
    }

    public void getHandle(){
        try {
            if (!exchange.getRequestMethod().equals("GET") && !exchange.getRequestMethod().equals("POST")) {
                sendStatusCode(400);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }  
    }

    String[] getHandleGet() throws JSONException{
        String body;
        ArrayList<String> s = new ArrayList<String>();
        JSONObject jsonBody = null; 

        try {
            body = Utils.convert(exchange.getRequestBody());
            jsonBody = new JSONObject(body);
        } catch (Exception e) {
            sendStatusCode(400);
            return null;
        }

        for (String field : fields) {
            if(!jsonBody.has(field)){
                sendStatusCode(400);
                return null;
            }
            s.add(jsonBody.getString(field));
        }
        
        String[] array = s.toArray(new String[s.size()]);
        return array;
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