package ca.utoronto.utm.mcs;

import org.json.*;
import com.sun.net.httpserver.HttpExchange;

/**
 * PUT /api/v1/addMovie
 * @throws JSONException
 * @param database instance of Neo4jDAO object storing the database
 * @param exchange HttpExchange object storing storing the request information
 * @return 
 *  - 200 OK: relationship added successfully
 *  - 400 BAD REQUEST: If actorId/movieId are improperly formatted
 *  - 500 INTERNAL SERVER ERROR: If an error occurred during a database operation
 */

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
