package com.xiaobei.spring.demo.dependency.source;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.*;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Collection;

/**
 * 此处的{@link Configuration}必须添加，不添加的时候无法正常读取到文件{@code /META-INF/default.properties}，原因如下：
 * <h2>{@link PropertySource @PropertySource} 必需结合 {@link Configuration @Configuration} 的原因</h2>
 * {@link AbstractApplicationContext#refresh()}
 *  {@link AbstractApplicationContext#invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory)}
 *   {@link org.springframework.context.support.PostProcessorRegistrationDelegate#invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory, java.util.List)}
 *    {@link PostProcessorRegistrationDelegate#invokeBeanDefinitionRegistryPostProcessors(Collection, BeanDefinitionRegistry)}
 *     {@link ConfigurationClassPostProcessor#postProcessBeanDefinitionRegistry(BeanDefinitionRegistry)}
 *      {@link ConfigurationClassPostProcessor#processConfigBeanDefinitions(org.springframework.beans.factory.support.BeanDefinitionRegistry)}
 *       {@link ConfigurationClassParser#parse(java.util.Set)}
 *        {@link ConfigurationClassParser#parse(AnnotationMetadata, java.lang.String)}
 *         {@link ConfigurationClassParser#processConfigurationClass(org.springframework.context.annotation.ConfigurationClass)}
 *           {@link ConfigurationClassParser#doProcessConfigurationClass(org.springframework.context.annotation.ConfigurationClass, org.springframework.context.annotation.ConfigurationClassParser.SourceClass)}
 *           该类中有对注解 {@link PropertySource} 和 {@link PropertySources} 的处理逻辑
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