package com.xiaobei.spring.aop.demo;

import java.lang.reflect.Method;

/**
 * 前置拦截
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-02-12 20:39:39
 */
public interface BeforeInterceptor {

    /**
     * 前置拦截
     * @param proxy
     * @param method 具体拦截的方法
     * @param args 具体拦截的方法参数
     * @return 前置拦截返回值
     */
    Object before(Object proxy, Method method, Object[] args);
}
