package com.xiaobei.spring.aop.demo.pointcut;

import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.lang.reflect.Method;

/**
 * {@link StaticMethodMatcherPointcut} 默认情况下匹配所有的类，
 * 开发人员只需要匹配方法即可
 *
 * 使用示例参见：{@link com.xiaobei.spring.aop.demo.PointAPIDemo}
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-02-17 09:24:24
 */
public class StaticMethodMatcherPointcutDemo extends StaticMethodMatcherPointcut {

    /**
     * 匹配所有方法名为 {@code echo} 的方法
     * @param method
     * @param targetClass
     * @return
     */
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return "echo".equals(method.getName());
    }
}
