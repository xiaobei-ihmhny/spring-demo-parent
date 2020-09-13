package com.xiaobei.spring.demo.bean.lifecycle;

import com.xiaobei.spring.demo.bean.lifecycle.domain.City;
import org.junit.Test;
import org.springframework.beans.TypeConverter;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/9 19:50
 */
public class MergedBeanDefinitionDemo {

    /**
     * @see DefaultListableBeanFactory#resolveDependency(DependencyDescriptor, java.lang.String, java.util.Set, TypeConverter)
     * @see ConfigurableBeanFactory#getMergedBeanDefinition(java.lang.String)
     * @see AbstractBeanFactory#getMergedBeanDefinition(java.lang.String)
     * <p>
     * TODO 需要再读！！！
     * 实际执行操作逻辑的位置：
     * @see AbstractBeanFactory#getMergedBeanDefinition(java.lang.String, BeanDefinition, BeanDefinition)
     *
     * <p>运行结果：</p>
     * LifeCycleDomain{id=2, name='XML配置', city=BIEJING}
     * SuperLifeCycleDomain{child='子类', number=null} LifeCycleDomain{id=2, name='XML配置', city=BIEJING}
     */
    @Test
    public void mergedBeanDefinitionByXml() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        String location = "META-INF/life-cycle-merged-domain.xml";
        reader.loadBeanDefinitions(location);
        // 依赖查找
        LifeCycleDomain lifeCycleDomain = beanFactory.getBean("lifeCycleDomain", LifeCycleDomain.class);
        System.out.println(lifeCycleDomain);
        LifeCycleDomain superLifeCycle = beanFactory.getBean("superLifeCycle", LifeCycleDomain.class);
        System.out.println(superLifeCycle);
    }

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

    static class SuperLifeCycleDomain extends LifeCycleDomain {

        private String child;

        private Long number;

        public String getChild() {
            return child;
        }

        public SuperLifeCycleDomain setChild(String child) {
            this.child = child;
            return this;
        }

        public Long getNumber() {
            return number;
        }

        public SuperLifeCycleDomain setNumber(Long number) {
            this.number = number;
            return this;
        }

        @Override
        public String toString() {
            return "SuperLifeCycleDomain{" +
                    "child='" + child + '\'' +
                    ", number=" + number +
                    "} " + super.toString();
        }
    }

    @Test
    public void mergedBeanDefinitionByAnnotation() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(MergedBeanDefinitionConfig.class);
        applicationContext.refresh();
        // 依赖查找
        LifeCycleDomain lifeCycleDomain = applicationContext.getBean("lifeCycleDomain", LifeCycleDomain.class);
        System.out.println(lifeCycleDomain);
        LifeCycleDomain superLifeCycle = applicationContext.getBean("superLifeCycle", LifeCycleDomain.class);
        System.out.println(superLifeCycle);
        applicationContext.close();
    }

    static class MergedBeanDefinitionConfig {

        @Bean
        public LifeCycleDomain lifeCycleDomain() {
            LifeCycleDomain lifeCycleDomain = new LifeCycleDomain();
            lifeCycleDomain.setId(20200913L);
            lifeCycleDomain.setName("naTie");
            lifeCycleDomain.setCity(City.BIEJING);
            return lifeCycleDomain;
        }

        @Bean
        public LifeCycleDomain superLifeCycle() {
            SuperLifeCycleDomain superLifeCycleDomain = new SuperLifeCycleDomain();
            superLifeCycleDomain.setChild("注解子类");
            return superLifeCycleDomain;
        }
    }
}
