package com.xiaobei.spring.demo.event;

import com.xiaobei.spring.demo.event.config.ApplicationListenerConfig;
import org.junit.Test;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

/**
 * {@link ApplicationListener}
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/23 22:00
 */
public class ApplicationListenerDemo {

    /**
     *
     */
    @Test
    public void genericApplicationContextApplicationListener() {
        GenericApplicationContext applicationContext = new GenericApplicationContext();
        // 向 Spring 应用上下文注册事件
//        applicationContext.addApplicationListener(new ApplicationListener<ApplicationEvent>() {
//            @Override
//            public void onApplicationEvent(ApplicationEvent event) {
//                System.out.printf("接收到 Spring 事件：%s\n", event);
//            }
//        });
        applicationContext.addApplicationListener(event -> System.out.printf("接收到 Spring 事件：%s\n", event));
        // 启动 Spring 应用上下文
        applicationContext.refresh();
        // 启动 Spring 上下文
        applicationContext.start();
        // 关闭 Spring 应用上下文
        applicationContext.close();
    }

    /**
     * <h2>运行结果：</h2>
     * 接收到 Spring 事件：org.springframework.context.event.ContextRefreshedEvent[source=org.springframework.context.annotation.AnnotationConfigApplicationContext@75828a0f, started on Sun Aug 23 22:30:40 CST 2020]
     * 接收到 Spring 事件：org.springframework.context.event.ContextClosedEvent[source=org.springframework.context.annotation.AnnotationConfigApplicationContext@75828a0f, started on Sun Aug 23 22:30:40 CST 2020]
     */
    @Test
    public void addApplicationListenerBaseInterface() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ApplicationListenerDemo.class);
        // 接口方式注册 spring 事件监听器
        applicationContext.addApplicationListener(event -> System.out.printf("接收到 Spring 事件：%s\n", event));
        // 启动 spring 应用上下文
        applicationContext.refresh();
        // 关闭 spring 应用上下文
        applicationContext.close();
    }

    /**
     * 示例 spring 注解事件监听器的使用
     *
     * <h2>运行结果：</h2>
     * 当前线程[main]，事件为：接收到事件 Order(1)：ContextRefreshedEvent
     * 当前线程[main]，事件为：接收到事件 Order(2)：ContextStartedEvent
     * 当前线程[main]，事件为：接收到事件：ContextStartedEvent
     * 当前线程[main]，事件为：接收到事件：ContextStoppedEvent
     * 当前线程[main]，事件为：接收到事件：ContextClosedEvent
     * 当前线程[SimpleAsyncTaskExecutor-1]，事件为：接收到事件 Async：ContextRefreshedEvent
     */
    @Test
    public void addApplicationListenerBaseAnnotation() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ApplicationListenerConfig.class);
        // 启动 spring 应用上下文
        applicationContext.refresh();
        applicationContext.start();
        applicationContext.stop();
        // 关闭 spring 应用上下文
        applicationContext.close();
    }
}
