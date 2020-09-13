package com.xiaobei.spring.demo.bean.lifecycle;

import com.xiaobei.spring.demo.bean.lifecycle.domain.City;
import org.junit.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.support.*;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;

import java.util.Arrays;
import java.util.Properties;
import java.util.stream.Stream;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/9 14:53
 */
@SuppressWarnings("DuplicatedCode")
public class BeanMetadataConfigurationDemo {

    static class LifeCycleDomain {

        private Long id;

        private String name;

        private City city;

        public Long getId() {
            return id;
        }

        public LifeCycleDomain setId(Long id) {
            this.id = id;
            return this;
        }

        public String getName() {
            return name;
        }

        public LifeCycleDomain setName(String name) {
            this.name = name;
            return this;
        }

        public City getCity() {
            return city;
        }

        public LifeCycleDomain setCity(City city) {
            this.city = city;
            return this;
        }

        @Override
        public String toString() {
            return "LifeCycleDomain{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", city=" + city +
                    '}';
        }
    }

    /**
     * 不指定编码读取{@link Properties}配置
     *
     * <p>运行结果：
     * 当前找到的bean的名称为：[life]
     * LifeCycleDomain{id=2, name='Spring Bean元信息配置阶段-properties', city=BIEJING}
     */
    @Test
    public void resourceOrientedByProperties() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        PropertiesBeanDefinitionReader reader = new PropertiesBeanDefinitionReader(beanFactory);
        String location = "META-INF/life-cycle-metadata-domain.properties";
        reader.loadBeanDefinitions(location);
        // 依赖查找
        String[] names = beanFactory.getBeanDefinitionNames();
        System.out.println("当前找到的bean的名称为：" + Arrays.toString(names));
        ObjectProvider<LifeCycleDomain> lifeCycleDomain = beanFactory.getBeanProvider(LifeCycleDomain.class);
        System.out.println(lifeCycleDomain.getIfAvailable());
    }
    /**
     * 指定编码读取{@link Properties}配置
     *
     * <p>运行结果：
     * 当前找到的bean的名称为：[life]
     * LifeCycleDomain{id=2, name='Spring Bean元信息配置阶段-properties', city=BIEJING}
     */
    @Test
    public void resourceOrientedByPropertiesSpecifyEncoding() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        PropertiesBeanDefinitionReader reader = new PropertiesBeanDefinitionReader(beanFactory);
        String location = "META-INF/life-cycle-metadata-domain.properties";
        Resource resource = new ClassPathResource(location);
        EncodedResource encodedResource = new EncodedResource(resource, "UTF-8");
        reader.loadBeanDefinitions(encodedResource);
        // 依赖查找
        String[] names = beanFactory.getBeanDefinitionNames();
        System.out.println("当前找到的bean的名称为：" + Arrays.toString(names));
        ObjectProvider<LifeCycleDomain> lifeCycleDomain = beanFactory.getBeanProvider(LifeCycleDomain.class);
        System.out.println(lifeCycleDomain.getIfAvailable());
    }

    /**
     * 通过XML的方式配置元信息
     *
     * <p>运行结果：
     * 当前找到的bean的名称为：[lifeCycleDomain]
     * LifeCycleDomain{id=2, name='Spring Bean元信息配置阶段-XML', city=BIEJING}
     */
    @Test
    public void resourceOrientedByXml() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader  = new XmlBeanDefinitionReader(beanFactory);
        String location = "META-INF/life-cycle-metadata-domain.xml";
        reader.loadBeanDefinitions(location);
        // 依赖查找
        String[] names = beanFactory.getBeanDefinitionNames();
        System.out.println("当前找到的bean的名称为：" + Arrays.toString(names));
        ObjectProvider<LifeCycleDomain> lifeCycleDomain = beanFactory.getBeanProvider(LifeCycleDomain.class);
        System.out.println(lifeCycleDomain.getIfAvailable());
    }

    /**
     * TODO xml文件中可以指定相关的编码，故在加载时不需要再额外指定！！！
     * 通过XML的方式配置元信息（指定编码）
     *
     * <p>运行结果：</p>
     * 当前找到的bean的名称为：[lifeCycleDomain]
     * LifeCycleDomain{id=2, name='Spring Bean元信息配置阶段-XML', city=BIEJING}
     */
    @Test
    public void resourceOrientedByXmlSpecifyEncoding() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader  = new XmlBeanDefinitionReader(beanFactory);
        String location = "META-INF/life-cycle-metadata-domain.xml";
        Resource resource = new ClassPathResource(location);
        EncodedResource encodedResource = new EncodedResource(resource, "UTF-8");
        reader.loadBeanDefinitions(encodedResource);
        // 依赖查找
        String[] names = beanFactory.getBeanDefinitionNames();
        System.out.println("当前找到的bean的名称为：" + Arrays.toString(names));
        ObjectProvider<LifeCycleDomain> lifeCycleDomain = beanFactory.getBeanProvider(LifeCycleDomain.class);
        System.out.println(lifeCycleDomain.getIfAvailable());
    }

    /**
     * 面向注解配置元信息
     * bean的名称生成来自于 {@link BeanNameGenerator}，注解实现为 {@link AnnotationBeanNameGenerator}
     * @see AnnotatedBeanDefinitionReader
     * @see BeanNameGenerator
     * @see AnnotationBeanNameGenerator
     *
     * <p>运行结果：</p>
     * org.springframework.context.annotation.internalConfigurationAnnotationProcessor
     * org.springframework.context.annotation.internalAutowiredAnnotationProcessor
     * org.springframework.context.annotation.internalCommonAnnotationProcessor
     * org.springframework.context.event.internalEventListenerProcessor
     * org.springframework.context.event.internalEventListenerFactory
     * beanMetadataConfigurationDemo.LifeCycleDomain
     * LifeCycleDomain{id=3, name='面向注解', city=BIEJING}
     */
    @Test
    public void annotationOriented() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        AnnotatedBeanDefinitionReader reader  = new AnnotatedBeanDefinitionReader(beanFactory);
        // 注解指定的类型的bean
        reader.registerBean(LifeCycleDomain.class, () -> new LifeCycleDomain()
                .setId(3L)
                .setName("面向注解")
                .setCity(City.BIEJING));
        // 依赖查找
        String[] names = beanFactory.getBeanDefinitionNames();
        Stream.of(names).forEach(System.out::println);
        ObjectProvider<LifeCycleDomain> lifeCycleDomain = beanFactory.getBeanProvider(LifeCycleDomain.class);
        System.out.println(lifeCycleDomain.getIfAvailable());
    }

    /**
     * 面向api方式配置元信息
     *
     * <p>运行结果：</p>
     * 当前找到的bean的名称为：[lifeCycleDomain]
     * LifeCycleDomain类型的bean为：LifeCycleDomain{id=4, name='面向api', city=BIEJING}
     */
    @Test
    public void apiOriented() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(LifeCycleDomain.class);
        // 配置元信息
        builder.addPropertyValue("id", 4L);
        builder.addPropertyValue("name", "面向api");
        builder.addPropertyValue("city", City.BIEJING);
        AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();
        beanFactory.registerBeanDefinition("lifeCycleDomain", beanDefinition);
        // 依赖查找
        String[] names = beanFactory.getBeanDefinitionNames();
        System.out.println("当前找到的bean的名称为：" + Arrays.toString(names));
        ObjectProvider<LifeCycleDomain> lifeCycleDomain = beanFactory.getBeanProvider(LifeCycleDomain.class);
        System.out.println("LifeCycleDomain类型的bean为："+ lifeCycleDomain.getIfAvailable());
    }
}
