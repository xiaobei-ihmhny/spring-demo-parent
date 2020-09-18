package com.xiaobei.spring.demo.configuration.metadata;

import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.BeanDefinitionParserDelegate;
import org.springframework.beans.factory.xml.DefaultBeanDefinitionDocumentReader;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlReaderContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/9/17 22:39
 */
public class XmlBeanBeanDefinitionDemo {


    /**
     * <h2>{@code <bean>} 标签的解析是通过自有api实现的，并没有使用xml的扩展机制</h2>
     * 加载 Bean 的配置信息{@link XmlBeanDefinitionReader#loadBeanDefinitions(EncodedResource)}
     *   {@link XmlBeanDefinitionReader#doLoadBeanDefinitions(InputSource, Resource)}
     *    {@code Document doc = doLoadDocument(inputSource, resource);}
     *     将资源信息变成 {@link Document}：{@link XmlBeanDefinitionReader#doLoadDocument(InputSource, Resource)}
     *    {@code int count = registerBeanDefinitions(doc, resource);}
     *     注册{@link BeanDefinition}：{@link XmlBeanDefinitionReader#registerBeanDefinitions(Document, Resource)}
     *      {@code documentReader.registerBeanDefinitions(doc, createReaderContext(resource));}
     *      {@link DefaultBeanDefinitionDocumentReader#registerBeanDefinitions(Document, XmlReaderContext)}
     *       {@link DefaultBeanDefinitionDocumentReader#doRegisterBeanDefinitions(org.w3c.dom.Element)}
     *        {@code parseBeanDefinitions(root, this.delegate);}
     *        {@link DefaultBeanDefinitionDocumentReader#parseBeanDefinitions(org.w3c.dom.Element, BeanDefinitionParserDelegate)}
     *         {@code parseDefaultElement(ele, delegate);}
     *         {@link DefaultBeanDefinitionDocumentReader#parseDefaultElement(Element, BeanDefinitionParserDelegate)}
     *          {@link DefaultBeanDefinitionDocumentReader#processBeanDefinition(org.w3c.dom.Element, BeanDefinitionParserDelegate)}
     *           将xml中的配置解析成 BeanDefinition {@code BeanDefinitionHolder bdHolder = delegate.parseBeanDefinitionElement(ele);}
     *             {@link BeanDefinitionParserDelegate#parseBeanDefinitionElement(org.w3c.dom.Element)}
     *           进行 BeanDefinition 的注册 {@code BeanDefinitionReaderUtils.registerBeanDefinition(bdHolder, getReaderContext().getRegistry());}
     *            {@link BeanDefinitionReaderUtils#registerBeanDefinition(BeanDefinitionHolder, BeanDefinitionRegistry)}
     *
     * @see XmlBeanDefinitionReader
     */
    @Test
    public void xmlLoadBeanConfigurationMetadata() {

    }

}
