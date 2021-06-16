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

public class ComputeBaconNumber  {

    private Neo4jDAO database;
    private HttpExchange exchange;

    public ComputeBaconNumber(Neo4jDAO database, HttpExchange exchange) {
        this.database = database;
        this.exchange = exchange;
    }

    public void handle() {
    }

    
}
