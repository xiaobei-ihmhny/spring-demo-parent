package com.xiaobei.springmvc.demo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.xiaobei.springmvc.demo.databinder.AttributeMethodProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.TimeZone;

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
     * 添加自定义的消息转换器
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(converter());
    }

    /**
     * 仅适用于请求参数为 {@link org.springframework.web.bind.annotation.RequestBody @RequestBody}
     * 的请求
     * @return
     */
    @Bean(name = "converter")
    public MappingJackson2HttpMessageConverter converter() {
        ObjectMapper mapper =
                Jackson2ObjectMapperBuilder
                        .json()
                        // 处理 ResponseBody 返回时的日期类型
                        .simpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        // 时区为东八区
                        .timeZone(TimeZone.getTimeZone("GMT+8"))
                        // 为 null 时字段不显示
                        .serializationInclusion(JsonInclude.Include.NON_NULL)
                        // 将驼峰规则转化为蛇形规则
                        .propertyNamingStrategy(new PropertyNamingStrategies.SnakeCaseStrategy())
                        .build();
        return new MappingJackson2HttpMessageConverter(mapper);
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
