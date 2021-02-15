package com.xiaobei.spring.aop.demo.config;

import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 示例 After Aspect 注解使用
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-02-15 21:25:25
 */
@Aspect
public class AfterAspectAnnotationDemo {

    private static final Logger LOGGER = LoggerFactory.getLogger(AfterAspectAnnotationDemo.class);

    @Pointcut("target(com.xiaobei.spring.aop.demo.service.SumService) " +
            "&& execution(public int incrementAndGet(..))")
    public void incrementAndGet() {
    }

    @Before("incrementAndGet()")
    public void before() {
        LOGGER.info("[before] 方法执行...");
    }

    @AfterReturning("incrementAndGet()")
    public void afterReturning() {
        LOGGER.info("[afterReturning] 方法执行...");
    }

    @After("incrementAndGet()")
    public void after() {
        LOGGER.info("[after] 方法执行...");
    }

    @AfterThrowing("incrementAndGet()")
    public void afterThrowing() {
        LOGGER.info("[afterThrowing] 方法执行...");
    }

}
