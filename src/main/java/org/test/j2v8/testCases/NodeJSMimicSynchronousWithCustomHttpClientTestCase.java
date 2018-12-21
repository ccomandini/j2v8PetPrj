package org.test.j2v8.testCases;

import com.eclipsesource.v8.JavaCallback;
import com.eclipsesource.v8.NodeJS;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.lang3.StringUtils;
import org.test.j2v8.jsCallbacks.StringBuilderCallback;
import org.test.j2v8.NodeJSExecutor;
import org.test.j2v8.jsCallbacks.HttpClientWithHttpOkCallback;
import org.test.j2v8.jsCallbacks.HttpClientWithUnirestCallback;

/**
 * c.comandini
 * 21/12/2018 at 17:51
 **/
public class NodeJSMimicSynchronousWithCustomHttpClientTestCase {


    private JavaCallback javaHttpClientUsingUnirest = new HttpClientWithUnirestCallback();


    private JavaCallback javaHttpClientUsingHttpOk = new HttpClientWithHttpOkCallback();

    public void runWithUnirest(){
        run(false);
    }

    public void runWithHttpOk(){
        run(true);
    }

    private void run(boolean useHttpOk) {
        NodeJS nodeJS = null;
        try {

            Path path = Paths.get(NodeJSExecutor.class.getClassLoader().getResource("test-script-java-httpclient.js").toURI());
            File nodeScript = path.toFile(); //can be something downloaded through AWS S3 ...

            nodeJS = NodeJS.createNodeJS();

            StringBuilder stringBuilder = new StringBuilder();

            nodeJS.getRuntime().registerJavaMethod(useHttpOk ? javaHttpClientUsingHttpOk : javaHttpClientUsingUnirest, "javaHttpClient");

            nodeJS.getRuntime().registerJavaMethod(new StringBuilderCallback(stringBuilder), "javaCallback");//it's adding a new js function (javaCallback) that binds a java function within the javascript script

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
