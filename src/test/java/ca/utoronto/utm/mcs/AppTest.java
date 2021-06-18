package ca.utoronto.utm.mcs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

// TODO Please Write Your Tests For CI/CD In This Class. You will see
// these tests pass/fail on github under github actions.
public class AppTest {
    private Server server;
    JSONObject jsonBody = new JSONObject();
    String urlParameters;

    public int setup(String urlParameters, URL urlInput) throws IOException{
        ServerComponent component = DaggerServerComponent.create();

        server = component.buildServer();
        
        server.getServer().start();
    
        ReqHandlerComponent component2 = DaggerReqHandlerComponent.create();
        ReqHandler handle = component2.buildHandler();
        server.getServer().createContext("/api/v1", handle);

        URL url = urlInput;

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Content-Length", Integer.toString(urlParameters.getBytes().length));
        // connection.setRequestProperty("Content-Language", "en-US");

        connection.setUseCaches(false);
        connection.setDoOutput(true);

        // Send request
        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.close();

/*         // Get Response
        InputStream is = connection.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
        String line;

        while ((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\r');
        }
        rd.close(); */
       
        return  connection.getResponseCode();

    }

    @Test
    public void addUser200Test() {
        int response = 0;
        // Create connection
        try{
            
            URL url = new URL("http://localhost:8080/api/v1/addActor");
            jsonBody.put("name", "mutasem");
            jsonBody.put("actorId", "15");
            urlParameters = jsonBody.toString();

            response = setup(urlParameters, url);

        }
        catch(Exception e){
            fail("Unexpected error");       
        }
        finally {
            assertEquals( 200, response);            
        }
    }

/*     @Test
    public void addUser400Test() {
        // Create connection
        try{


        }
        catch(Exception e){

        }
    } */

}
