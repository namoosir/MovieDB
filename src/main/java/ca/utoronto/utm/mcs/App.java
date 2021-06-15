package ca.utoronto.utm.mcs;

import java.io.IOException;

import com.sun.net.httpserver.HttpServer;

public class App
{
    static int port = 8080;

    public static void main(String[] args) throws IOException
    {
        // TODO Create Your Server Context Here, There Should Only Be One Context
        ServerComponent component = DaggerServerComponent.create();

        Server server = component.buildServer();
        server.getServer().start();
        ReqHandlerComponent component2 = DaggerReqHandlerComponent.create();


         /*  String uriDb = "bolt://localhost:7687";
          Driver driver = GraphDatabase.driver(uriDb, AuthTokens.basic("neo4j","1234"));

        Neo4jDAO database = new Neo4jDAO(driver); */
        //"http://localhost:8080";
        ReqHandler handle = component2.buildHandler();
        server.createContext("/", handle);

    	System.out.printf("Server started on port %d\n", port);
    }
}
//depenecy should be passed to the class that needs them (usually through constructor
//add @inject into classed want injected