package com.xiaobei.spring.aop.demo.adviceorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * xml 方式定义普通类 {@link AspectAWithXml} 作为切面类
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-02-15 16:51:51
 */
public class AspectAWithXml {

    private static final Logger LOGGER = LoggerFactory.getLogger(AspectAWithXml.class);

    public void beforeEchoMethodInAspectA() {
        LOGGER.info("@Order(1) 的 Aspect 切面 AspectA 中的 @Before 方法");
    }
}
