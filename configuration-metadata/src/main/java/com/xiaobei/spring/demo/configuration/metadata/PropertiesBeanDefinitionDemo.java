package com.xiaobei.spring.demo.configuration.metadata;

import com.xiaobei.spring.demo.configuration.metadata.domain.ResourceDomain;
import org.junit.Test;
import org.springframework.beans.factory.support.AbstractBeanDefinitionReader;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
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
     * 基于Properties资源装载Spring Bean配置元信息
     * 运行结果：
     * =============================================================================================
     * ResourceDomain{byteType=1, basicType=true, name='基于Properties资源装载Spring Bean配置元信息-PropertiesBeanDefinition', id=111, enumType=RUYANG, resourceLocation=class path resource [META-INF/configuration-for-xml.xml], clazz=class com.xiaobei.spring.demo.configuration.metadata.domain.ResourceDomain}
     * =============================================================================================
     * @see AbstractBeanDefinitionReader#loadBeanDefinitions(java.lang.String, java.util.Set)
     * @see PropertiesBeanDefinitionReader#loadBeanDefinitions(org.springframework.core.io.Resource)
     * @see PropertiesBeanDefinitionReader#loadBeanDefinitions(EncodedResource, java.lang.String)
     * @see PropertiesBeanDefinitionReader#registerBeanDefinitions(java.util.Map, java.lang.String, java.lang.String)
     * @see PropertiesBeanDefinitionReader#registerBeanDefinition(java.lang.String, java.util.Map, java.lang.String, java.lang.String)
     *
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
