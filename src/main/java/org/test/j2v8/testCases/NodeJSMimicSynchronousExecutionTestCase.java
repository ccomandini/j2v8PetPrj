package org.test.j2v8.testCases;

import com.eclipsesource.v8.NodeJS;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.lang3.StringUtils;
import org.test.j2v8.jsCallbacks.StringBuilderCallback;
import org.test.j2v8.NodeJSExecutor;

/**
 * c.comandini
 * 21/12/2018 at 17:51
 **/
public class NodeJSMimicSynchronousExecutionTestCase {

    public void run() {
        NodeJS nodeJS = null;
        try {

            Path path = Paths.get(NodeJSExecutor.class.getClassLoader().getResource("test-script.js").toURI());
            File nodeScript = path.toFile();

            nodeJS = NodeJS.createNodeJS();

            StringBuilder stringBuilder = new StringBuilder();

            nodeJS.getRuntime().registerJavaMethod(new StringBuilderCallback(stringBuilder), "javaCallback");//it's adding a new js function (javaCallback) that binds a java function within the javascript script

            nodeJS.exec(nodeScript);

            while(nodeJS.isRunning()) {
                nodeJS.handleMessage(); //ask to V8 to handle the stack
            }

            String out = StringUtils.abbreviate(stringBuilder.toString(), 300);

            System.out.println("NodeJS execution (mimic synch) >>> "+out);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(nodeJS!=null) {
                nodeJS.release(); //release the node environment/process
            }
        }
    }


}
