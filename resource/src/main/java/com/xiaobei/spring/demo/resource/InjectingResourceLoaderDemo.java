package com.xiaobei.spring.demo.resource;

import com.xiaobei.spring.demo.resource.utils.ResourceUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/16 21:35
 */
public class InjectingResourceLoaderDemo implements ResourceLoaderAware {


    /**
     * 方法一：通过Aware接口回调注入
     */
    private ResourceLoader resourceLoader;

    /**
     * 方法二：@Autowired 注入 ResourceLoader
     */
    @Autowired
    private ResourceLoader autowiredResourceLoader;

    /**
     * 注入 ApplicationContext 作为 ResourceLoader
     */
    @Autowired
    private AbstractApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        System.out.println("resourceLoader == autowiredResourceLoader：" + (resourceLoader == autowiredResourceLoader));
        System.out.println("resourceLoader == applicationContext：" + (resourceLoader == applicationContext));

    }

    /**
     * <h2>运行结果：</h2>
     * resourceLoader == autowiredResourceLoader：true
     * resourceLoader == applicationContext：true
     */
    @Test
    public void injectResource() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册当前类
        applicationContext.register(InjectingResourceLoaderDemo.class);
        // 启动应用上下文
        applicationContext.refresh();
        // 关闭应用上下文
        applicationContext.close();
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
