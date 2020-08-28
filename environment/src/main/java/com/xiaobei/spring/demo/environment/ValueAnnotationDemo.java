package com.xiaobei.spring.demo.environment;

import org.springframework.beans.PropertyValues;
import org.springframework.beans.TypeConverter;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.QualifierAnnotationAutowireCandidateResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/28 20:52
 */
public class ValueAnnotationDemo {

    @Value("${user.name}")
    private String username;

    /**
     * TODO 其中 {@link DefaultListableBeanFactory#getAutowireCandidateResolver()} 获取到的对象为什么是 {@link ContextAnnotationAutowireCandidateResolver}
     *
     * <h2>运行结果：</h2>
     * lenovo
     *
     * @param args
     *
     * {@link AutowiredAnnotationBeanPostProcessor.AutowiredFieldElement#inject(Object, String, PropertyValues)}
     * {@link DefaultListableBeanFactory#resolveDependency(DependencyDescriptor, java.lang.String, java.util.Set, TypeConverter)}
     * {@link DefaultListableBeanFactory#doResolveDependency(DependencyDescriptor, java.lang.String, java.util.Set, TypeConverter)}
     * {@code Object value = getAutowireCandidateResolver().getSuggestedValue(descriptor);}
     * {@link ContextAnnotationAutowireCandidateResolver}
     * {@link QualifierAnnotationAutowireCandidateResolver#getSuggestedValue(DependencyDescriptor)}
     * {@link QualifierAnnotationAutowireCandidateResolver#findValue(java.lang.annotation.Annotation[])}
     * {@link QualifierAnnotationAutowireCandidateResolver#extractValue(org.springframework.core.annotation.AnnotationAttributes)}
     */
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        applicationContext.register(ValueAnnotationDemo.class);
        applicationContext.refresh();
        ValueAnnotationDemo annotationDemo = applicationContext.getBean(ValueAnnotationDemo.class);
        System.out.println(annotationDemo.username);

        applicationContext.close();
    }
}
