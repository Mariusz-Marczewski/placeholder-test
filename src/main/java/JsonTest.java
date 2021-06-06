import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.InputStreamReader;

public class JsonTest
{
    public static void main(String[] args) throws Exception
    {
        JsonTest json = new JsonTest();
        json.doItArray();
    }

    public void doIt() throws Exception {
        JsonReader reader = null;
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpHost target = new HttpHost("jsonplaceholder.typicode.com", 80, "http");
        HttpGet request = new HttpGet("/posts/1");
        request.addHeader("accept", "application/json");
        CloseableHttpResponse response = null;
        try {
            response = client.execute(target, request);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new Exception("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            }

            reader = javax.json.Json.createReader(new InputStreamReader((response.getEntity().getContent())));
            JsonObject object = reader.readObject();

            // System.out.println("raw : " + object.toString()) ;
            System.out.println("id : " + object.getJsonNumber("id")) ;
            System.out.println("title : " + object.getString("title")) ;
         /*
            output
              id : 1
              title : sunt aut facere repellat provident occaecati excepturi optio reprehenderit
              Done.
         */
        }
        finally {
            if (reader != null) reader.close();
            if (client != null) client.close();
            if (response != null) response.close();
            System.out.println("Done.");
        }
    }

    public void doItArray() throws Exception
    {
        JsonReader reader = null;
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpHost target = new HttpHost("jsonplaceholder.typicode.com", 80, "http");
        HttpGet request = new HttpGet("/posts");
        request.addHeader("accept", "application/json");
        CloseableHttpResponse response = null;
        try {
            response = client.execute(target, request);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new Exception("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            }

            javax.json.Json.createArrayBuilder()

            reader = javax.json.Json.createReader(new InputStreamReader((response.getEntity().getContent())))
            JsonArray array = reader.readArray();

            for (int j = 0; j < array.size(); j++  ) {
                JsonObject jo = array.getJsonObject(j);
                System.out.println("id : " + jo.getJsonNumber("id")) ;
                System.out.println("title : " + jo.getString("title")) ;
            }
         /*
            output
              id : 1
              title : sunt aut facere repellat provident occaecati excepturi optio reprehenderit
              ...
              id : 100
              title : at nam consequatur ea labore ea harum
              Done.
         */
        }
        finally {
            if (reader != null) reader.close();
            if (client != null) client.close();
            if (response != null) response.close();
            System.out.println("Done.");
        }
    }
}
