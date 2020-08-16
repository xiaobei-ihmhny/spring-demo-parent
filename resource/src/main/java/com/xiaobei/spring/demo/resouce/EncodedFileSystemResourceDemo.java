package com.xiaobei.spring.demo.resouce;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.WritableResource;
import org.springframework.core.io.support.EncodedResource;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

/**
 * 带字符编码的 {@link FileSystemResource} 的使用示例
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/16 16:07
 *
 * @see FileSystemResource
 * @see WritableResource
 * @see EncodedResource
 * @see Reader
 */
public class EncodedFileSystemResourceDemo {

    /**
     * {@link WritableResource}的扩展实现 {@link FileSystemResource}的基本使用
     * 以及带编码的 {@link EncodedResource}的基本使用
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        String separator = File.separator;
        String filePath = System.getProperties().get("user.dir") + "/resource/src/main/java/com/xiaobei/spring/demo/resouce/EncodedFileSystemResourceDemo.java";
        if("\\".equals(separator)) {
            filePath = filePath.replace("/", separator);
        }
        File file = new File(filePath);
        FileSystemResource fileSystemResource = new FileSystemResource(file);
        EncodedResource encodedResource = new EncodedResource(fileSystemResource, StandardCharsets.UTF_8);
        // 获取字符输入流
        Reader reader = encodedResource.getReader();
        System.out.println(IOUtils.toString(reader));
    }
}
