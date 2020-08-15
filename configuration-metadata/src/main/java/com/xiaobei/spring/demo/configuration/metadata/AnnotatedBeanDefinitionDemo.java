package com.xiaobei.spring.demo.configuration.metadata;

import org.junit.Test;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionCustomizer;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Arrays;
import java.util.function.Supplier;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/15 20:25
 */
public class AnnotatedBeanDefinitionDemo {

    /**
     *
     * <h2>运行结果：</h2>
     *
     * com.xiaobei.spring.demo.configuration.metadata.AnnotatedBeanDefinitionDemo@77cd7a0
     * [annotatedBeanDefinitionDemo]
     *
     * <h2>主要内容：</h2>
     * 条件评估：{@link org.springframework.context.annotation.ConditionEvaluator}
     * Bean范围解析：{@link org.springframework.context.annotation.ScopeMetadataResolver}
     * Bean处理：{@link AnnotationConfigUtils#processCommonDefinitionAnnotations(AnnotatedBeanDefinition)}
     * Bean注册：{@link BeanDefinitionReaderUtils#registerBeanDefinition(BeanDefinitionHolder, BeanDefinitionRegistry)}
     *
     *
     * <h2>步骤：</h2>
     * {@link AnnotatedBeanDefinitionReader#register(java.lang.Class[])} 注册
     * {@link AnnotatedBeanDefinitionReader#doRegisterBean(Class, String, Class[], Supplier, BeanDefinitionCustomizer[])} 真正的注册逻辑
     * {@link org.springframework.context.annotation.ConditionEvaluator#shouldSkip(AnnotatedTypeMetadata)}
     * 对Bean的{@link Conditional}条件进行评估
     * 其中的 {@code AnnotationConfigUtils.processCommonDefinitionAnnotations(abd);}是对元信息的处理
     *
     * <h2>相关参考：</h2>
     * @see org.springframework.context.annotation.ConditionEvaluator#shouldSkip(AnnotatedTypeMetadata)
     * @see AnnotationConfigUtils#processCommonDefinitionAnnotations(AnnotatedBeanDefinition)
     * @see BeanDefinitionReaderUtils#registerBeanDefinition(BeanDefinitionHolder, BeanDefinitionRegistry)
     *
     * @see
     */
    @Test
    public void annotatedBeanDefinitionReader() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(beanFactory);
        // 注解当前类
        reader.register(AnnotatedBeanDefinitionDemo.class);
        // 依赖查找
        AnnotatedBeanDefinitionDemo bean = beanFactory.getBean(AnnotatedBeanDefinitionDemo.class);
        System.out.println(bean);
        // 当前所有的bean的名称
        String[] demoBeanNames = beanFactory.getBeanNamesForType(AnnotatedBeanDefinitionDemo.class);
        System.out.println(Arrays.toString(demoBeanNames));
    }
}
