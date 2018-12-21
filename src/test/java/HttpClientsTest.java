import com.mashape.unirest.http.Unirest;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * c.comandini
 * 21/12/2018 at 18:42
 **/
public class HttpClientsTest {

    public static void main(String ... args){
        OkHttpClient client = new OkHttpClient();
        doHttpClientsWarmup(client);
        System.out.println("...");
        testUnirest();
        testHttpOk(client);
        testUnirest();
        testHttpOk(client);
    }

    private static void doHttpClientsWarmup(OkHttpClient client) {
        if(client!=null) {
            try {
                Request request = new Request.Builder()
                        .url("https://www.google.com")
                        .build();
                Response response = client.newCall(request).execute();
                response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            Unirest.get("https://www.google.com").asString().getBody();
        }catch (Exception e){}
    }

    public static void testHttpOk(OkHttpClient client) {
        try {
            long start = System.currentTimeMillis();

            Request request = new Request.Builder()
                    .url("https://www.google.com")
                    .build();
            Response response = client.newCall(request).execute();
            response.body().string();
            long end = System.currentTimeMillis();
            System.out.println("httpOk done in " + (end - start) + " msecs");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void testUnirest() {
        try {
            long start = System.currentTimeMillis();
            Unirest.get("https://www.google.com").asString().getBody();
            long end = System.currentTimeMillis();
            System.out.println("unirest done in " + (end - start) + " msecs");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
