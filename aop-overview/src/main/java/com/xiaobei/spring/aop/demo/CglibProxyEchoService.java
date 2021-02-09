package com.xiaobei.spring.aop.demo;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * cglib 实现动态代理
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021-02-09 22:45:45
 */
public class CglibProxyEchoService implements MethodInterceptor {

    @Override
    public Object intercept(Object target, Method method,
                            Object[] args, MethodProxy methodProxy) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = methodProxy.invokeSuper(target, args);
        long endTime = System.currentTimeMillis();
        System.out.printf("当前方法执行耗时：%d", (endTime - startTime));
        return result;
    }
}
