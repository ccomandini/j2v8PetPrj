package org.test.j2v8;

import com.mashape.unirest.http.Unirest;
import org.test.j2v8.testCases.NodeJSSimpleTestCase;
import org.test.j2v8.testCases.NodeJSMimicSynchronousExecutionTestCase;
import org.test.j2v8.testCases.NodeJSMimicSynchronousWithCustomHttpClientTestCase;
import org.test.j2v8.testCases.SimpleJavascriptTestCase;

/**
 * c.comandini
 * 20/12/2018 at 23:33
 **/
public class NodeJSExecutor {

    public static void main(String... args) {
        doHttpClientsWarmup();
        System.out.println("=========== 0 ===========");
        long start = System.currentTimeMillis();
        SimpleJavascriptTestCase sj = new SimpleJavascriptTestCase();
        sj.run();
        long end = System.currentTimeMillis();
        System.out.println((end - start) + " msecs");
        System.out.println("\n\n=========== 1 ===========");
        start = System.currentTimeMillis();
        NodeJSSimpleTestCase nodeJSTestCase1 = new NodeJSSimpleTestCase();
        nodeJSTestCase1.run();
        end = System.currentTimeMillis();
        System.out.println((end - start) + " msecs");
        System.out.println("\n\n=========== 2 ===========");
        start = System.currentTimeMillis();
        NodeJSMimicSynchronousExecutionTestCase nodeJSTestCase2 = new NodeJSMimicSynchronousExecutionTestCase();
        nodeJSTestCase2.run();
        end = System.currentTimeMillis();
        System.out.println((end - start) + " msecs");
        System.out.println("\n\n=========== 3 ===========");
        start = System.currentTimeMillis();
        NodeJSMimicSynchronousWithCustomHttpClientTestCase nodeJSTestCase3 = new NodeJSMimicSynchronousWithCustomHttpClientTestCase();
        nodeJSTestCase3.run(false);
        end = System.currentTimeMillis();
        System.out.println((end - start) + " msecs");
        System.out.println("\n\n=========== 4 ===========");
        start = System.currentTimeMillis();
        nodeJSTestCase3 = new NodeJSMimicSynchronousWithCustomHttpClientTestCase();
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
