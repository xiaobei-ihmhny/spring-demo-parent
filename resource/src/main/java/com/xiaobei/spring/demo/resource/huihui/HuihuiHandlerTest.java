package com.xiaobei.spring.demo.resource.huihui;

import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/16 23:06
 */
public class HuihuiHandlerTest {


    /**
     * <h2>运行结果：</h2>
     * name=\u4F9D\u8D56\u6CE8\u5165Spring Resource
     *
     *
     * 需要添加 -Djava.protocol.handler.pkgs=com.xiaobei.spring.demo.resource
     * @param args
     * @throws IOException
     *
     * @see java.net.URLStreamHandler
     * @see URLConnection
     */
    public static void main(String[] args) throws IOException {
        URL url = new URL("huihui:///META-INF/test.properties");
        InputStream inputStream = url.openStream();
        System.out.println(StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8));
    }
}
