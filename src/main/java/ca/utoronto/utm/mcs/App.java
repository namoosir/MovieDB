package ca.utoronto.utm.mcs;

import java.io.IOException;

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
       
        ReqHandler handle = component2.buildHandler();

        server.getServer().createContext("/api/v1", handle);

    	System.out.printf("Server started on port %d\n", port);
    }
}