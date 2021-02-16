package com.xiaobei.spring.aop.demo;

import com.xiaobei.spring.aop.demo.pointcut.EchoServicePointcut;
import com.xiaobei.spring.aop.demo.proxyfactory.CustomMethodInterceptor;
import com.xiaobei.spring.aop.demo.service.CustomEchoService;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

/**
 * API 方式实现 Pointcut 示例
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-02-15 11:30:30
 */
public class PointAPIDemo {

    public static void main(String[] args) {
        // 创建 pointcut
        EchoServicePointcut pointcut
                = new EchoServicePointcut("echo", CustomEchoService.class);
        CustomEchoService customEchoService = new CustomEchoService();
        ProxyFactory proxyFactory = new ProxyFactory(customEchoService);
        // 将 Pointcut 适配成 Advisor
        DefaultPointcutAdvisor advisor
                = new DefaultPointcutAdvisor(pointcut, new CustomMethodInterceptor());
        // 添加 Advisor
        proxyFactory.addAdvisor(advisor);
        // 通过 ProxyFactory 获得被代理类
        CustomEchoService echoServiceProxy = (CustomEchoService) proxyFactory.getProxy();
        echoServiceProxy.echo("tietie");
    }
}
