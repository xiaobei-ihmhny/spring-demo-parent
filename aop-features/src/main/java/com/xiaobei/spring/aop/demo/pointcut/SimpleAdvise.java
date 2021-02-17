package com.xiaobei.spring.aop.demo.pointcut;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 直接实现 {@link MethodInterceptor} 完成方法拦截
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-02-17 20:49:49
 */
public class SimpleAdvise implements MethodInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleAdvise.class);

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        LOGGER.info("invoke before...");
        Object resultValue = invocation.proceed();
        LOGGER.info("invoke after...");
        return resultValue;
    }
}
