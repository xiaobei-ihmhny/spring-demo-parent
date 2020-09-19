package com.xiaobei.spring.demo.configuration.metadata.yaml;

import com.xiaobei.spring.demo.configuration.metadata.domain.ResourceDomain;
import org.junit.Test;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.util.Map;
import java.util.Properties;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/16 10:22
 */
public class XmlBasedYamlPropertySourceDemo {

    /**
     * 通过xml文件的方式来读取yaml文件
     *
     * <h2>运行结果：</h2>
     * {domain={id=121, name=xiaobei, enumType=LUOYANG}, user={name=小贝}}
     *
     * <h2>相关资料</h2>
     * @see YamlMapFactoryBean
     *
     * <h2>疑问：</h2>
     * 其中名称为“yamlMap”的bean的类型为什么是 {@link java.util.LinkedHashMap}， 而不是{@link YamlMapFactoryBean}呢？？？
     *
     * <h2>疑问解答：</h2>
     * 因为 {@link YamlMapFactoryBean} 实现了 {@link FactoryBean<Map<String, Object>>} 故其实际对象为 {@link Map}
     */
    @Test
    public void yamlPropertyReader() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        String location = "classpath:/META-INF/yaml-property-source-context.xml";
        reader.loadBeanDefinitions(location);
        // 依赖查找
        Map<String, Object> map = beanFactory.getBean("yamlMap", Map.class);
        System.out.println(map);
    }

    /**
     * 配置 {@link PropertySourcesPlaceholderConfigurer} 的资源为 yaml 文件
     * 需要配合 {@link YamlPropertiesFactoryBean} 来将 yaml 文件解析成 {@link Properties} 文件
     *
     * <h2>运行结果：</h2>
     * ResourceDomain{byteType=0, basicType=null, name='xiaobei', nickname='null', id=121, enumType=LUOYANG, resourceLocation=null, clazz=null}
     */
    @Test
    public void yamlPropertyReaderByApplicationContext() {
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("classpath:/META-INF/yaml-property-source-context.xml");
        applicationContext.refresh();
        ResourceDomain bean = applicationContext.getBean(ResourceDomain.class);
        System.out.println(bean);
        applicationContext.close();
    }
}
