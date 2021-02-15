package com.xiaobei.spring.aop.demo.adviceorder;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;

/**
 * 通过实现 {@link Ordered} 接口来定义切面顺序
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-02-15 16:34:34
 */
@Aspect
public class AspectB extends BaseAspect implements Ordered {

    private static final Logger LOGGER = LoggerFactory.getLogger(AspectB.class);

    @Before("echoMethodInCustomEchoService()")
    public void beforeEchoMethodInAspectB() {
        LOGGER.info("@Order(2) 的 Aspect 切面 AspectB 中的 @Before 方法");
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
