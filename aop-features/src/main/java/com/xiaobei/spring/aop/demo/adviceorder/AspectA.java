package com.xiaobei.spring.aop.demo.adviceorder;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

/**
 * 通过标注 {@link Order @Order} 注解来定义切面顺序
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-02-15 16:34:34
 */
@Aspect
@Order(1)
public class AspectA extends BaseAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(AspectA.class);

    @Before("echoMethodInCustomEchoService()")
    public void beforeEchoMethodInAspectA() {
        LOGGER.info("@Order(1) 的 Aspect 切面 AspectA 中的 @Before 方法");
    }
}
