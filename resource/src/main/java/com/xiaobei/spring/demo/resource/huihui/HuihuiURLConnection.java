package com.xiaobei.spring.demo.resource.huihui;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * TODO 实现 URLStreamHandlerFactory 并传递到 URL 中 待完成？？？？
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/16 22:57
 */
public class HuihuiURLConnection extends URLConnection {

    private final ClassPathResource resource;

    /**
     *  URL = huihui:///META-INF/test.properties
     * @param url
     */
    protected HuihuiURLConnection(URL url) {
        super(url);
        this.resource = new ClassPathResource(url.getPath());
    }

    @Override
    public void connect() throws IOException {
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return resource.getInputStream();
    }
}
