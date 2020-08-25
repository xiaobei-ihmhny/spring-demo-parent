package com.xiaobei.spring.demo.event;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.EventListener;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Consumer;

import static org.springframework.context.support.AbstractApplicationContext.APPLICATION_EVENT_MULTICASTER_BEAN_NAME;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/25 10:16
 */
public class AsyncEventHandlerDemo {

    /**
     * 以同步方式实现
     *
     * <h2>运行结果：</h2>
     * [线程 ：main] 监听到事件：com.xiaobei.spring.demo.event.MyApplicationEvent[source=Hello, World]
     */
    @Test
    public void syncApplicationEvent() {
        genericApplicationContextConfig(applicationContext -> {});
    }

    /**
     * 在{@link ApplicationEventMulticaster} 中添加 {@link java.util.concurrent.Executor} 实现
     * 并使用默认的线程工厂
     *
     * <h2>运行结果：</h2>
     * [线程 ：pool-1-thread-1] 监听到事件：com.xiaobei.spring.demo.event.MyApplicationEvent[source=Hello, World]
     *
     */
    @Test
    public void asyncApplicationEventDefaultThreadFactory() {
        genericApplicationContextConfig(applicationContext -> {
            // 获取 {@code ApplicationEventMulticaster}
            ApplicationEventMulticaster applicationEventMulticaster =
                    applicationContext.getBean(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, ApplicationEventMulticaster.class);
            if(applicationEventMulticaster instanceof SimpleApplicationEventMulticaster) {
                SimpleApplicationEventMulticaster multicaster = (SimpleApplicationEventMulticaster) applicationEventMulticaster;
                multicaster.setTaskExecutor(Executors.newSingleThreadExecutor());
            }
        });
    }

    /**
     * 在{@link ApplicationEventMulticaster} 中添加 {@link java.util.concurrent.Executor} 实现
     * 并使用自定义的线程工厂 {@link CustomizableThreadFactory}
     *
     * <h2>运行结果：</h2>
     * [线程 ：my-event-thread-pool1] 监听到事件：com.xiaobei.spring.demo.event.MyApplicationEvent[source=Hello, World]
     */
    @Test
    public void asyncApplicationEventCustomizedThreadFactory() {
        genericApplicationContextConfig(applicationContext -> {
            // 获取 {@code ApplicationEventMulticaster}
            ApplicationEventMulticaster applicationEventMulticaster =
                    applicationContext.getBean(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, ApplicationEventMulticaster.class);
            if(applicationEventMulticaster instanceof SimpleApplicationEventMulticaster) {
                SimpleApplicationEventMulticaster multicaster = (SimpleApplicationEventMulticaster) applicationEventMulticaster;
                multicaster.setTaskExecutor(Executors.newSingleThreadExecutor(new CustomizableThreadFactory("my-event-thread-pool")));
            }
        });
    }

    /**
     * {@link GenericApplicationContext} 配置
     * @param multicasterConsumer 进行自定义 {@link ApplicationEventMulticaster} 的处理
     */
    private void genericApplicationContextConfig(Consumer<GenericApplicationContext> multicasterConsumer) {
        GenericApplicationContext applicationContext = new GenericApplicationContext();
        // 注册 Spring 事件监听器
        applicationContext.addApplicationListener(new MyApplicationListener());
        // 启动 Spring 应用上下文
        applicationContext.refresh();
        // 发布事件前添加额外处理
        multicasterConsumer.accept(applicationContext);
        // 发布 Spring 事件
        applicationContext.publishEvent(new MyApplicationEvent("Hello, World"));
        // 关闭 Spring 应用上下文
        applicationContext.close();
    }

    /**
     * 配置 {@link AnnotationConfigApplicationContext} 并注解指定的 Class
     * @param clazz
     */
    private void annotationConfigApplicationContextConfig(Class<?> clazz) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册配置bean
        applicationContext.register(clazz);
        // 启动 Spring 应用上下文
        applicationContext.refresh();
        // 发布 Spring 事件
        applicationContext.publishEvent(new MyApplicationEvent("Hello, World"));
        // 关闭 Spring 应用上下文
        applicationContext.close();
    }

    /**
     * 注解方式实现事件同步处理
     *
     * <h2>运行结果：</h2>
     * [线程 ：main] 监听到事件：com.xiaobei.spring.demo.event.MyApplicationEvent[source=Hello, World]
     */
    @Test
    public void syncApplicationEventByAnnotation() {
        annotationConfigApplicationContextConfig(SyncApplicationEventConfig.class);
    }

    static class SyncApplicationEventConfig {

        @EventListener
        public void applicationHandler(MyApplicationEvent event) {
            System.out.printf("[线程 ：%s] 监听到事件：%s\n" ,Thread.currentThread().getName(), event);
        }
    }

    /**
     * 注解方式实现事件异步处理
     *
     * <h2>运行结果：</h2>
     * [线程 ：SimpleAsyncTaskExecutor-1] 监听到事件：com.xiaobei.spring.demo.event.MyApplicationEvent[source=Hello, World]
     *
     * @see EnableAsync
     * @see Async
     */
    @Test
    public void asyncApplicationEventByAnnotationDefaultThreadFactory() {
        annotationConfigApplicationContextConfig(AsyncApplicationEventConfig.class);
    }

    @EnableAsync
    static class AsyncApplicationEventConfig {

        @EventListener
        @Async
        public void applicationHandler(MyApplicationEvent event) {
            System.out.printf("[线程 ：%s] 监听到事件：%s\n" ,Thread.currentThread().getName(), event);
        }
    }


    /**
     * 注解方式实现事件异步处理并自定义线程工厂
     *
     * <h2>运行结果：</h2>
     * [线程 ：my-event-thread-pool-annotation1] 监听到事件：com.xiaobei.spring.demo.event.MyApplicationEvent[source=Hello, World]
     *
     * @see EnableAsync
     * @see Async
     */
    @Test
    public void asyncApplicationEventByAnnotationCustomizedThreadFactory() {
        annotationConfigApplicationContextConfig(AsyncApplicationEventConfigIncludeCustomizedThreadFactory.class);
    }

    @EnableAsync
    static class AsyncApplicationEventConfigIncludeCustomizedThreadFactory {

        @EventListener
        @Async
        public void applicationHandler(MyApplicationEvent event) {
            System.out.printf("[线程 ：%s] 监听到事件：%s\n" ,Thread.currentThread().getName(), event);
        }

        @Bean
        public Executor taskExecutor() {
            return Executors.newSingleThreadScheduledExecutor(new CustomizableThreadFactory("my-event-thread-pool-annotation"));
        }
    }

}
