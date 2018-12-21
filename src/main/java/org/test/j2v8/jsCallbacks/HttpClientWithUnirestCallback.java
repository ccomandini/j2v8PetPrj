package org.test.j2v8.jsCallbacks;

import com.eclipsesource.v8.JavaCallback;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;
import com.mashape.unirest.http.Unirest;

/**
 * c.comandini
 * 21/12/2018 at 22:35
 **/
public class HttpClientWithUnirestCallback implements JavaCallback {

    public Object invoke(V8Object receiver, V8Array parameters) {
        String output = "";
        try {
            String url = parameters.getString(0);
            long start = System.currentTimeMillis();
            output = Unirest.get(url).asString().getBody();
            long end = System.currentTimeMillis();
            System.out.println("unirest done in " + (end-start)+" msecs");
        }catch (Exception e){
            e.printStackTrace();
        }
        return output;
    }
}
