package ca.utoronto.utm.mcs;
import com.sun.net.httpserver.HttpServer;
import javax.inject.Inject;



public class Server {
    private HttpServer server;
    
    @Inject
    public Server(HttpServer server){
        this.server = server;
    }
    
    public HttpServer getServer(){
        return this.server;
    }
}
