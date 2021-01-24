package com.xiaobei.springmvc.demo;

import com.xiaobei.springmvc.demo.databinder.AttributeMethodProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * springmvc核心配置，实现接口 {@link WebMvcConfigurer} 并覆盖相关方法以实现相关功能。
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021-01-22 18:40:40
 */
@EnableWebMvc
@Configuration
@ComponentScan(basePackages = "com.xiaobei.springmvc.demo")
public class SpringWebMvcConfiguration implements WebMvcConfigurer {

    /**
     * 1.11.3. Type Conversion
     * <a href="https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-config-conversion">Type Conversion</a>
     * 默认情况下会安装支持字段上使用的两种转换器
     * <ol>1. @NumberFormat</ol>
     * <ol>1. @DateTimeFormat</ol>
     *
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {

    }



    /**
     * 添加请求参数的绑定处理逻辑
     * @param resolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(processor());
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {

    }

    @Bean
    public AttributeMethodProcessor processor() {
        return new AttributeMethodProcessor(true);
    }

}
