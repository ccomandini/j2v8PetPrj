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
public class NodeJSTestCaseNeverEndScript {

    public void run() {
        NodeJS nodeJS = null;
        try {
            Path path = Paths.get(NodeJSExecutor.class.getClassLoader().getResource("bad-code-2.js").toURI());
            File nodeScript = path.toFile();

            nodeJS = NodeJS.createNodeJS();

            final NodeJS nodeJsFinal = nodeJS;

            Thread thread = new Thread(){
                public void run(){
                    try {
                        Thread.sleep(2200);
                    } catch (InterruptedException e) { }
                    nodeJsFinal.getRuntime().release();
                }
            };

            thread.run();

            thread.start();

            nodeJS.exec(nodeScript);

            while(nodeJS.isRunning()) {
                nodeJS.handleMessage(); //ask to V8 to handle the stack
            }

            System.out.println("yo yo yo");
        } catch (Exception e) {
            System.out.println("Interrupted >>> "+e.getMessage());
        } finally {
            if(nodeJS!=null) {
                try {
                    if(!nodeJS.getRuntime().isReleased()) {
                        nodeJS.release(); //release the node environment/process
                    }
                }catch (Exception e){

                }
            }
        }
    }


}
