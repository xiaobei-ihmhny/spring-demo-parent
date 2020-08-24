package com.xiaobei.spring.demo.event;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.annotation.PostConstruct;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/24 21:45
 */
public class InjectingApplicationEventPublisherDemo implements ApplicationEventPublisherAware,
        ApplicationContextAware {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        applicationEventPublisher.publishEvent(new MyApplicationEvent("#3 -> The event from @Autowired ApplicationEventPublisherAware"));
        applicationContext.publishEvent(new MyApplicationEvent("#4 -> The event from @Autowired ApplicationContextAware"));
    }

    /**
     * TODO 复习Bean的生命周期！！！
     *
     * <h2>运行结果：</h2>
     *
     * [线程 ：main] 监听到事件：com.xiaobei.spring.demo.event.MyApplicationEvent[source=#1 -> The event from ApplicationEventPublisherAware]
     * [线程 ：main] 监听到事件：com.xiaobei.spring.demo.event.MyApplicationEvent[source=#2 -> The event from ApplicationContextAware]
     * [线程 ：main] 监听到事件：com.xiaobei.spring.demo.event.MyApplicationEvent[source=#3 -> The event from @Autowired ApplicationEventPublisherAware]
     * [线程 ：main] 监听到事件：com.xiaobei.spring.demo.event.MyApplicationEvent[source=#4 -> The event from @Autowired ApplicationContextAware]
     * @param args
     */
    public static void main(String[] args) {
        // 创建注解驱动的 Spring 应用上下文
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册 Configuration Class
        applicationContext.register(InjectingApplicationEventPublisherDemo.class);
        // 注册 事件监听
        applicationContext.addApplicationListener(new MyApplicationListener());
        // 启动 Spring 应用上下文
        applicationContext.refresh();
        // 关闭 Spring 应用上下文
        applicationContext.close();

    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        applicationEventPublisher.publishEvent(new MyApplicationEvent("#1 -> The event from ApplicationEventPublisherAware"));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContext.publishEvent(new MyApplicationEvent("#2 -> The event from ApplicationContextAware"));
    }
}
