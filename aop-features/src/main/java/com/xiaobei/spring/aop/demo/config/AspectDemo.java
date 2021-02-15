package com.xiaobei.spring.aop.demo.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 使用 {@link Aspect @Aspect} 定义切面，需要是一个 Spring Bean
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-02-14 20:54:54
 */
@Aspect
public class AspectDemo {

    private static final Logger LOGGER = LoggerFactory.getLogger(AspectDemo.class);

    /**
     * 定义一个匹配任何 public 方法的切入点表达式
     * 其中的方法名即为 Pointcut 名称
     */
    @Pointcut("execution(public * *(..))")
    private void anyPublicMethod() {
    }

    @Pointcut("execution(* echo(..))")
    private void anyEchoMethod() {
    }

    @Before("anyPublicMethod()")
    public void beforeAnyPublicMethod() {
        LOGGER.info("@Before in any public method.");
    }

    @Before("anyEchoMethod()")
    public void beforeEchoMethod() {
        LOGGER.info("@Before in any echo method.");
    }

    /**
     * {@link Around @Around} 拦截
     * 需要添加 {@link ProceedingJoinPoint} 来手动调用被拦截方法
     * @param pjp
     * @return
     */
    @Around("anyEchoMethod()")
    public Object aroundAnyEchoMethod(ProceedingJoinPoint pjp) throws Throwable {
        LOGGER.info("@Around before in any echo method.");
        // 手动调用拦截方法
        return pjp.proceed();
    }

}
