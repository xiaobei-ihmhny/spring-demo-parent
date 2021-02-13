package com.xiaobei.spring.aop.demo;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * cglib 实现动态代理
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021-02-09 22:45:45
 */
public class CglibProxyEchoService implements MethodInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(CglibProxyEchoService.class);

    @Override
    public Object intercept(Object target, Method method,
                            Object[] args, MethodProxy methodProxy) throws Throwable {
        long startTime = System.currentTimeMillis();
        // 使用 methodProxy调用被代理对象的方法
        Object result = methodProxy.invokeSuper(target, args);
        long endTime = System.currentTimeMillis();
        LOGGER.info("当前方法执行耗时：{}", (endTime - startTime));
        return result;
    }
}
