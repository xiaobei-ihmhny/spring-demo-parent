package com.xiaobei.spring.demo;

import com.xiaobei.spring.demo.domain.QuestionsDomain;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.*;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.MethodMetadata;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/30 12:13
 */
public class BeanAnnotationDemo {

    /**
     * <h2>将 {@link ConfigurationClassPostProcessor} 的 BeanDefinition 添加到 BeanFactory 中</h2>
     * {@link AnnotationConfigApplicationContext#AnnotationConfigApplicationContext()}
     * {@link AnnotatedBeanDefinitionReader#AnnotatedBeanDefinitionReader(BeanDefinitionRegistry, Environment)}
     * {@code AnnotationConfigUtils.registerAnnotationConfigProcessors(this.registry);}
     * {@link AnnotationConfigUtils#registerAnnotationConfigProcessors(BeanDefinitionRegistry, java.lang.Object)}
     * {@code if (!registry.containsBeanDefinition(CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME))}
     *
     * <h2>执行 {@link ConfigurationClassPostProcessor} 中的
     * {@link ConfigurationClassPostProcessor#postProcessBeanDefinitionRegistry(BeanDefinitionRegistry)} 的后置处理</h2>
     *
     * {@link AbstractApplicationContext#invokeBeanFactoryPostProcessors(org.springframework.beans.factory.config.ConfigurableListableBeanFactory)}
     * {@link org.springframework.context.support.PostProcessorRegistrationDelegate#invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory, java.util.List)}
     * {@code invokeBeanDefinitionRegistryPostProcessors(currentRegistryProcessors, registry);}
     * {@link ConfigurationClassPostProcessor#postProcessBeanDefinitionRegistry(BeanDefinitionRegistry)}
     * {@link ConfigurationClassPostProcessor#processConfigBeanDefinitions(BeanDefinitionRegistry)}
     *      {@code parser.parse(candidates);}
     *          {@link ConfigurationClassParser#parse(java.util.Set)}
     *          {@link ConfigurationClassParser#parse(org.springframework.core.type.AnnotationMetadata, java.lang.String)}
     *              {@link ConfigurationClassParser#processConfigurationClass(org.springframework.context.annotation.ConfigurationClass)}
     *                  {@code sourceClass = doProcessConfigurationClass(configClass, sourceClass);}
     *                      {@link ConfigurationClassParser#doProcessConfigurationClass(org.springframework.context.annotation.ConfigurationClass, org.springframework.context.annotation.ConfigurationClassParser.SourceClass)}
     *                          {@code Set< MethodMetadata > beanMethods = retrieveBeanMethodMetadata(sourceClass);}
     *                          {@link ConfigurationClassParser#retrieveBeanMethodMetadata(org.springframework.context.annotation.ConfigurationClassParser.SourceClass)}
     *      {@code this.reader.loadBeanDefinitions(configClasses);}
     *          {@link ConfigurationClassBeanDefinitionReader#loadBeanDefinitions(java.util.Set)}
     *              {@link ConfigurationClassBeanDefinitionReader#loadBeanDefinitionsForConfigurationClass(org.springframework.context.annotation.ConfigurationClass, org.springframework.context.annotation.ConfigurationClassBeanDefinitionReader.TrackedConditionEvaluator)}
     *                  {@link loadBeanDefinitionsForBeanMethod(beanMethod);}
     *                      {@link ConfigurationClassBeanDefinitionReader#loadBeanDefinitionsForBeanMethod(org.springframework.context.annotation.BeanMethod)}
     *                          {@code if (metadata.isStatic()) {} 处理静态方法
     *                          {@code else { } 处理实例方法
     * @param args
     *
     *
     *
     */
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        applicationContext.register(BeanAnnotationDemo.class);
        // 启动 Spring 应用上下文
        applicationContext.refresh();
        QuestionsDomain domain = applicationContext.getBean(QuestionsDomain.class);
        System.out.println(domain);

        // 关闭 Spring 应用上下文
        applicationContext.close();
    }

    @Bean
    public  QuestionsDomain domain() {
        QuestionsDomain domain = new QuestionsDomain();
        domain.setId(248);
        domain.setName("加餐3 @Bean的处理流程是怎样的？");
        return domain;
    }
}
