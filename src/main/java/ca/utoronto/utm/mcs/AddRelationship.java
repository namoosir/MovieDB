package ca.utoronto.utm.mcs;

import org.json.*;
import com.sun.net.httpserver.HttpExchange;

/**
 * PUT /api/v1/addRelationship
 * @throws JSONException
 * @param database instance of Neo4jDAO object storing the database
 * @param exchange HttpExchange object storing storing the request information
 * @return 
 *  - 200 OK: Movie added successfully
 *  - 400 BAD REQUEST: If name/movieId are improperly formatted
 *  - 404 NOT FOUND: If actor or movie does not exist when adding relationship
 *  - 500 INTERNAL SERVER ERROR: If an error occurred during a database operation
 */
public class AddRelationship extends Add{

    public AddRelationship(Neo4jDAO database, HttpExchange exchange){
        super(database, exchange, "actorId", "movieId");
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
            database.addRelationship(input[0], input[1]);
        } catch(StatusException e){
            super.sendStatusCode(e.getStatus());
            return;
        }
        super.sendStatusCode(200);
    }

}
