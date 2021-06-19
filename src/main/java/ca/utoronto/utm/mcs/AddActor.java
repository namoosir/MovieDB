package ca.utoronto.utm.mcs;

import org.json.*;
import com.sun.net.httpserver.HttpExchange;

//PUT /api/v1/addActor
//@param: name String, actorId string
//@return:
//- 200 OK: Actor added successfully
//- 400 BAD REQUEST: If name/actorId are improperly formatted
//- 500 INTERNAL SERVER ERROR: If an error occurred during a database operation
public class AddActor extends Add{

    public AddActor(Neo4jDAO database, HttpExchange exchange){
        super(database, exchange, "name", "actorId");
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
            database.addActor(input[0], input[1]);
        } catch(StatusException e){
            super.sendStatusCode(e.getStatus());
            return;
        }
        super.sendStatusCode(200);
    }
}
