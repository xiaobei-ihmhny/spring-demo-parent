package com.xiaobei.spring.aop.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 使用一个普通的类定义切面，需要是一个 Spring Bean
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-02-14 20:54:54
 */
public class AspectXmlDemo {

    private static final Logger LOGGER = LoggerFactory.getLogger(AspectXmlDemo.class);

    public void beforeAnyPublicMethod() {
        LOGGER.info("@Before in any public method.");
    }

    public void beforeEchoMethod() {
        LOGGER.info("@Before in any echo method.");
    }

}
