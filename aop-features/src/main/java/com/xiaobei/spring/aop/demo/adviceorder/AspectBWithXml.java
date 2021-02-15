package com.xiaobei.spring.aop.demo.adviceorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * xml 方式定义普通类 {@link AspectBWithXml} 作为切面类
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-02-15 16:51:51
 */
public class AspectBWithXml {

    private static final Logger LOGGER = LoggerFactory.getLogger(AspectBWithXml.class);

    public void beforeEchoMethodInAspectB() {
        LOGGER.info("@Order(2) 的 Aspect 切面 AspectB 中的 @Before 方法");
    }

}