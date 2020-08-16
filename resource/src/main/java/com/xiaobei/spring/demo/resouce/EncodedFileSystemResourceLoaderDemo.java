package com.xiaobei.spring.demo.resouce;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.core.io.support.EncodedResource;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

/**
 * TODO 感觉还是意犹未尽！！！
 * {@link FileSystemResourceLoader} 的使用示例
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/16 16:07
 *
 * @see FileSystemResourceLoader
 * @see WritableResource
 * @see EncodedResource
 * @see Reader
 */
public class EncodedFileSystemResourceLoaderDemo {

    /**
     * <h2>注意</h2>
     * 在 unix/linux/mac等系统下时，需要在 {@code filePath} 前多添加一个 “/”，可参考实现：
     * {@link FileSystemResourceLoader#getResourceByPath(java.lang.String)}
     *
     *
     * @param args
     * @throws IOException
     *
     * @see DefaultResourceLoader#getResource(java.lang.String)
     * @see FileSystemResourceLoader#getResourceByPath(java.lang.String)
     */
    public static void main(String[] args) throws IOException {
        String separator = File.separator;
        String filePath = System.getProperties().get("user.dir") + "/resource/src/main/java/com/xiaobei/spring/demo/resouce/EncodedFileSystemResourceDemo.java";
        if("\\".equals(separator)) {
            filePath = filePath.replace("/", separator);
        }
        if("/".equals(separator)) {
            filePath = "/" + filePath;
        }
        FileSystemResourceLoader loader = new FileSystemResourceLoader();
        Resource resource = loader.getResource(filePath);
        EncodedResource encodedResource = new EncodedResource(resource, StandardCharsets.UTF_8);
        // 获取字符输入流
        Reader reader = encodedResource.getReader();
        System.out.println(IOUtils.toString(reader));
    }
}
