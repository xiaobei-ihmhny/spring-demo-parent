package com.xiaobei.spring.demo.bean.lifecycle;

import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringValueResolver;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * {@link org.springframework.beans.factory.Aware} 相关实现的注入阶段
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/9/15 21:38
 */
public class BeanAwareInjectLifeCycleDemo {

    static class AwareHolder implements BeanNameAware, BeanClassLoaderAware,
            BeanFactoryAware, EnvironmentAware, EmbeddedValueResolverAware,
            ResourceLoaderAware, ApplicationEventPublisherAware,
            MessageSourceAware, ApplicationContextAware {

        private String beanName;

        private ClassLoader classLoader;

        private BeanFactory beanFactory;

        private Environment environment;

        private StringValueResolver resolver;

        private ResourceLoader resourceLoader;

        private ApplicationEventPublisher applicationEventPublisher;

        private MessageSource messageSource;

        private ApplicationContext applicationContext;

        private final AtomicInteger count =new AtomicInteger(0);

        @Override
        public void setBeanName(String beanName) {
            this.beanName = beanName;
            System.out.printf("beanName 加载顺序排名, %s\n", count.addAndGet(1));
        }

        @Override
        public void setBeanClassLoader(ClassLoader classLoader) {
            this.classLoader = classLoader;
            System.out.printf("classLoader 加载顺序排名: %s\n", count.addAndGet(1));
        }

        @Override
        public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
            this.beanFactory = beanFactory;
            System.out.printf("beanFactory 加载顺序排名: %s\n", count.addAndGet(1));
        }

        @Override
        public void setEnvironment(Environment environment) {
            this.environment = environment;
            System.out.printf("environment 加载顺序排名: %s\n", count.addAndGet(1));
        }

        @Override
        public void setEmbeddedValueResolver(StringValueResolver resolver) {
            this.resolver = resolver;
            System.out.printf("resolver 加载顺序排名: %s\n", count.addAndGet(1));
        }

        @Override
        public void setResourceLoader(ResourceLoader resourceLoader) {
            this.resourceLoader = resourceLoader;
            System.out.printf("resourceLoader 加载顺序排名: %s\n", count.addAndGet(1));
        }

        @Override
        public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
            this.applicationEventPublisher = applicationEventPublisher;
            System.out.printf("applicationEventPublisher 加载顺序排名: %s\n", count.addAndGet(1));
        }

        @Override
        public void setMessageSource(MessageSource messageSource) {
            this.messageSource = messageSource;
            System.out.printf("messageSource 加载顺序排名: %s\n", count.addAndGet(1));
        }

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            this.applicationContext = applicationContext;
            System.out.printf("applicationContext 加载顺序排名: %s\n", count.addAndGet(1));
        }

        @Override
        public String toString() {
            return "AwareHolder{\n" +
                    "beanName='" + beanName + '\'' +
                    ", \n  classLoader=" + classLoader +
                    ", \n  beanFactory=" + beanFactory +
                    ", \n  environment=" + environment +
                    ", \n  resolver=" + resolver +
                    ", \n  resourceLoader=" + resourceLoader +
                    ", \n  applicationEventPublisher=" + applicationEventPublisher +
                    ", \n  messageSource=" + messageSource +
                    ", \n  applicationContext=" + applicationContext +
                    "\n  }";
        }
    }

    /**
     * <h2>相关的回调流程如下：</h2>
     * 整个调用链路如下：
     * {@link AbstractBeanFactory#getBean(java.lang.String, java.lang.Class)}
     * {@link AbstractBeanFactory#doGetBean(java.lang.String, java.lang.Class, java.lang.Object[], boolean)}
     * {@link AbstractBeanFactory#createBean(java.lang.String, RootBeanDefinition, java.lang.Object[])}
     * {@link AbstractAutowireCapableBeanFactory#doCreateBean(java.lang.String, RootBeanDefinition, java.lang.Object[])}
     * {@code exposedObject = initializeBean(beanName, exposedObject, mbd);}
     * {@link AbstractAutowireCapableBeanFactory#initializeBean(String, Object, RootBeanDefinition)}
     * {@code invokeAwareMethods(beanName, bean);}
     * {@link AbstractAutowireCapableBeanFactory#invokeAwareMethods(String, Object)}
     *  该方法中会依次的调用 {@link BeanNameAware}、{@link BeanClassLoaderAware} 和 {@link BeanFactoryAware}，
     *  这也就是结果中显示的加载顺序。
     *
     * <h2>运行结果说明：</h2>
     * {@link BeanFactory} 是一个基础的 IoC 容器，不具备 {@link ApplicationContext} 所特有的 aop、事件、国际化等面向企业的特性
     *
     * <h2>运行结果：</h2>
     * beanName 加载顺序排名: 1
     * classLoader 加载顺序排名: 2
     * beanFactory 加载顺序排名: 3
     * AwareHolder{
     * beanName='awareHolder',
     *   classLoader=sun.misc.Launcher$AppClassLoader@18b4aac2,
     *   beanFactory=org.springframework.beans.factory.support.DefaultListableBeanFactory@29774679: defining beans [awareHolder]; root of factory hierarchy,
     *   environment=null,
     *   resolver=null,
     *   resourceLoader=null,
     *   applicationEventPublisher=null,
     *   messageSource=null,
     *   applicationContext=null
     *   }
     */
    @Test
    public void defaultListableBeanFactoryAware() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        String location = "META-INF/life-cycle-aware-domain.xml";
        reader.loadBeanDefinitions(location);
        AwareHolder awareHolder = beanFactory.getBean("awareHolder", AwareHolder.class);
        System.out.println(awareHolder);
    }

    /**
     *
     * <h2>相关的回调流程如下：</h2>
     * 整个调用链路如下：
     * {@link AbstractBeanFactory#getBean(java.lang.String, java.lang.Class)}
     * {@link AbstractBeanFactory#doGetBean(java.lang.String, java.lang.Class, java.lang.Object[], boolean)}
     * {@link AbstractBeanFactory#createBean(java.lang.String, RootBeanDefinition, java.lang.Object[])}
     * {@link AbstractAutowireCapableBeanFactory#doCreateBean(java.lang.String, RootBeanDefinition, java.lang.Object[])}
     * {@code exposedObject = initializeBean(beanName, exposedObject, mbd);}
     * {@link AbstractAutowireCapableBeanFactory#initializeBean(String, Object, RootBeanDefinition)}
     * {@code invokeAwareMethods(beanName, bean);}
     * {@link AbstractAutowireCapableBeanFactory#invokeAwareMethods(String, Object)}
     *  该方法中会依次的调用 {@link BeanNameAware}、{@link BeanClassLoaderAware} 和 {@link BeanFactoryAware}，
     *  这也就是结果中显示的前三个的加载顺序。
     *  <h3>以下是 {@link ApplicationContext} 相比 {@link BeanFactory} 多的东西</h3>
     * {@code wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);}
     * {@link AbstractAutowireCapableBeanFactory#applyBeanPostProcessorsBeforeInitialization(java.lang.Object, java.lang.String)}
     * {@link org.springframework.context.support.ApplicationContextAwareProcessor#postProcessBeforeInitialization(java.lang.Object, java.lang.String)}
     * {@code invokeAwareInterfaces(bean);}
     * {@link org.springframework.context.support.ApplicationContextAwareProcessor#invokeAwareInterfaces(java.lang.Object)}
     *  该方法中会依次的调用 {@link EnvironmentAware}、{@link EmbeddedValueResolverAware}、{@link ResourceLoaderAware}、
     *  {@link ApplicationEventPublisherAware}、{@link MessageSourceAware} 和 {@link ApplicationContextAware}
     *  这也就是结果中显示的第三个之后的加载顺序。
     *
     * <h2>{@link ApplicationContext} 相比 {@link BeanFactory} 多出的aware的原因如下：</h2>
     * {@link AbstractApplicationContext#refresh()}
     *  {@code prepareBeanFactory(beanFactory); }
     *  {@link AbstractApplicationContext#prepareBeanFactory(ConfigurableListableBeanFactory)}
     *   {@code beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor (this));}
     *   该行代码添加了 Spring 内置的相关aware接口的后置处理逻辑
     *   {@link org.springframework.context.support.ApplicationContextAwareProcessor#postProcessBeforeInitialization(java.lang.Object, java.lang.String)}
     *    {@code invokeAwareInterfaces(bean);}
     *    {@link org.springframework.context.support.ApplicationContextAwareProcessor#invokeAwareInterfaces(java.lang.Object)}
     *
     * <h2>运行结果：</h2>
     * beanName 加载顺序排名, 1
     * classLoader 加载顺序排名: 2
     * beanFactory 加载顺序排名: 3
     * environment 加载顺序排名: 4
     * resolver 加载顺序排名: 5
     * resourceLoader 加载顺序排名: 6
     * applicationEventPublisher 加载顺序排名: 7
     * messageSource 加载顺序排名: 8
     * applicationContext 加载顺序排名: 9
     * AwareHolder{
     * beanName='beanAwareInjectLifeCycleDemo.AwareHolder',
     *   classLoader=sun.misc.Launcher$AppClassLoader@18b4aac2,
     *   beanFactory=org.springframework.beans.factory.support.DefaultListableBeanFactory@17579e0f: defining beans [org.springframework.context.annotation.internalConfigurationAnnotationProcessor,org.springframework.context.annotation.internalAutowiredAnnotationProcessor,org.springframework.context.annotation.internalCommonAnnotationProcessor,org.springframework.context.event.internalEventListenerProcessor,org.springframework.context.event.internalEventListenerFactory,beanAwareInjectLifeCycleDemo.AwareHolder]; root of factory hierarchy,
     *   environment=StandardEnvironment {activeProfiles=[], defaultProfiles=[default], propertySources=[PropertiesPropertySource {name='systemProperties'}, SystemEnvironmentPropertySource {name='systemEnvironment'}]},
     *   resolver=org.springframework.beans.factory.config.EmbeddedValueResolver@481a15ff,
     *   resourceLoader=org.springframework.context.annotation.AnnotationConfigApplicationContext@75828a0f, started on Tue Sep 15 22:02:58 CST 2020,
     *   applicationEventPublisher=org.springframework.context.annotation.AnnotationConfigApplicationContext@75828a0f, started on Tue Sep 15 22:02:58 CST 2020,
     *   messageSource=org.springframework.context.annotation.AnnotationConfigApplicationContext@75828a0f, started on Tue Sep 15 22:02:58 CST 2020,
     *   applicationContext=org.springframework.context.annotation.AnnotationConfigApplicationContext@75828a0f, started on Tue Sep 15 22:02:58 CST 2020
     *   }
     *
     * <h2>从运行结果：</h2>
     *
     */
    @Test
    public void applicationContextAware() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AwareHolder.class);
        applicationContext.refresh();
        AwareHolder awareHolder = applicationContext.getBean(AwareHolder.class);
        System.out.println(awareHolder);
        applicationContext.close();
    }
}
