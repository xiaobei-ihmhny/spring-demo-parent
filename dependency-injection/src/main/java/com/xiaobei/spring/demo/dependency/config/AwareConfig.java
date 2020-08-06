package com.xiaobei.spring.demo.dependency.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.*;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringValueResolver;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-08-05 13:27:27
 */
public class AwareConfig implements BeanFactoryAware, ApplicationContextAware,
        EnvironmentAware, ResourceLoaderAware, BeanClassLoaderAware,
        BeanNameAware, MessageSourceAware, ApplicationEventPublisherAware,
        EmbeddedValueResolverAware {
    private ClassLoader classLoader;

    private BeanFactory beanFactory;

    private String name;

    private ApplicationContext applicationContext;

    private ApplicationEventPublisher applicationEventPublisher;

    private StringValueResolver resolver;

    private Environment environment;

    private MessageSource messageSource;

    private ResourceLoader resourceLoader;

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public String getName() {
        return name;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public ApplicationEventPublisher getApplicationEventPublisher() {
        return applicationEventPublisher;
    }

    public StringValueResolver getResolver() {
        return resolver;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public MessageSource getMessageSource() {
        return messageSource;
    }

    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setBeanName(String name) {
        this.name = name;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public String toString() {
        return "AwareConfig{" +
                "classLoader=" + classLoader +
                ", beanFactory=" + beanFactory +
                ", name='" + name + '\'' +
                ", applicationContext=" + applicationContext +
                ", applicationEventPublisher=" + applicationEventPublisher +
                ", resolver=" + resolver +
                ", environment=" + environment +
                ", messageSource=" + messageSource +
                ", resourceLoader=" + resourceLoader +
                '}';
    }
}