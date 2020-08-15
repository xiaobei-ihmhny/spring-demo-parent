package com.xiaobei.spring.demo.configuration.metadata;

import com.xiaobei.spring.demo.configuration.metadata.domain.ResourceDomain;
import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/15 23:31
 */
public class ExtensibleXmlAuthoringDemo {


    /**
     * <h2>运行结果</h2>
     * ResourceDomain{byteType=0, basicType=null, name='小贝', nickname='null', id=118, enumType=RUYANG, resourceLocation=null, clazz=null}
     *
     * <h2>基于 Extensible XML authoring扩展Spring XML元素的步骤</h2>
     * 1. 编写 XML Schema 文件：定义XML结构
     * 2. 自定义 NamespaceHander 实现：命名空间绑定
     * 3. 自定义 BeanDefinitionParser 实现：XML元素与BeanDefinition解析
     * 4. 注册 XML 扩展：命名空间与 XML Schema 映射
     */
    @Test
    public void extensibleXmlAuthoring() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        String location = "classpath:/META-INF/xiaobei-context.xml";
        reader.loadBeanDefinitions(location);
        // 依赖查找
        ResourceDomain bean = beanFactory.getBean(ResourceDomain.class);
        System.out.println(bean);
    }
}
