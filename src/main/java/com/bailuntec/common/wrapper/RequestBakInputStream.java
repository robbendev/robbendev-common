package com.bailuntec.common.wrapper;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.ByteArrayInputStream;

/**
 * Request 备份流
 *
 * @author robbendev
 */
public class RequestBakInputStream extends ServletInputStream {

    private ByteArrayInputStream byteArrayInputStream = null;

    public RequestBakInputStream(ByteArrayInputStream byteArrayInputStream) {
        this.byteArrayInputStream = byteArrayInputStream;
    }

    @Override
    public int read() {
        if (this.byteArrayInputStream != null) {
            return byteArrayInputStream.read();
        }
        return 0;
    }

    public boolean isFinished() {
        return true;
    }

    public boolean isReady() {
        return (this.byteArrayInputStream != null);
    }

    public void setReadListener(ReadListener readListener) {

    }
}
