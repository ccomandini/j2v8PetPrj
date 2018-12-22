package org.test.j2v8;

import com.mashape.unirest.http.Unirest;
import org.test.j2v8.testCases.NodeJSSimpleTestCase;
import org.test.j2v8.testCases.NodeJSMimicSynchronousExecutionTestCase;
import org.test.j2v8.testCases.NodeJSMimicSynchronousWithCustomHttpClientTestCase;
import org.test.j2v8.testCases.NodeJSWithExternalModuleTestCase;
import org.test.j2v8.testCases.SimpleJavascriptTestCase;
import org.test.j2v8.testCases.bad.NodeJSTestCaseBadWriteFile;
import org.test.j2v8.testCases.bad.NodeJSTestCaseNeverEndScript;
import org.test.j2v8.testCases.bad.NodeJSTestCaseStopProcess;

/**
 * c.comandini
 * 20/12/2018 at 23:33
 **/
public class NodeJSExecutor {

    public static void main(String... args) {
        notStandardModuleUsage();
        System.out.println("=========== tests ===========");
        firstSetOfTests();
        System.out.println("=========== bad cases ===========");
        badCases();
    }

    public static void badCases() {
        System.out.println("=========== save file ===========");
        long start = System.currentTimeMillis();
        NodeJSTestCaseBadWriteFile writeFileTest = new NodeJSTestCaseBadWriteFile();
        writeFileTest.run();
        long end = System.currentTimeMillis();
        System.out.println((end - start) + " msecs");

        System.out.println("\n\n=========== infinite loop ===========");
        start = System.currentTimeMillis();
        NodeJSTestCaseNeverEndScript neverEndScript = new NodeJSTestCaseNeverEndScript();
        neverEndScript.run();
        end = System.currentTimeMillis();
        System.out.println((end - start) + " msecs");

        System.out.println("\n\n=========== stop process ===========");
        start = System.currentTimeMillis();
        NodeJSTestCaseStopProcess stopProcess = new NodeJSTestCaseStopProcess();
        stopProcess.run();
        end = System.currentTimeMillis();
        System.out.println((end - start) + " msecs");

        System.out.println("\n\n=========== END BAD ===========");
    }

    public static void notStandardModuleUsage() {
        long start = System.currentTimeMillis();
        NodeJSWithExternalModuleTestCase testCase = new NodeJSWithExternalModuleTestCase();
        testCase.run();
        long end = System.currentTimeMillis();
        System.out.println((end - start) + " msecs");
    }

    public static void firstSetOfTests() {
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
        nodeJSTestCase3.runWithUnirest();
        end = System.currentTimeMillis();
        System.out.println((end - start) + " msecs");


        System.out.println("\n\n=========== 4 ===========");
        start = System.currentTimeMillis();
        nodeJSTestCase3 = new NodeJSMimicSynchronousWithCustomHttpClientTestCase();
        nodeJSTestCase3.runWithHttpOk();
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
