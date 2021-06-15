package ca.utoronto.utm.mcs;

import javax.inject.Inject;
import java.io.IOException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


public class ReqHandler implements HttpHandler {

    // TODO Complete This Class
    private Neo4jDAO database;

    @Inject
    public ReqHandler(Neo4jDAO database){
        this.database = database;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        
    }
}