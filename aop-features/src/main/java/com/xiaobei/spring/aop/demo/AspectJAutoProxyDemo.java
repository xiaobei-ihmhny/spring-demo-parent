package com.xiaobei.spring.aop.demo;

import com.xiaobei.spring.aop.demo.service.CustomEchoService;
import com.xiaobei.spring.aop.demo.service.EchoService;
import com.xiaobei.spring.aop.demo.service.SumService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * TODO
 *
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-02-16 07:08:08
 */
@DisplayName("测试自动代理")
public class AspectJAutoProxyDemo {

    private static final Logger LOGGER = LoggerFactory.getLogger(AspectJAutoProxyDemo.class);

    /**
     * 运行结果：
     * 2021-02-16 07:36:47.188 [main] INFO  com.xiaobei.spring.aop.demo.service.SumService - 当前变量的值为：1
     * 2021-02-16 07:36:47.207 [main] INFO  c.x.s.a.demo.proxyfactory.CustomMethodInterceptor - 当前拦截方法签名为：public java.lang.String com.xiaobei.spring.aop.demo.service.CustomEchoService.echo(java.lang.String)
     * 2021-02-16 07:36:47.230 [main] INFO  c.x.spring.aop.demo.service.CustomEchoService - [CustomEchoService] Hello tietie
     * 2021-02-16 07:36:47.230 [main] INFO  c.x.s.a.demo.proxyfactory.CustomMethodInterceptor - 当前拦截方法签名为：public abstract java.lang.String com.xiaobei.spring.aop.demo.service.EchoService.echo(java.lang.String)
     * 2021-02-16 07:36:47.230 [main] INFO  c.x.spring.aop.demo.service.DefaultEchoService - Hello tietie
     *
     */
    @Test
    @DisplayName("BeanNameAutoProxyCreator 使用示例")
    public void beanNameAutoProxyCreator() {
        ClassPathXmlApplicationContext applicationContext
                = new ClassPathXmlApplicationContext(
                        "classpath:/META-INF/spring-aop-auto-proxy-BeanNameAutoProxyCreator.xml");
        SumService sumService = applicationContext.getBean("sumService", SumService.class);
        sumService.incrementAndGet();
        CustomEchoService customEchoService = applicationContext.getBean(CustomEchoService.class);
        customEchoService.echo("tietie");
        EchoService echoService = applicationContext.getBean(EchoService.class);
        echoService.echo("tietie");
        applicationContext.close();
    }

    /**
     * 运行结果：
     * 2021-02-16 08:12:59.507 [main] INFO  com.xiaobei.spring.aop.demo.service.SumService - 当前变量的值为：1
     * 2021-02-16 08:12:59.507 [main] INFO  c.x.spring.aop.demo.service.CustomEchoService - [CustomEchoService] Hello huihui
     * 2021-02-16 08:12:59.507 [main] INFO  c.x.s.a.demo.proxyfactory.CustomMethodInterceptor - 当前拦截方法签名为：public abstract java.lang.String com.xiaobei.spring.aop.demo.service.EchoService.echo(java.lang.String)
     * 2021-02-16 08:12:59.507 [main] INFO  c.x.spring.aop.demo.service.DefaultEchoService - Hello huihui
     */
    @Test
    @DisplayName("DefaultAdvisorAutoProxyCreator 使用示例")
    public void defaultAdvisorAutoProxyCreator() {
        ClassPathXmlApplicationContext applicationContext
                = new ClassPathXmlApplicationContext(
                "classpath:/META-INF/spring-aop-auto-proxy-DefaultAdvisorAutoProxyCreator.xml");
        SumService sumService = applicationContext.getBean("sumService", SumService.class);
        sumService.incrementAndGet();
        CustomEchoService customEchoService = applicationContext.getBean(CustomEchoService.class);
        customEchoService.echo("huihui");
        EchoService echoService = applicationContext.getBean(EchoService.class);
        echoService.echo("huihui");
        applicationContext.close();
    }

    /**
     * 运行结果：
     *
     * 2021-02-16 12:12:05.056 [main] INFO  c.x.spring.aop.demo.config.AfterAspectXmlDemo - [before] 方法执行...
     * 2021-02-16 12:12:05.073 [main] INFO  com.xiaobei.spring.aop.demo.service.SumService - 当前变量的值为：1
     * 2021-02-16 12:12:05.075 [main] INFO  c.x.spring.aop.demo.config.AfterAspectXmlDemo - [after] 方法执行...
     * 2021-02-16 12:12:05.075 [main] INFO  c.x.spring.aop.demo.config.AfterAspectXmlDemo - [afterReturning] 方法执行...
     * 2021-02-16 12:12:05.076 [main] INFO  com.xiaobei.spring.aop.demo.AspectJAutoProxyDemo - 最终得到的值为：1
     */
    @Test
    @DisplayName("AnnotationAwareAspectJAutoProxyCreator 使用示例")
    public void annotationAwareAspectJAutoProxyCreator() {
        ClassPathXmlApplicationContext applicationContext
                = new ClassPathXmlApplicationContext(
                "classpath:/META-INF/spring-aop-auto-proxy-AnnotationAwareAspectJAutoProxyCreator.xml");
        SumService sumService = applicationContext.getBean(SumService.class);
        sumService.incrementAndGet();
        LOGGER.info("最终得到的值为：{}" ,sumService.getOriginal());
        applicationContext.close();
    }
}
