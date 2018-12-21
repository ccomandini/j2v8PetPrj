package org.test.j2v8.jsCallbacks;

import com.eclipsesource.v8.JavaCallback;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;

/**
 * c.comandini
 * 21/12/2018 at 23:21
 **/
public class ValueProviderCallback implements JavaCallback {

    private final String value;

    public ValueProviderCallback(String value) {
        this.value = value;
    }

    @Override
    public Object invoke(V8Object receiver, V8Array parameters) {
        return value;
    }
}
