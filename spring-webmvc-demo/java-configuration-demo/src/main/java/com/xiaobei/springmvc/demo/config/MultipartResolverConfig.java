package com.xiaobei.springmvc.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * {@link org.springframework.web.multipart.MultipartResolver} 相关实现
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-03-19 06:56:56
 */
@Configuration
public class MultipartResolverConfig implements WebMvcConfigurer {

    /**
     * 使用 {@link CommonsMultipartResolver} 基于
     *  Apache Commons FileUpload(http://commons.apache.org/proper/commons-fileupload/) 的实现
     *  来完成文件上传工作
     * @return
     */
    @Profile(value = "apache commons upload")
    @Bean(name = DispatcherServlet.MULTIPART_RESOLVER_BEAN_NAME)
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setDefaultEncoding("UTF-8");
        // 允许上传文件的最大字节数
        multipartResolver.setMaxUploadSize(10485760);
        return multipartResolver;
    }
}
