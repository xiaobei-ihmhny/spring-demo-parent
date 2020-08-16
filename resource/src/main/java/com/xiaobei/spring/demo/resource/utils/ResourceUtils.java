package com.xiaobei.spring.demo.resource.utils;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;
import java.io.Reader;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/16 20:23
 */
public interface ResourceUtils {

    static String getContext(Resource resource)  {
        try {
            return getContext(resource, "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static String getContext(Resource resource, String encoding) throws IOException {
        EncodedResource encodedResource = new EncodedResource(resource, encoding);
        try(Reader reader = encodedResource.getReader()) {
            return IOUtils.toString(reader);
        }
    }
}
