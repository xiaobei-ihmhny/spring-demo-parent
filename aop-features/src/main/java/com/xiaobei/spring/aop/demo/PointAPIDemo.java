package com.xiaobei.spring.aop.demo;

import com.xiaobei.spring.aop.demo.config.EmptyAspect;
import com.xiaobei.spring.aop.demo.pointcut.EchoServiceEchoMethodPointcut;
import com.xiaobei.spring.aop.demo.pointcut.EchoServicePointcut;
import com.xiaobei.spring.aop.demo.pointcut.SimpleAdvise;
import com.xiaobei.spring.aop.demo.pointcut.StaticMethodMatcherPointcutDemo;
import com.xiaobei.spring.aop.demo.proxyfactory.CustomMethodInterceptor;
import com.xiaobei.spring.aop.demo.service.ActionService;
import com.xiaobei.spring.aop.demo.service.CustomEchoService;
import com.xiaobei.spring.aop.demo.service.DefaultEchoService;
import com.xiaobei.spring.aop.demo.service.EchoService;
import org.aopalliance.aop.Advice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.ControlFlowPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;

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

    @Nested
    @DisplayName("示例 JdkRegexpMethodPointcut 使用")
    class JdkRegexpMethodPointcutTest {

        /**
         * 运行结果：
         * 2021-02-17 20:56:39.853 [main] INFO  com.xiaobei.spring.aop.demo.pointcut.SimpleAdvise - invoke before...
         * 2021-02-17 20:56:39.953 [main] INFO  com.xiaobei.spring.aop.demo.service.ActionService - lightening...
         * 2021-02-17 20:56:39.953 [main] INFO  com.xiaobei.spring.aop.demo.pointcut.SimpleAdvise - invoke after...
         * 2021-02-17 20:56:39.953 [main] INFO  com.xiaobei.spring.aop.demo.pointcut.SimpleAdvise - invoke before...
         * 2021-02-17 20:56:39.953 [main] INFO  com.xiaobei.spring.aop.demo.service.ActionService - light up...
         * 2021-02-17 20:56:39.953 [main] INFO  com.xiaobei.spring.aop.demo.pointcut.SimpleAdvise - invoke after...
         * 2021-02-17 20:56:39.953 [main] INFO  com.xiaobei.spring.aop.demo.service.ActionService - shutdown...
         */
        @Test
        @DisplayName("JdkRegexpMethodPointcut 简单使用")
        public void sample() {
            ActionService actionService = new ActionService();
            JdkRegexpMethodPointcut pointcut = new JdkRegexpMethodPointcut();
            // 正则表达式匹配方法名以 lig开头的方法
            pointcut.setPattern(".*lig.*");
            Advice advice = new SimpleAdvise();
            Advisor advisor = new DefaultPointcutAdvisor(pointcut, advice);
            // 创建代理工厂
            ProxyFactory proxyFactory = new ProxyFactory();
            proxyFactory.addAdvisor(advisor);
            proxyFactory.setTarget(actionService);
            ActionService proxyAction = (ActionService) proxyFactory.getProxy();

            // 调用代理对象的方法
            proxyAction.lightening();
            proxyAction.lightUp();
            proxyAction.shutdown();
        }
    }

    @Nested
    @DisplayName("StaticMethodMatcherPointcut 使用示例")
    class StaticMethodMatcherPointcutTest {

        /**
         * 运行结果：
         *
         * 2021-02-17 21:19:20.726 [main] INFO  com.xiaobei.spring.aop.demo.service.ActionService - lightening...
         * 2021-02-17 21:19:20.729 [main] INFO  com.xiaobei.spring.aop.demo.service.ActionService - light up...
         * 2021-02-17 21:19:20.729 [main] INFO  com.xiaobei.spring.aop.demo.service.ActionService - shutdown...
         * 2021-02-17 21:19:20.732 [main] INFO  com.xiaobei.spring.aop.demo.pointcut.SimpleAdvise - invoke before...
         * 2021-02-17 21:19:20.732 [main] INFO  com.xiaobei.spring.aop.demo.service.ActionService - echo...
         * 2021-02-17 21:19:20.732 [main] INFO  com.xiaobei.spring.aop.demo.pointcut.SimpleAdvise - invoke after...
         */
        @Test
        @DisplayName("简单使用示例")
        public void sample() {
            Advice advice = new SimpleAdvise();
            Pointcut pointcut = new StaticMethodMatcherPointcutDemo();
            Advisor advisor = new DefaultPointcutAdvisor(pointcut, advice);
            ActionService actionService = new ActionService();

            // 使用 ProxyFactoryBean 工厂
            AspectJProxyFactory proxyFactory = new AspectJProxyFactory();
            proxyFactory.setTarget(actionService);
            proxyFactory.addAdvisor(advisor);
            ActionService proxyAction = proxyFactory.getProxy();
            proxyAction.lightening();
            proxyAction.lightUp();
            proxyAction.shutdown();
            proxyAction.echo();
        }
    }

    @Nested
    @DisplayName("ControlFlowPointcut 使用示例")
    class ControlFlowPointcutTest {

        /**
         * TODO {@link ControlFlowPointcut#matches(java.lang.reflect.Method, java.lang.Class, java.lang.Object...)} 中
         * 的 {@code for (StackTraceElement element : new Throwable().getStackTrace())} 可能存在效率问题：
         * <li>1. trace是同步执行</li>
         * <li>2. 链路可能比较深</li>
         * TODO 可以考虑使用 java 9 中的 {@link StackWalker} 实现
         *
         * 运行结果：
         *
         * 2021-02-17 21:42:52.363 [main] INFO  com.xiaobei.spring.aop.demo.service.ActionService - echo...
         * 2021-02-17 21:42:52.366 [main] INFO  com.xiaobei.spring.aop.demo.service.ActionService - shutdown...
         * 2021-02-17 21:42:52.366 [main] INFO  com.xiaobei.spring.aop.demo.pointcut.SimpleAdvise - invoke before...
         * 2021-02-17 21:42:52.366 [main] INFO  com.xiaobei.spring.aop.demo.service.ActionService - echo...
         * 2021-02-17 21:42:52.366 [main] INFO  com.xiaobei.spring.aop.demo.pointcut.SimpleAdvise - invoke after...
         * 2021-02-17 21:42:52.366 [main] INFO  com.xiaobei.spring.aop.demo.pointcut.SimpleAdvise - invoke before...
         * 2021-02-17 21:42:52.366 [main] INFO  com.xiaobei.spring.aop.demo.service.ActionService - lightening...
         * 2021-02-17 21:42:52.367 [main] INFO  com.xiaobei.spring.aop.demo.pointcut.SimpleAdvise - invoke after...
         * 2021-02-17 21:42:52.367 [main] INFO  com.xiaobei.spring.aop.demo.service.ActionService - shutdown...
         */
        @Test
        @DisplayName("简单使用示例")
        public void sample() {
            // 只有通过指定类的指定方法调用时才会被拦截
            Pointcut pointcut = new ControlFlowPointcut(ControlFlowPointcutTest.class, "method1");
            Advice advice = new SimpleAdvise();
            Advisor advisor = new DefaultPointcutAdvisor(pointcut, advice);

            ActionService actionService = new ActionService();
            // 创建代理工厂
            ProxyFactory proxyFactory = new ProxyFactory();
            proxyFactory.setTarget(actionService);
            proxyFactory.addAdvisor(advisor);

            ActionService proxy = (ActionService) proxyFactory.getProxy();
            // 直接调用 echo 方法
            proxy.echo();
            // 直接调用 shutdown 方法
            proxy.shutdown();
            // 通过 ControlFlowPointcutTest 调用
            ControlFlowPointcutTest test = new ControlFlowPointcutTest();
            test.method1(proxy);
            test.method2(proxy);
        }

        public void method1(ActionService actionService) {
            actionService.echo();
            actionService.lightening();
        }

        public void method2(ActionService actionService) {
            actionService.shutdown();
        }
    }

    /**
     * {@link ComposablePointcut} 的使用示例
     * 组合多个 {@link Pointcut}，可以使用 {@link ComposablePointcut#union(Pointcut)} 取并集，
     * 或者使用 {@link ComposablePointcut#intersection(Pointcut)} 取交集
     * 46 | Pointcut便利实现 04:00
     */
    @Nested
    @DisplayName("ComposablePointcut 示例")
    class ComposablePointcutTest {

        @Test
        @DisplayName("简单使用示例")
        public void sample() {
            // 第一个 pointcut
            JdkRegexpMethodPointcut pointcut1 = new JdkRegexpMethodPointcut();
            // 正则表达式匹配方法名以 lig开头的方法
            pointcut1.setPattern(".*lig.*");
            // 第二个 pointcut
            Pointcut pointcut2 = new StaticMethodMatcherPointcutDemo();
            // 组合两个不同的 pointcut
            ComposablePointcut pointcut = new ComposablePointcut();
            pointcut.union(pointcut1.getClassFilter());
            pointcut.union(pointcut1.getMethodMatcher());
            pointcut.union(pointcut2);

            Advice advice = new SimpleAdvise();
            Advisor advisor = new DefaultPointcutAdvisor(pointcut, advice);

            ActionService actionService = new ActionService();
            // 创建代理工厂
            ProxyFactory proxyFactory = new ProxyFactory();
            proxyFactory.addAdvisor(advisor);
            proxyFactory.setTarget(actionService);
            ActionService proxyAction = (ActionService) proxyFactory.getProxy();

            // 调用代理对象的方法
            proxyAction.lightening();// 匹配 pointcut1
            proxyAction.lightUp();// 匹配 pointcut1
            proxyAction.shutdown();// 匹配 pointcut1
            proxyAction.echo();// 匹配 pointcut2
        }
    }


}
