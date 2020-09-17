package com.xiaobei.spring.demo.configuration.metadata;

import com.xiaobei.spring.demo.configuration.metadata.domain.PropertyValuesDomain;
import org.junit.Test;
import org.springframework.beans.BeanMetadataAttributeAccessor;
import org.springframework.beans.BeanMetadataElement;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.core.AttributeAccessor;
import org.springframework.util.ObjectUtils;

/**
 * Bean 属性上下文 {@link AttributeAccessor} 与 {@link BeanMetadataElement} 示例
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/13 20:38
 * @see AttributeAccessor
 * @see BeanMetadataElement
 */
public class BeanConfigurationMetadataDemo {

    /**
     * <p>运行结果：</p>
     * PropertyValuesDomain{id=108, name='xiaobei'}
     *
     * {@link BeanMetadataElement#getSource()} 和 {@link AttributeAccessor}
     * 主要是辅助BeanDefinition做扩展并在Bean的生命周期中做相应处理
     *
     * 其中：{@link AttributeAccessor#setAttribute(java.lang.String, java.lang.Object)}
     * 不会影响Bean的 populate、initialize
     *
     * @see AttributeAccessor#setAttribute(java.lang.String, java.lang.Object)
     * @see BeanMetadataElement#getSource()
     * @see BeanMetadataAttributeAccessor#setSource(java.lang.Object)
     * @see BeanMetadataAttributeAccessor#getSource()
     */
    @Test
    public void propertyValues() {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(PropertyValuesDomain.class);
        builder.addPropertyValue("id", 108L);
        builder.addPropertyValue("name", "108 Spring Bean属性元信息：PropertyValues");
        AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();
        // org.springframework.core.AttributeAccessor.setAttribute
        beanDefinition.setAttribute("name","xiaobei");
        // 来源 org.springframework.beans.BeanMetadataAttributeAccessor.setSource
        beanDefinition.setSource(BeanConfigurationMetadataDemo.class);
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // BeanPostProcessor
        beanFactory.addBeanPostProcessor(new BeanPostProcessor() {
            // Spring Bean 初始化后阶段
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if(ObjectUtils.nullSafeEquals("propertyValuesDomain", beanName)
                        && PropertyValuesDomain.class.equals(bean.getClass())) {
                    BeanDefinition domainBeanDefinition = beanFactory.getBeanDefinition("propertyValuesDomain");
                    String name = (String) domainBeanDefinition.getAttribute("name");
                    // 可以根据 {@code BeanMetadataAttributeAccessor#getSource()}的结果做一定处理
                    Object source = domainBeanDefinition.getSource();
                    if(BeanConfigurationMetadataDemo.class.equals(source)) {
                        System.out.println("当前BeanDefinition的来源为：" + source);
                        PropertyValuesDomain domain = (PropertyValuesDomain) bean;
                        domain.setName(name);
                        return domain;
                    }
                }
                return null;
            }
        });
        beanFactory.registerBeanDefinition("propertyValuesDomain", beanDefinition);
        PropertyValuesDomain domain = beanFactory.getBean(PropertyValuesDomain.class);
        System.out.println(domain);
    }
}
