package com.xiaobei.spring.demo.configuration.metadata;

import com.xiaobei.spring.demo.configuration.metadata.domain.ResourceDomain;
import com.xiaobei.spring.demo.configuration.metadata.extensible.UserBeanDefinitionParser;
import com.xiaobei.spring.demo.configuration.metadata.extensible.XiaobeiNamespaceHandler;
import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinitionReader;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.BeanDefinitionParserDelegate;
import org.springframework.beans.factory.xml.DefaultBeanDefinitionDocumentReader;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlReaderContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.AbstractRefreshableApplicationContext;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;

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
     *
     * @see UserBeanDefinitionParser
     * @see XiaobeiNamespaceHandler
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

    /**
     *
     * <h2>当使用 {@link ApplicationContext} 作为 IoC 容器时的触发时机</h2>
     * {@link AbstractApplicationContext#refresh()}
     * 中的方法{@code ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();}
     * {@link AbstractApplicationContext#obtainFreshBeanFactory()}
     * {@link AbstractApplicationContext#refreshBeanFactory()}
     * {@link AbstractRefreshableApplicationContext#refreshBeanFactory()}
     * {@link AbstractXmlApplicationContext#loadBeanDefinitions(DefaultListableBeanFactory)}
     * {@link AbstractXmlApplicationContext#loadBeanDefinitions(XmlBeanDefinitionReader)}
     * {@link AbstractBeanDefinitionReader#loadBeanDefinitions(org.springframework.core.io.Resource...)}
     * {@link XmlBeanDefinitionReader#loadBeanDefinitions(org.springframework.core.io.Resource)}
     * {@link XmlBeanDefinitionReader#loadBeanDefinitions(EncodedResource)}
     * {@link XmlBeanDefinitionReader#doLoadBeanDefinitions(org.xml.sax.InputSource, Resource)}
     * {@link XmlBeanDefinitionReader#registerBeanDefinitions(org.w3c.dom.Document, Resource)}
     * {@link DefaultBeanDefinitionDocumentReader#registerBeanDefinitions(org.w3c.dom.Document, XmlReaderContext)}
     * {@link DefaultBeanDefinitionDocumentReader#doRegisterBeanDefinitions(org.w3c.dom.Element)}
     * {@link DefaultBeanDefinitionDocumentReader#parseBeanDefinitions(org.w3c.dom.Element, BeanDefinitionParserDelegate)}
     * {@link BeanDefinitionParserDelegate#parseCustomElement(org.w3c.dom.Element)}
     * {@link BeanDefinitionParserDelegate#parseCustomElement(org.w3c.dom.Element, BeanDefinition)}
     *
     *
     * <h2>核心流程</h2>
     * {@link BeanDefinitionParserDelegate#parseCustomElement(org.w3c.dom.Element, BeanDefinition)}<br/>
     * 1. 获取 namespace<br/>
     * 2. 通过 namespace 解析 NamespaceHandler<br/>
     * 3. 构造 ParserContext<br/>
     * 4. 解析元素，获取 BeanDefinition<br/>
     *
     */
    public void extensibleXmlAuthoringProcess() {
        // 注解写明了调用流程
    }
}
