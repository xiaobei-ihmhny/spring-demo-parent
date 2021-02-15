package com.xiaobei.spring.aop.demo.adviceorder;

import org.aspectj.lang.annotation.Pointcut;

/**
 * TODO
 *
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-02-15 16:36:36
 */
public abstract class BaseAspect {

    /**
     * 拦截 {@link com.xiaobei.spring.aop.demo.service.CustomEchoService#echo(String)} 方法
     */
    @Pointcut("target(com.xiaobei.spring.aop.demo.service.CustomEchoService) " +
            "&& execution(public String echo(String))")
    public void echoMethodInCustomEchoService() {
    }
}
