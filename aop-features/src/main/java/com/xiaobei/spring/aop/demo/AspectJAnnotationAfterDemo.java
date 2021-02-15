package com.xiaobei.spring.aop.demo;

import com.xiaobei.spring.aop.demo.config.AfterAspectAnnotationDemo;
import com.xiaobei.spring.aop.demo.config.AspectJConfig;
import com.xiaobei.spring.aop.demo.service.SumService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * annotation 方式配置三种 After Advice——
 * <li>{@link org.aspectj.lang.annotation.After}</li>
 * <li>{@link org.aspectj.lang.annotation.AfterReturning}</li>
 * <li>{@link org.aspectj.lang.annotation.AfterThrowing}</li>
 *
 * TODO 实现通过 AspectJ 包装非线程安全的方法实现线程安全
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-02-15 21:38:38
 */
@Configuration
public class AspectJAnnotationAfterDemo {

    private static final Logger LOGGER = LoggerFactory.getLogger(AspectJAnnotationAfterDemo.class);

    @Bean
    public SumService sumService() {
        return new SumService(0);
    }

    /**
     * 运行结果：
     * 2021-02-15 22:12:22.494 [main] INFO  c.x.s.aop.demo.config.AfterAspectAnnotationDemo - [before] 方法执行...
     * 2021-02-15 22:12:22.511 [main] INFO  com.xiaobei.spring.aop.demo.service.SumService - 当前变量的值为：1
     * 2021-02-15 22:12:22.512 [main] INFO  c.x.s.aop.demo.config.AfterAspectAnnotationDemo - [after] 方法执行...
     * 2021-02-15 22:12:22.512 [main] INFO  c.x.s.aop.demo.config.AfterAspectAnnotationDemo - [afterReturning] 方法执行...
     * 2021-02-15 22:12:22.513 [main] INFO  c.x.spring.aop.demo.AspectJAnnotationAfterDemo - 最终得到的值为：1
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext applicationContext
                = new AnnotationConfigApplicationContext();
        applicationContext.register(AspectJConfig.class,
                AfterAspectAnnotationDemo.class, AspectJAnnotationAfterDemo.class);
        applicationContext.refresh();
        SumService sumService = applicationContext.getBean(SumService.class);
        sumService.incrementAndGet();
        LOGGER.info("最终得到的值为：{}" ,sumService.getOriginal());
        applicationContext.close();
    }
}
