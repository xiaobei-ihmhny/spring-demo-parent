package com.xiaobei.spring.demo.event;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import static org.springframework.context.support.AbstractApplicationContext.APPLICATION_EVENT_MULTICASTER_BEAN_NAME;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/25 10:16
 */
public class AsyncEventHandlerDemo {

    public static void main(String[] args) {
        // [线程 ：main] 监听到事件：com.xiaobei.spring.demo.event.MyApplicationEvent[source=Hello, World]
//        syncApplicationEvent();
        // [线程 ：pool-1-thread-1] 监听到事件：com.xiaobei.spring.demo.event.MyApplicationEvent[source=Hello, World]
        // 当前操作发生异常，异常信息为：java.lang.RuntimeException: 发生异常
//        asyncApplicationEventDefaultThreadFactory();
        // [线程 ：my-event-thread-pool1] 监听到事件：com.xiaobei.spring.demo.event.MyApplicationEvent[source=Hello, World]
        // 当前操作发生异常，异常信息为：java.lang.RuntimeException: 发生异常
//        asyncApplicationEventCustomizedThreadFactory();
        // @Autowired方式配置 Multicaster时，事件发生异常 java.lang.RuntimeException: 发生异常....
        // [线程 ：main] 监听到事件：com.xiaobei.spring.demo.event.MyApplicationEvent[source=Hello, World]
//        syncApplicationEventByAnnotation();
        // @Autowired方式配置 Multicaster时，事件发生异常 java.lang.RuntimeException: 发生异常....
        // 八月 25, 2020 2:33:26 下午 org.springframework.aop.interceptor.AsyncExecutionAspectSupport getDefaultExecutor
        // 信息: No task executor bean found for async processing: no bean of type TaskExecutor and no bean named 'taskExecutor' either
        // [线程 ：SimpleAsyncTaskExecutor-1] 监听到事件：com.xiaobei.spring.demo.event.MyApplicationEvent[source=Hello, World]
//        asyncApplicationEventByAnnotationDefaultThreadFactory();
        // @Autowired方式配置 Multicaster时，事件发生异常 java.lang.RuntimeException: 发生异常....
        // [线程 ：my-event-thread-pool-annotation1] 监听到事件：com.xiaobei.spring.demo.event.MyApplicationEvent[source=Hello, World]
//        asyncApplicationEventByAnnotationCustomizedThreadFactory();
    }

    /**
     * 以同步方式实现
     *
     * <h2>运行结果：</h2>
     * [线程 ：main] 监听到事件：com.xiaobei.spring.demo.event.MyApplicationEvent[source=Hello, World]
     */
    @Test
    public static void syncApplicationEvent() {
        genericApplicationContextConfig(applicationContext -> {
        });
    }

    /**
     * 在{@link ApplicationEventMulticaster} 中添加 {@link java.util.concurrent.Executor} 实现
     * 并使用默认的线程工厂
     *
     * <h2>运行结果：</h2>
     * [线程 ：pool-1-thread-1] 监听到事件：com.xiaobei.spring.demo.event.MyApplicationEvent[source=Hello, World]
     */
    @Test
    public static void asyncApplicationEventDefaultThreadFactory() {
        genericApplicationContextConfig(applicationContext -> {
            // 获取 {@code ApplicationEventMulticaster}
            ApplicationEventMulticaster applicationEventMulticaster =
                    applicationContext.getBean(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, ApplicationEventMulticaster.class);
            if (applicationEventMulticaster instanceof SimpleApplicationEventMulticaster) {
                SimpleApplicationEventMulticaster multicaster = (SimpleApplicationEventMulticaster) applicationEventMulticaster;
                ExecutorService taskExecutor = Executors.newSingleThreadExecutor();
                // 为当前的 {@code ApplicationEventMulticaster} 添加线程池
                multicaster.setTaskExecutor(taskExecutor);
                // 为当前的 {@code ApplicationEventMulticaster} 添加异常处理
                multicaster.setErrorHandler(t -> System.err.printf("当前操作发生异常，异常信息为：%s\n", t));
                shutDownThePool(applicationEventMulticaster, taskExecutor);
            }
            applicationContext.addApplicationListener((ApplicationListener<MyApplicationEvent>) event -> {
                throw new RuntimeException("发生异常");
            });
        });
    }

    /**
     * 关闭线程池
     * @param applicationEventMulticaster
     * @param taskExecutor
     */
    private static void shutDownThePool(ApplicationEventMulticaster applicationEventMulticaster, ExecutorService taskExecutor) {
        // 添加对 ContextClosedEvent 事件处理，在应用上下文关闭时关闭线程池
        applicationEventMulticaster.addApplicationListener((ApplicationListener<ContextClosedEvent>) closedEvent -> {
            if (!taskExecutor.isShutdown()) {
                taskExecutor.shutdown();
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
    public static void asyncApplicationEventCustomizedThreadFactory() {
        genericApplicationContextConfig(applicationContext -> {
            // 获取 {@code ApplicationEventMulticaster}
            ApplicationEventMulticaster applicationEventMulticaster =
                    applicationContext.getBean(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, ApplicationEventMulticaster.class);
            if (applicationEventMulticaster instanceof SimpleApplicationEventMulticaster) {
                SimpleApplicationEventMulticaster multicaster = (SimpleApplicationEventMulticaster) applicationEventMulticaster;
                ExecutorService taskExecutor = Executors.newSingleThreadExecutor(new CustomizableThreadFactory("my-event-thread-pool"));
                multicaster.setTaskExecutor(taskExecutor);
                // 为当前的 {@code ApplicationEventMulticaster} 添加异常处理
                multicaster.setErrorHandler(t -> System.err.printf("当前操作发生异常，异常信息为：%s\n", t));
                shutDownThePool(applicationEventMulticaster, taskExecutor);
            }
            applicationContext.addApplicationListener((ApplicationListener<MyApplicationEvent>) event -> {
                throw new RuntimeException("发生异常");
            });
        });
    }

    /**
     * {@link GenericApplicationContext} 配置
     *
     * @param multicasterConsumer 进行自定义 {@link ApplicationEventMulticaster} 的处理
     */
    private static void genericApplicationContextConfig(Consumer<GenericApplicationContext> multicasterConsumer) {
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
     *
     * @param clazz
     */
    private static void annotationConfigApplicationContextConfig(Class<?> clazz) {
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
    public static void syncApplicationEventByAnnotation() {
        annotationConfigApplicationContextConfig(SyncApplicationEventConfig.class);
    }

    static class SyncApplicationEventConfig {

        @Autowired
        private ApplicationEventMulticaster applicationEventMulticaster;

        @PostConstruct
        public void initErrorHandler() {
            if(applicationEventMulticaster instanceof SimpleApplicationEventMulticaster) {
                SimpleApplicationEventMulticaster multicaster = (SimpleApplicationEventMulticaster) applicationEventMulticaster;
                multicaster.setErrorHandler(t -> System.err.printf("@Autowired方式配置 Multicaster时，事件发生异常 %s\n", t));
            }
        }

        @EventListener
        public void error(MyApplicationEvent event) {
            throw new RuntimeException("发生异常....");
        }

        @EventListener
        public void applicationHandler(MyApplicationEvent event) {
            System.out.printf("[线程 ：%s] 监听到事件：%s\n", Thread.currentThread().getName(), event);
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
    public static void asyncApplicationEventByAnnotationDefaultThreadFactory() {
        annotationConfigApplicationContextConfig(AsyncApplicationEventConfig.class);
    }

    @EnableAsync
    static class AsyncApplicationEventConfig {

        @Autowired
        private ApplicationEventMulticaster applicationEventMulticaster;

        @PostConstruct
        public void initErrorHandler() {
            if(applicationEventMulticaster instanceof SimpleApplicationEventMulticaster) {
                SimpleApplicationEventMulticaster multicaster = (SimpleApplicationEventMulticaster) applicationEventMulticaster;
                multicaster.setErrorHandler(t -> System.err.printf("@Autowired方式配置 Multicaster时，事件发生异常 %s\n", t));
            }
        }

        @EventListener
        public void error(MyApplicationEvent event) {
            throw new RuntimeException("发生异常....");
        }

        @EventListener
        @Async
        public void applicationHandler(MyApplicationEvent event) {
            System.out.printf("[线程 ：%s] 监听到事件：%s\n", Thread.currentThread().getName(), event);
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
    public static void asyncApplicationEventByAnnotationCustomizedThreadFactory() {
        annotationConfigApplicationContextConfig(AsyncApplicationEventConfigIncludeCustomizedThreadFactory.class);
    }

    @EnableAsync
    static class AsyncApplicationEventConfigIncludeCustomizedThreadFactory {

        @Autowired
        private ApplicationEventMulticaster applicationEventMulticaster;

        @PostConstruct
        public void initErrorHandler() {
            if(applicationEventMulticaster instanceof SimpleApplicationEventMulticaster) {
                SimpleApplicationEventMulticaster multicaster = (SimpleApplicationEventMulticaster) applicationEventMulticaster;
                multicaster.setErrorHandler(t -> System.err.printf("@Autowired方式配置 Multicaster时，事件发生异常 %s\n", t));
            }
        }

        @EventListener
        public void error(MyApplicationEvent event) {
            throw new RuntimeException("发生异常....");
        }
        @EventListener
        @Async
        public void applicationHandler(MyApplicationEvent event) {
            System.out.printf("[线程 ：%s] 监听到事件：%s\n", Thread.currentThread().getName(), event);
        }

        @Bean
        public Executor taskExecutor() {
            return Executors.newSingleThreadScheduledExecutor(new CustomizableThreadFactory("my-event-thread-pool-annotation"));
        }
    }

}
