package org.apache.flink.client.model.pojo;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Dmitrii_Kober on 3/29/2018.
 */
public class StringBufferOutputStream extends OutputStream {

    protected StringBuffer buffer;

    public StringBufferOutputStream() {
        buffer = new StringBuffer();
    }

    public void write(int ch) throws IOException {
        buffer.append((char) ch);
    }

    @Override
    public String toString() {
        return buffer.toString();
    }
}
