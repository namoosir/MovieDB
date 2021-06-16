package ca.utoronto.utm.mcs;

import dagger.Module;
import dagger.Provides;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.io.IOException;




@Module
public class ServerModule {
    // TODO Complete This Module

/*     private final int port;

    public ServerModule(int port) {
        this.port = port;
    } */

    @Provides
    public HttpServer provideServer(){
        try {
			return HttpServer.create(new InetSocketAddress("0.0.0.0", 8080), 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
    }
    
}
