package com.xiaobei.spring.demo.configuration.metadata;

import org.junit.Test;
import org.springframework.beans.factory.xml.BeanDefinitionParserDelegate;
import org.springframework.beans.factory.xml.DocumentDefaultsDefinition;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * 容器配置元信息
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/9/17 21:37
 */
public class ContextConfigurationMetadataDemo {

    /**
     * <p>XML配置元信息</p>
     *
     * xml配置元信息的解析器为：{@link BeanDefinitionParserDelegate}
     * <p>对于 xml 信息的解析位置在：
     * {@link BeanDefinitionParserDelegate#populateDefaults(DocumentDefaultsDefinition, DocumentDefaultsDefinition, Element)}
     *
     * spring 对于 xml 的解析使用的是 DOM 技术，参见 {@link Document}
     */
    @Test
    public void xmlConfigurationMetadata() {

    }
}
