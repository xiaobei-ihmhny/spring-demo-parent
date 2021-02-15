package com.xiaobei.spring.aop.demo.config;

import org.aspectj.lang.ProceedingJoinPoint;
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

    /**
     * {@link org.aspectj.lang.annotation.Around} 拦截动作
     * @param pjp
     * @return
     */
    public Object aroundEchoMethod(ProceedingJoinPoint pjp) throws Throwable {
        LOGGER.info("@Around in any echo method: {}", pjp.getSignature());
        return pjp.proceed();
    }

}
