package com.xiaobei.spring.demo.configuration.metadata.yaml;

import org.junit.Test;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

import java.util.Map;

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
     * TODO 其中名称为“yamlMap”的bean的类型为什么是 {@link java.util.LinkedHashMap}， 而不是{@link YamlMapFactoryBean}呢？？？
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
}
