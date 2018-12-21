package org.test.j2v8;

import com.mashape.unirest.http.Unirest;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * c.comandini
 * 20/12/2018 at 23:33
 **/
public class NodeJSExecutor {

    public static void main(String... args) {
        doHttpClientsWarmup();
        System.out.println("=========== 0 ===========");
        long start = System.currentTimeMillis();
        SimpleJavascript sj = new SimpleJavascript();
        sj.run();
        long end = System.currentTimeMillis();
        System.out.println((end - start) + " msecs");
        System.out.println("\n\n=========== 1 ===========");
        start = System.currentTimeMillis();
        NodeJSTestCase1 nodeJSTestCase1 = new NodeJSTestCase1();
        nodeJSTestCase1.run();
        end = System.currentTimeMillis();
        System.out.println((end - start) + " msecs");
        System.out.println("\n\n=========== 2 ===========");
        start = System.currentTimeMillis();
        NodeJSTestCase2 nodeJSTestCase2 = new NodeJSTestCase2();
        nodeJSTestCase2.run();
        end = System.currentTimeMillis();
        System.out.println((end - start) + " msecs");
        System.out.println("\n\n=========== 3 ===========");
        start = System.currentTimeMillis();
        NodeJSTestCase3 nodeJSTestCase3 = new NodeJSTestCase3();
        nodeJSTestCase3.run(false);
        end = System.currentTimeMillis();
        System.out.println((end - start) + " msecs");
        System.out.println("\n\n=========== 4 ===========");
        start = System.currentTimeMillis();
        nodeJSTestCase3 = new NodeJSTestCase3();
        nodeJSTestCase3.run(true);
        end = System.currentTimeMillis();
        System.out.println((end - start) + " msecs");
        System.out.println("\n\n=========== END ===========");
    }


    private static void doHttpClientsWarmup() {
        try {
            Unirest.get("https://www.facebook.com").asString().getBody();
        } catch (Exception e) {
        }
    }


}
