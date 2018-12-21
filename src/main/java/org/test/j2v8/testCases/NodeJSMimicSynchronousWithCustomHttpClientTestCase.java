package org.test.j2v8.testCases;

import com.eclipsesource.v8.JavaCallback;
import com.eclipsesource.v8.NodeJS;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;
import com.mashape.unirest.http.Unirest;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.test.j2v8.jsCallbacks.CustomJavaCallback;
import org.test.j2v8.NodeJSExecutor;

/**
 * c.comandini
 * 21/12/2018 at 17:51
 **/
public class NodeJSMimicSynchronousWithCustomHttpClientTestCase {


    private JavaCallback javaHttpClient = new JavaCallback() {
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
    };

    private OkHttpClient okHttpClient = new OkHttpClient();

    private JavaCallback javaHttpClientV2 = new JavaCallback() {
        public Object invoke(V8Object receiver, V8Array parameters) {
            String output = "";
            try {
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
    };

    public void run(boolean useHttpOk) {
        NodeJS nodeJS = null;
        try {

            Path path = Paths.get(NodeJSExecutor.class.getClassLoader().getResource("test-script-java-httpclient.js").toURI());
            File nodeScript = path.toFile(); //can be something downloaded through AWS S3 ...

            nodeJS = NodeJS.createNodeJS();

            StringBuilder stringBuilder = new StringBuilder();

            nodeJS.getRuntime().registerJavaMethod(useHttpOk ? javaHttpClientV2 : javaHttpClient, "javaHttpClient");

            nodeJS.getRuntime().registerJavaMethod(new CustomJavaCallback(stringBuilder), "javaCallback");//it's adding a new js function (javaCallback) that binds a java function within the javascript script

            nodeJS.exec(nodeScript);

            while(nodeJS.isRunning()) {
                nodeJS.handleMessage(); //ask to V8 to handle the stack
            }

            String out = stringBuilder.toString();

            System.out.println("NodeJS execution "+(useHttpOk?"with httpok":"with unirest")+" >>> "+StringUtils.abbreviate(out,300));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(nodeJS!=null) {
                nodeJS.release(); //release the node environment/process
            }
        }
    }






}
