package com.xiaobei.spring.aop.demo;

import com.xiaobei.spring.aop.demo.service.SumService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * xml 方式配置三种 After Advice——
 * <li>{@link org.aspectj.lang.annotation.After}</li>
 * <li>{@link org.aspectj.lang.annotation.AfterReturning}</li>
 * <li>{@link org.aspectj.lang.annotation.AfterThrowing}</li>
 *
 * TODO 实现通过 AspectJ 包装非线程安全的方法实现线程安全
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-02-15 22:19:19
 */
public class AspectJXmlAfterDemo {

    private static final Logger LOGGER = LoggerFactory.getLogger(AspectJXmlAfterDemo.class);

    /**
     * 运行结果：
     * 2021-02-15 22:22:28.985 [main] INFO  c.x.spring.aop.demo.config.AfterAspectXmlDemo - [before] 方法执行...
     * 2021-02-15 22:22:29.036 [main] INFO  com.xiaobei.spring.aop.demo.service.SumService - 当前变量的值为：1
     * 2021-02-15 22:22:29.037 [main] INFO  c.x.spring.aop.demo.config.AfterAspectXmlDemo - [after] 方法执行...
     * 2021-02-15 22:22:29.038 [main] INFO  c.x.spring.aop.demo.config.AfterAspectXmlDemo - [afterReturning] 方法执行...
     * 2021-02-15 22:22:29.038 [main] INFO  com.xiaobei.spring.aop.demo.AspectJXmlAfterDemo - 最终得到的值为：1
     * @param args
     */
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext
                = new ClassPathXmlApplicationContext(
                        "classpath:/META-INF/spring-aop-after-config.xml");
        SumService sumService = applicationContext.getBean(SumService.class);
        sumService.incrementAndGet();
        LOGGER.info("最终得到的值为：{}" ,sumService.getOriginal());
        applicationContext.close();
    }
}
