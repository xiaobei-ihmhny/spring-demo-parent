package com.xiaobei.spring.aop.demo;

import com.xiaobei.spring.aop.demo.pointcut.EchoServiceEchoMethodPointcut;
import com.xiaobei.spring.aop.demo.pointcut.EchoServicePointcut;
import com.xiaobei.spring.aop.demo.proxyfactory.CustomMethodInterceptor;
import com.xiaobei.spring.aop.demo.service.CustomEchoService;
import com.xiaobei.spring.aop.demo.service.DefaultEchoService;
import com.xiaobei.spring.aop.demo.service.EchoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

/**
 * API 方式实现 Pointcut 示例
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-02-15 11:30:30
 */
@DisplayName("Pointcut 示例")
public class PointAPIDemo {

    /**
     * 运行结果：
     *
     * 2021-02-16 16:39:52.273 [main] INFO  c.x.s.a.demo.proxyfactory.CustomMethodInterceptor - 当前拦截方法签名为：public java.lang.String com.xiaobei.spring.aop.demo.service.CustomEchoService.echo(java.lang.String)
     * 2021-02-16 16:39:52.292 [main] INFO  c.x.spring.aop.demo.service.CustomEchoService - [CustomEchoService] Hello tietie
     */
    @Test
    @DisplayName("通过继承 StaticMethodMatcherPointcut 来实现 pointcut")
    public void extendsStaticMethodMatcherPointcut() {
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

    /**
     * 运行结果：
     * 2021-02-16 16:41:20.475 [main] INFO  c.x.s.a.demo.proxyfactory.CustomMethodInterceptor - 当前拦截方法签名为：public java.lang.String com.xiaobei.spring.aop.demo.service.CustomEchoService.echo(java.lang.String)
     * 2021-02-16 16:41:20.542 [main] INFO  c.x.spring.aop.demo.service.CustomEchoService - [CustomEchoService] Hello tietie
     */
    @Test
    @DisplayName("通过实现 Pointcut 接口，直接定义")
    public void implementPointcut() {
        // 创建 pointcut
        EchoServiceEchoMethodPointcut pointcut = EchoServiceEchoMethodPointcut.INSTANCE;
        DefaultEchoService defaultEchoService = new DefaultEchoService();
        ProxyFactory proxyFactory = new ProxyFactory(defaultEchoService);
        // 将 Pointcut 适配成 Advisor
        DefaultPointcutAdvisor advisor
                = new DefaultPointcutAdvisor(pointcut, new CustomMethodInterceptor());
        // 添加 Advisor
        proxyFactory.addAdvisor(advisor);
        // 通过 ProxyFactory 获得被代理类，此外不能用 {@link DefaultEchoService}，只能使用 {@link EchoService}
        EchoService echoServiceProxy = (EchoService) proxyFactory.getProxy();
        echoServiceProxy.echo("tietie");
    }
}
