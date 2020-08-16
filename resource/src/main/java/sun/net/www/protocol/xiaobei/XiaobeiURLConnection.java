package sun.net.www.protocol.xiaobei;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/16 22:57
 */
public class XiaobeiURLConnection extends URLConnection {

    private final ClassPathResource resource;

    /**
     *  URL = xiaobei:///META-INF/test.properties
     * @param url
     */
    protected XiaobeiURLConnection(URL url) {
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
