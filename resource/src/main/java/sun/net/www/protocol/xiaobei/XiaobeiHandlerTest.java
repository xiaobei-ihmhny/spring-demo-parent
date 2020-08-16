package sun.net.www.protocol.xiaobei;

import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/16 23:00
 */
public class XiaobeiHandlerTest {

    /**
     * <h2>运行结果：</h2>
     * name=\u4F9D\u8D56\u6CE8\u5165Spring Resource
     *
     *
     * @param args
     * @throws IOException
     *
     * @see java.net.URLStreamHandler
     * @see URLConnection
     */
    public static void main(String[] args) throws IOException {
        URL url = new URL("xiaobei:///META-INF/test.properties");
        InputStream inputStream = url.openStream();
        System.out.println(StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8));
    }
}
