package com.xiaobei.spring.demo.configuration.metadata.extensible;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/15 23:21
 */
public class XiaobeiNamespaceHandler extends NamespaceHandlerSupport {


    @Override
    public void init() {
        registerBeanDefinitionParser("resourceDomain", new UserBeanDefinitionParser());
    }
}
