package com.xiaobei.spring.aop.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 示例 Aspect After通知 xml 配置方式
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-02-15 22:14:14
 */
public class AfterAspectXmlDemo {

    private static final Logger LOGGER = LoggerFactory.getLogger(AfterAspectXmlDemo.class);

    public void before() {
        LOGGER.info("[before] 方法执行...");
    }

    public void afterReturning() {
        LOGGER.info("[afterReturning] 方法执行...");
    }

    public void after() {
        LOGGER.info("[after] 方法执行...");
    }

    public void afterThrowing() {
        LOGGER.info("[afterThrowing] 方法执行...");
    }
}
