package ca.utoronto.utm.mcs;

import java.util.ArrayList;
import org.json.*;
import com.sun.net.httpserver.HttpExchange;

/**
 * PUT /api/v1/get{something}
    @throws JSONException
    @param database instance of Neo4jDAO object storing the database
    @param exchange instance of HttpExchange storing the request information
    @param fields String array of fields to be parsed 
    @return:
        List of strings for database use
        - 400 BAD REQUEST: If name/actorId are improperly formatted
 */ 
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