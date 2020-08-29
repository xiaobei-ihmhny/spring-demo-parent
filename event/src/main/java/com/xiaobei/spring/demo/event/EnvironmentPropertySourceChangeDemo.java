package com.xiaobei.spring.demo.event;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/29 6:49
 */
public class EnvironmentPropertySourceChangeDemo {

    @Value("${user.name}")
    private String username;

    /**
     * <h2>运行结果：</h2>
     * lenovo
     * PropertySource[systemProperties]，user.name=lenovo
     * PropertySource[systemEnvironment]，user.name=null
     */
    @Test
    public void defaultPropertySource() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册当前类为 Configuration Class
        applicationContext.register(EnvironmentPropertySourceChangeDemo.class);
        // 启动 Spring 应用上下文
        applicationContext.refresh();
        EnvironmentPropertySourceChangeDemo demo = applicationContext.getBean(EnvironmentPropertySourceChangeDemo.class);
        System.out.println(demo.username);
        MutablePropertySources propertySources = applicationContext.getEnvironment().getPropertySources();
        propertySources.forEach(propertySource ->
                System.out.printf("PropertySource[%s]，user.name=%s\n", propertySource.getName(), propertySource.getProperty("user.name")));
        // 关闭 Spring 应用上下文
        applicationContext.close();
    }

    /**
     * <h2>运行结果：</h2>
     * 小贝
     * PropertySource[customizedPropertySource]，user.name=小贝
     * PropertySource[systemProperties]，user.name=lenovo
     * PropertySource[systemEnvironment]，user.name=null
     */
    @Test
    public void addPropertySourceBeforeRefresh() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册当前类为 Configuration Class
        applicationContext.register(EnvironmentPropertySourceChangeDemo.class);
        // 在 Spring 应用上下文启动前调整 Environment 中的 PropertySource
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        // 获取 {@code MutablePropertySources}
        MutablePropertySources propertySources = environment.getPropertySources();
        Map<String, Object> source = new HashMap<>();
        source.put("user.name", "小贝");
        MapPropertySource propertySource = new MapPropertySource("customizedPropertySource", source);
        propertySources.addFirst(propertySource);
        // 启动 Spring 应用上下文
        applicationContext.refresh();
        EnvironmentPropertySourceChangeDemo demo = applicationContext.getBean(EnvironmentPropertySourceChangeDemo.class);
        System.out.println(demo.username);
        propertySources.forEach(ps ->
                System.out.printf("PropertySource[%s]，user.name=%s\n", ps.getName(), ps.getProperty("user.name")));
        // 关闭 Spring 应用上下文
        applicationContext.close();
    }

    /**
     * {@link Value} 无动态刷新能力
     *
     * <h2>运行结果：</h2>
     * 小贝
     * PropertySource[customizedPropertySource]，user.name=拿铁
     * PropertySource[systemProperties]，user.name=lenovo
     * PropertySource[systemEnvironment]，user.name=null
     */
    @Test
    public void addPropertySourceAfterRefresh() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册当前类为 Configuration Class
        applicationContext.register(EnvironmentPropertySourceChangeDemo.class);
        // 在 Spring 应用上下文启动前调整 Environment 中的 PropertySource
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        // 获取 {@code MutablePropertySources}
        MutablePropertySources propertySources = environment.getPropertySources();
        Map<String, Object> source = new HashMap<>();
        source.put("user.name", "小贝");
        MapPropertySource propertySource = new MapPropertySource("customizedPropertySource", source);
        propertySources.addFirst(propertySource);
        // 启动 Spring 应用上下文
        applicationContext.refresh();
        // 在 Spring 应用启动后更新 PropertySource，此时的更新不会改变 {@link Value}中的值
        source.put("user.name", "拿铁");
        EnvironmentPropertySourceChangeDemo demo = applicationContext.getBean(EnvironmentPropertySourceChangeDemo.class);
        System.out.println(demo.username);
        propertySources.forEach(ps ->
                System.out.printf("PropertySource[%s]，user.name=%s\n", ps.getName(), ps.getProperty("user.name")));
        // 关闭 Spring 应用上下文
        applicationContext.close();
    }
}
