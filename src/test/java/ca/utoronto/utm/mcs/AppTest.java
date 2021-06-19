package ca.utoronto.utm.mcs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.net.HttpURLConnection;
import java.util.concurrent.TimeUnit;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;

// TODO Please Write Your Tests For CI/CD In This Class. You will see
// these tests pass/fail on github under github actions.
public class AppTest {
    private static Server server;
    String urlParameters;
    static Neo4jDAO database;

    @BeforeAll
    public static void initialSetup() {
        ReqHandler handle= null;

        ServerComponent c = DaggerServerComponent.create();
        server = c.buildServer();
        server.getServer().start();

        ReqHandlerComponent c2 = DaggerReqHandlerComponent.create();
        handle = c2.buildHandler();
        database = handle.database;

        
        server.getServer().createContext("/api/v1", handle);

        try {
            database.addActor("Kevin Bacon", "nm0000102");
        } catch (Exception e) {
            System.out.println("Unexpected error");
        }        
    }

    public int setup(String urlParameters, URL urlInput, String reqType) throws IOException{      

        URL url = urlInput;

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(reqType);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Content-Length", Integer.toString(urlParameters.getBytes().length));

        connection.setUseCaches(false);
        connection.setDoOutput(true);

        // Send request
        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.close();
        
        return connection.getResponseCode();
    }

    @Test
    public void addActor200Test() { //1-10
        JSONObject jsonBody = new JSONObject();
        int response = 0;
        try{
            URL url = new URL("http://localhost:8080/api/v1/addActor");
            jsonBody.put("name", "mutasem");
            jsonBody.put("actorId", "11");
            urlParameters = jsonBody.toString();

            response = setup(urlParameters, url, "PUT");

        }
        catch(Exception e){
            fail("Unexpected error");                
        }
        finally {
            assertEquals( 200, response);            
        }
    }

    @Test
    public void addActor400Test() { //11-20
        int response = 0;
        JSONObject jsonBody = new JSONObject();

        try{
            URL url = new URL("http://localhost:8080/api/v1/addActor");
            jsonBody.put("Name", "mutasem");
            jsonBody.put("actorID", "111");
            urlParameters = jsonBody.toString();

            response = setup(urlParameters, url, "PUT");

        }
        catch(Exception e){
            fail("Unexpected error");       
        }
        finally {
            assertEquals( 400, response);            
        }
    }

    @Test
    public void addMovie200Test() {//21-30
        int response = 0;
        JSONObject jsonBody = new JSONObject();

        try{
            URL url = new URL("http://localhost:8080/api/v1/addMovie");
            jsonBody.put("name", "mutasem");
            jsonBody.put("movieId", "21");
            urlParameters = jsonBody.toString();

            response = setup(urlParameters, url, "PUT");
        }
        catch(Exception e){
            fail("Unexpected error");       
        }
        finally {
            assertEquals(200, response);            
        }
    }

    @Test
    public void addMovie400Test() { //31-40
        int response = 0;
        JSONObject jsonBody = new JSONObject();

        try{
            URL url = new URL("http://localhost:8080/api/v1/addMovie");
            jsonBody.put("name", "mutasem");
            jsonBody.put("actorId", "31");
            urlParameters = jsonBody.toString();

            response = setup(urlParameters, url, "PUT");

        }
        catch(Exception e){
            fail("Unexpected error");       
        }
        finally {
            assertEquals(400, response);            
        }
    }

    
    @Test
    public void addRelationship200Test() {//41-50
        int response = 0;
        JSONObject jsonBody = new JSONObject();
        
        try{
            URL url = new URL("http://localhost:8080/api/v1/addRelationship");

            jsonBody.put("actorId", "45");
            jsonBody.put("movieId", "46");
   
            urlParameters = jsonBody.toString();

            database.addActor("Muta", "45");
            database.addMovie("Fam", "46");
            
            response = setup(urlParameters, url, "PUT");

        }
        catch (Exception e) {
            System.out.println(e);
        }
        finally {
            assertEquals(200, response);            
        }
    }

    @Test
    public void addRelationship404Test() {//51-60
        int response = 0;        
        JSONObject jsonBody = new JSONObject();

        try{
            URL url = new URL("http://localhost:8080/api/v1/addRelationship");

            jsonBody.put("actorId", "51");
            jsonBody.put("movieId", "60");
            
            urlParameters = jsonBody.toString();

            response = setup(urlParameters, url, "PUT");

        }
        catch(Exception e){
            fail("Unexpected error");       
        }
        finally {
            assertEquals(404, response);            
        }
    }

    @Test
    public void getActor200Test() {//61-70
        int response = 0;
        JSONObject jsonBody = new JSONObject();

        try{
            URL url = new URL("http://localhost:8080/api/v1/getActor");
            database.addActor("haha", "61");

            jsonBody.put("name", "haha");
            jsonBody.put("actorId", "61");
            urlParameters = jsonBody.toString();

            response = setup(urlParameters, url, "GET");

        }
        catch(Exception e){
            fail("Unexpected error");       
        }
        finally {
            assertEquals(200, response);            
        }
    }

    @Test
    public void getActor400Test() {//71-80
        int response = 0;
        JSONObject jsonBody = new JSONObject();

        try{
            URL url = new URL("http://localhost:8080/api/v1/getActor");

            jsonBody.put("name", "notfound");
            jsonBody.put("actorId", "71");
            urlParameters = jsonBody.toString();

            response = setup(urlParameters, url, "GET");

        }
        catch(Exception e){
            fail("Unexpected error");       
        }
        finally {
            assertEquals(404, response);            
        }
    }

    @Test
    public void hasRelationship200Test() {//81-90
        int response = 0;
        JSONObject jsonBody = new JSONObject();

        try{
            URL url = new URL("http://localhost:8080/api/v1/hasRelationship");

            jsonBody.put("actorId", "81");
            jsonBody.put("movieId", "82");
            urlParameters = jsonBody.toString();

            database.addActor("muta", "81");
            database.addMovie("ehe", "82");

            database.addRelationship("81", "82");      

            response = setup(urlParameters, url, "GET");
      
        }
        catch(Exception e){
            fail("Unexpected error");       
        }
        finally {
            assertEquals(200, response);            
        }
    }

    @Test
    public void hasRelationship404Test() {//91-100
        int response = 0;
        JSONObject jsonBody = new JSONObject();

        try{
            URL url = new URL("http://localhost:8080/api/v1/hasRelationship");

            jsonBody.put("actorId", "95");
            jsonBody.put("movieId", "82");
            urlParameters = jsonBody.toString();

            response = setup(urlParameters, url, "GET");

        }
        catch(Exception e){
            fail("Unexpected error");       
        }
        finally {
            assertEquals(404, response);            
        }
    }

    
    @Test
    public void computeBaconNumber200Test() {//101-110
        int response = 0;
        JSONObject jsonBody = new JSONObject();

        try{
            URL url = new URL("http://localhost:8080/api/v1/computeBaconNumber");

            jsonBody.put("actorId", "101");
            urlParameters = jsonBody.toString();

            database.addActor("target", "101");
            database.addMovie("movie3", "108");

            database.addRelationship("nm0000102", "108");
            database.addRelationship("101", "108");  

            response = setup(urlParameters, url, "GET");       
        }
        catch(Exception e){
            fail("Unexpected error");       
        }
        finally {
            assertEquals(200, response);            
        }
    }
    
    @Test
    public void computeBaconNumber404() {//111-120
        int response = 0;
        JSONObject jsonBody = new JSONObject();

        try{
            
            URL url = new URL("http://localhost:8080/api/v1/computeBaconNumber");

            jsonBody.put("actorId", "112");
            urlParameters = jsonBody.toString();

            response = setup(urlParameters, url, "GET");

        }
        catch(Exception e){
            fail("Unexpected error");       
        }
        finally {
            assertEquals(404, response);            
        }
    }

    @Test
    public void computeBaconPath200Test() {//121-130
        int response = 0;
        JSONObject jsonBody = new JSONObject();

        try{
            URL url = new URL("http://localhost:8080/api/v1/computeBaconPath");

            jsonBody.put("actorId", "121");
            urlParameters = jsonBody.toString();

            database.addActor("target", "121");
            database.addMovie("movie3", "128");

            database.addRelationship("nm0000102", "128");
            database.addRelationship("121", "128");  

            response = setup(urlParameters, url, "GET");       
        }
        catch(Exception e){
            fail("Unexpected error");       
        }
        finally {
            assertEquals(200, response);            
        }
    }
    
    @Test
    public void computeBaconPath404() {//131-140
        int response = 0;
        JSONObject jsonBody = new JSONObject();

        try{
            URL url = new URL("http://localhost:8080/api/v1/computeBaconPath");

            jsonBody.put("actorId", "131");
            urlParameters = jsonBody.toString();

            response = setup(urlParameters, url, "GET");

        }
        catch(Exception e){
            fail("Unexpected error");       
        }
        finally {
            assertEquals(404, response);            
        }
    }
}
