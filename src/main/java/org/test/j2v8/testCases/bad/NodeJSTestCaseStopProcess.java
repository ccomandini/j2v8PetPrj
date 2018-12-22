package org.test.j2v8.testCases.bad;

import com.eclipsesource.v8.JavaCallback;
import com.eclipsesource.v8.NodeJS;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.lang3.StringUtils;
import org.test.j2v8.NodeJSExecutor;

/**
 * c.comandini
 * 21/12/2018 at 17:51
 **/
public class NodeJSTestCaseStopProcess {

    public void run() {
        NodeJS nodeJS = null;
        try {
            Path path = Paths.get(NodeJSExecutor.class.getClassLoader().getResource("bad-code-1.js").toURI());
            File nodeScript = path.toFile();

            nodeJS = NodeJS.createNodeJS();

            JavaCallback callback = new JavaCallback() {
                public Object invoke(V8Object receiver, V8Array parameters) {
                    return null;
                }
            };

            nodeJS.getRuntime().registerJavaMethod(callback, "process");

            nodeJS.exec(nodeScript);

            while(nodeJS.isRunning()) {
                nodeJS.handleMessage(); //ask to V8 to handle the stack
            }

            System.out.println("we will never reach this point");
        } catch (Exception e) {
            System.out.println(">>>"+e.getMessage());
        } finally {
            if(nodeJS!=null) {
                nodeJS.release(); //release the node environment/process
            }
        }
    }


}
