package org.test.j2v8.jsCallbacks;

import com.eclipsesource.v8.JavaCallback;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * c.comandini
 * 21/12/2018 at 22:35
 **/
public class HttpClientWithHttpOkCallback implements JavaCallback {

    public Object invoke(V8Object receiver, V8Array parameters) {
        String output = "";
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            long start = System.currentTimeMillis();

            Request request = new Request.Builder()
                    .url("https://www.google.com")
                    .build();
            Response response = okHttpClient.newCall(request).execute();
            output = response.body().string();
            long end = System.currentTimeMillis();
            System.out.println("httpOk done in " + (end - start) + " msecs");
        }catch (Exception e){
            e.printStackTrace();
        }
        return output;
    }
}
