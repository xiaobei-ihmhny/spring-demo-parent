package com.xiaobei.spring.demo.configuration.metadata;

import com.xiaobei.spring.demo.configuration.metadata.domain.ResourceDomain;
import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.*;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.EncodedResource;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/14 20:56
 */
public class PropertiesBeanDefinitionDemo {

    /**
     * <h2>基于Properties资源装载Spring Bean配置元信息</h2>
     * 
     * <p>运行结果：</p>
     * =============================================================================================
     * ResourceDomain{byteType=1, basicType=true, name='基于Properties资源装载Spring Bean配置元信息-PropertiesBeanDefinition', id=111, enumType=RUYANG, resourceLocation=class path resource [META-INF/configuration-for-xml.xml], clazz=class com.xiaobei.spring.demo.configuration.metadata.domain.ResourceDomain}
     * =============================================================================================
     * {@link AbstractBeanDefinitionReader#loadBeanDefinitions(String, java.util.Set)}
     * {@link PropertiesBeanDefinitionReader#loadBeanDefinitions(org.springframework.core.io.Resource)}
     * {@link PropertiesBeanDefinitionReader#loadBeanDefinitions(EncodedResource, String)}
     * {@link PropertiesBeanDefinitionReader#registerBeanDefinitions(java.util.Map, String, String)}
     * {@link PropertiesBeanDefinitionReader#registerBeanDefinition(String, java.util.Map, String, String)}
     * {@code registerBeanDefinition(beanName, map, prefix + beanName, resourceDescription);}
     * {@link PropertiesBeanDefinitionReader#registerBeanDefinition(String, java.util.Map, String, String)}
     * 最终通过 {@link BeanDefinitionReaderUtils#createBeanDefinition(String, String, ClassLoader)} 创建 {@link BeanDefinition}
     * 并通过 {@link BeanDefinitionRegistry#registerBeanDefinition(String, BeanDefinition)} 来注册相关的 {@link BeanDefinition}
     */
    @Test
    public void propertiesBeanDefinitionReader() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        PropertiesBeanDefinitionReader reader = new PropertiesBeanDefinitionReader(beanFactory);
        String location = "classpath:/META-INF/configuration-for-properties.properties";
        ResourceLoader loader = new DefaultResourceLoader();
        Resource resource = loader.getResource(location);
        // 指定properties文件编码，以防出现乱码
        EncodedResource encodedResource = new EncodedResource(resource, "UTF-8");
        reader.loadBeanDefinitions(encodedResource);
        // 依赖查找
        ResourceDomain bean = beanFactory.getBean("resourceDomain", ResourceDomain.class);
        System.out.println(bean);
    }
}
