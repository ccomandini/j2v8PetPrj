package org.test.j2v8;

import com.eclipsesource.v8.NodeJS;
import com.mashape.unirest.http.Unirest;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.lang3.StringUtils;

/**
 * c.comandini
 * 21/12/2018 at 17:51
 **/
public class NodeJSTestCase2 {

    public void run() {
        NodeJS nodeJS = null;
        try {

            Path path = Paths.get(NodeJSExecutor.class.getClassLoader().getResource("test-script.js").toURI());
            File nodeScript = path.toFile(); //downloaded by AWS

            nodeJS = NodeJS.createNodeJS();

            StringBuilder stringBuilder = new StringBuilder();

            nodeJS.getRuntime().registerJavaMethod(new CustomJavaCallback(stringBuilder), "javaCallback");//it's adding a new js function (javaCallback) that binds a java function within the javascript script

            nodeJS.exec(nodeScript);

            while(nodeJS.isRunning()) {
                nodeJS.handleMessage(); //ask to V8 to handle the stack
            }

            String out = StringUtils.abbreviate(stringBuilder.toString(), 300);

            System.out.println("NodeJS execution >>> "+out);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(nodeJS!=null) {
                nodeJS.release(); //release the node environment/process
            }
        }
    }


}
