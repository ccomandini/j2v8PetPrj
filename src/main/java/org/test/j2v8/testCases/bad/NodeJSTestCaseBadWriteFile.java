package org.test.j2v8.testCases.bad;

import com.eclipsesource.v8.NodeJS;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.test.j2v8.NodeJSExecutor;

/**
 * c.comandini
 * 21/12/2018 at 17:51
 **/
public class NodeJSTestCaseBadWriteFile {
    //https://www.w3schools.com/nodejs/ref_modules.asp
    public void run() {
        NodeJS nodeJS = null;
        try {
            Path path = Paths.get(NodeJSExecutor.class.getClassLoader().getResource("bad-code-3.js").toURI());
            File nodeScript = path.toFile();

            nodeJS = NodeJS.createNodeJS();

            nodeJS.exec(nodeScript);

            while(nodeJS.isRunning()) {
                nodeJS.handleMessage(); //ask to V8 to handle the stack
            }

            System.out.println("Write file test");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(nodeJS!=null) {
                nodeJS.release(); //release the node environment/process
            }
        }
    }


}
