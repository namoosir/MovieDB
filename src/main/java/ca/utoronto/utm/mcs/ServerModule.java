package ca.utoronto.utm.mcs;

import dagger.Module;
import dagger.Provides;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.io.IOException;

@Module
public class ServerModule {
    private HttpServer server;
    @Provides
    public HttpServer provideServer(){
        try {
			server = HttpServer.create(new InetSocketAddress("0.0.0.0", 8080), 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return server;
    }    
}
