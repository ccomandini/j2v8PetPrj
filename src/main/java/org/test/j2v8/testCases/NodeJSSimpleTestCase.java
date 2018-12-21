package org.test.j2v8.testCases;

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
public class NodeJSSimpleTestCase {

    public void run() {
        NodeJS nodeJS = null;
        try {
            Path path = Paths.get(NodeJSExecutor.class.getClassLoader().getResource("test-script.js").toURI());
            File nodeScript = path.toFile();

            nodeJS = NodeJS.createNodeJS();

            JavaCallback callback = new JavaCallback() {
                public Object invoke(V8Object receiver, V8Array parameters) {
                    System.out.println("# of return parameters >> "+parameters.length());
                    System.out.println(StringUtils.abbreviate(parameters.getString(0),300) );
                    return null;
                }
            };

            nodeJS.getRuntime().registerJavaMethod(callback, "javaCallback");//it's adding a new js function (javaCallback) that binds a java function within the javascript script

            nodeJS.exec(nodeScript);

            while(nodeJS.isRunning()) {
                nodeJS.handleMessage(); //ask to V8 to handle the stack
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(nodeJS!=null) {
                nodeJS.release(); //release the node environment/process
            }
        }
    }


}
