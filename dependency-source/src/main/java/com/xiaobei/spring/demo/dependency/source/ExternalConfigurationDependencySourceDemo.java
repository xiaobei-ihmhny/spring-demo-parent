package com.xiaobei.spring.demo.dependency.source;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;

/**
 * TODO 此处的{@link Configuration}必须添加，不添加的时候无法正常读取到文件{@code /META-INF/default.properties}为什么？
 *
 * 示例外部化配置作为依赖来源
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-08-07 10:31:31
 */
@Configuration
@PropertySource(value = "classpath:/META-INF/default.properties", encoding = "GBK")
public class ExternalConfigurationDependencySourceDemo {

    /**
     * 后面的{@code :0}是配置默认值的意思，
     * 当无法获取user.id属性值时，会使用默认值
     */
    @Value("${user.id:0}")
    private Long id;

    @Value("${user.username:xiaobei-ihmhny}")
    private String username;

    @Value("${user.resource:classpath:/default.properties}")
    private Resource resource;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ExternalConfigurationDependencySourceDemo.class);
        applicationContext.refresh();
        ExternalConfigurationDependencySourceDemo bean = applicationContext.getBean(ExternalConfigurationDependencySourceDemo.class);
        System.out.println("bean.id = " + bean.id);
        System.out.println("bean.username = " + bean.username);
        System.out.println("bean.username = " + bean.resource);
        applicationContext.close();
    }
}