package org.test.j2v8.testCases;

import com.eclipsesource.v8.V8;

/**
 * c.comandini
 * 21/12/2018 at 16:06
 **/
public class SimpleJavascriptTestCase {

    public void run() {
        V8 runtime = null;
        try {
            runtime = V8.createV8Runtime();
            String result = runtime.executeStringScript(""
                    + "var hello = 'hello, ';\n"
                    + "var world = 'world!';\n"
                    + "hello.concat(world);\n");
            System.out.println("javascript execution > " + result);
        }catch (Exception e){

        }finally {
            if(runtime!=null){
                runtime.release();
            }
        }

    }
}
