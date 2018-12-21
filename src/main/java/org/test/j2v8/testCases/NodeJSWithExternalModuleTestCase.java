package org.test.j2v8.testCases;

import com.eclipsesource.v8.NodeJS;
import com.eclipsesource.v8.V8Object;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.test.j2v8.NodeJSExecutor;
import org.test.j2v8.jsCallbacks.StringBuilderCallback;
import org.test.j2v8.jsCallbacks.ValueProviderCallback;

/**
 * c.comandini
 * 21/12/2018 at 17:51
 **/
public class NodeJSWithExternalModuleTestCase {
    /**
     * https://www.npmjs.com/package/qr-image
     * Step for running the example:
     * 1. create a folder like /Users/ccomandini/testNodeJsModule
     * 2. move into that folder
     * 3. run this: npm install qr-image
     * 4. local path with module will be >>> /Users/ccomandini/testNodeJsModule/node_modules/qr-image
     * 5. change value of JS_MODULE_PATH with the module path
     * 6. change value of TARGET_FOLDER with something related to your local machine
     */

    private final static String JS_MODULE_PATH = "/Users/ccomandini/testNodeJsModule/node_modules/qr-image";

    private final static String TARGET_FOLDER = "/Users/ccomandini/Desktop/foo";

    public void run() {
        NodeJS nodeJS = null;
        try {
            File targetFolder = new File(TARGET_FOLDER);
            if(!targetFolder.exists()){
                targetFolder.mkdirs();
            }

            Path path = Paths.get(NodeJSExecutor.class.getClassLoader().getResource("test-script-with-module.js").toURI());
            File nodeScript = path.toFile();

            nodeJS = NodeJS.createNodeJS();

            StringBuilder stringBuilder = new StringBuilder();

            nodeJS.getRuntime().registerJavaMethod(new StringBuilderCallback(stringBuilder), "returnBack");//it's adding a new js function (returnBack) that binds a java function within the javascript script

            nodeJS.getRuntime().registerJavaMethod(new ValueProviderCallback(JS_MODULE_PATH), "modulePath");

            nodeJS.exec(nodeScript);

            while(nodeJS.isRunning()) {
                nodeJS.handleMessage(); //ask to V8 to handle the stack
            }

            String out = stringBuilder.toString();

            String outputFile = TARGET_FOLDER+File.separator+"qrCodeTest."+System.currentTimeMillis()+".pdf";

            FileUtils.writeStringToFile(new File(outputFile), out, Charset.forName("UTF-8"));

            System.out.println("NodeJS execution >>> "+ outputFile);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(nodeJS!=null) {
                nodeJS.release(); //release the node environment/process
            }
        }
    }


}
