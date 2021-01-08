package com.bailuntec.common.wrapper;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.util.Map;

/**
 * 备份request中的请求内容
 *
 * @author robbendev
 */
public class RequestBakRequestWrapper extends HttpServletRequestWrapper {

    /**
     * 备份的request请求信息
     */
    private final ByteArrayOutputStream cachedContent = new ByteArrayOutputStream(1024);

    private ServletInputStream inputStream;

    private BufferedReader reader;

    private Map<String, String[]> parameterMap;

    /**
     * 对request中的流进行备份
     *
     * @param request
     * @throws IOException
     */
    public RequestBakRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        this.parameterMap = request.getParameterMap();

        // 将request中的输入流读入字节数组输出流中
        byte[] buffer = new byte[1024];
        int len;
        while ((len = request.getInputStream().read(buffer)) > -1) {
            cachedContent.write(buffer, 0, len);
        }
        cachedContent.flush();

        // 设置输入流为备份流
        this.inputStream = new RequestBakInputStream(new ByteArrayInputStream(cachedContent.toByteArray()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return this.inputStream;
    }

    @Override
    public String getCharacterEncoding() {
        String characterEncoding = super.getCharacterEncoding();
        return (characterEncoding != null ? characterEncoding : "ISO-8859-1");
    }

    @Override
    public BufferedReader getReader() throws IOException {
        if (this.reader == null) {
            this.reader = new BufferedReader(new InputStreamReader(this.inputStream, getCharacterEncoding()));
        }
        return this.reader;
    }

    /**
     * 返回流中的byte字节数组信息
     *
     * @return byte
     */
    public byte[] getContentAsByteArray() {
        return this.cachedContent.toByteArray();
    }

    public ByteArrayOutputStream getCachedContent() {
        return this.cachedContent;
    }

    @Override
    public String getParameter(String name) {
        if (this.parameterMap != null) {
            String[] params = this.parameterMap.get(name);
            if (params != null && params.length > 0) {
                return params[0];
            }
        }
        return null;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return parameterMap;
    }
}
