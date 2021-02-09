package com.xiaobei.spring.aop.demo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * {@link EchoService} 的 {@link InvocationHandler} 处理类
 *
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021-02-09 22:35:35
 */
public class TimeStatisticsInvocationHandler<T> implements InvocationHandler {

    private final T targetObj;

    public TimeStatisticsInvocationHandler(T targetObj) {
        this.targetObj = targetObj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = method.invoke(targetObj, args);
        long endTime = System.currentTimeMillis();
        System.out.printf("当前方法执行耗时：%d", (endTime - startTime));
        return result;
    }
}
