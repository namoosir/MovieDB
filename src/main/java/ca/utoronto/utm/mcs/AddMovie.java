package ca.utoronto.utm.mcs;


import org.json.*;
import com.sun.net.httpserver.HttpExchange;

public class AddMovie extends Add{

    
    public AddMovie(Neo4jDAO database, HttpExchange exchange){
        super(database, exchange, "name", "movieId");
    }

    public void handle() {
        try {
            super.addHandle();
            if(super.failed) return;
            handlePut();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void handlePut() throws JSONException {
        String input[];

        input = super.addHandlePut();
        
        if(super.failed) return;

        try{
            database.addMovie(input[0], input[1]);
        } catch(StatusException e){
            super.sendStatusCode(e.getStatus());
            return;
        }
        super.sendStatusCode(200);
    }

}
