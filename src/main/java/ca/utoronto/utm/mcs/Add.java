package ca.utoronto.utm.mcs;

import org.json.*;
import com.sun.net.httpserver.HttpExchange;

/**
 * PUT /api/v1/add{something}
    @throws JSONException
    @param database instance of Neo4jDAO object storing the database
    @param exchange instance of HttpExchange storing the request information
    @param field1 String to be parsed 
    @param field2 String to be parsed
    @return:
        List of strings for database use
        - 400 BAD REQUEST: If name/actorId are improperly formatted
 */ 
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