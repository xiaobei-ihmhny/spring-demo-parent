package com.xiaobei.spring.aop.demo;

import java.lang.reflect.Method;

/**
 * 后置拦截
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-02-12 20:40:40
 */
public interface AfterInterceptor {

    /**
     *
     * @param proxy
     * @param method
     * @param args
     * @param result
     * @return
     */
    Object after(Object proxy, Method method, Object[] args, Object result);
}
