package org.test.j2v8;

import com.eclipsesource.v8.JavaCallback;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;


/**
 * c.comandini
 * 21/12/2018 at 17:55
 **/
public class CustomJavaCallback implements JavaCallback {

    private final StringBuilder externalStringBuilder;

    public CustomJavaCallback(StringBuilder externalStringBuilder) {
        this.externalStringBuilder = externalStringBuilder;
    }

    public Object invoke(V8Object receiver, V8Array parameters) {
        externalStringBuilder.append(parameters.getString(0));
        return null;
    }
}
