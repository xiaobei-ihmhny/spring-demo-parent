package com.xiaobei.spring.demo.application.context.lifecycle;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.support.GenericApplicationContext;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/29 20:40
 */
public class LifecycleDemo {

    /**
     * <h2>运行结果：</h2>
     * MyLifecycle 已启动...
     * MyLifecycle 已停止
     *
     * 通过实现 {@link org.springframework.context.Lifecycle} 来通过应用上下文的启动与停止触发相应Bean的启动、停止
     * @param args
     */
    public static void main(String[] args) {
        GenericApplicationContext applicationContext = new GenericApplicationContext();
        applicationContext.registerBeanDefinition("myLifecycle",
                BeanDefinitionBuilder.genericBeanDefinition(MyLifecycle.class).getBeanDefinition());
        // 刷新 Spring 应用上下文
        applicationContext.refresh();
        // 启动  Spring 应用上下文
        applicationContext.start();
        // 停止  Spring 应用上下文
        applicationContext.stop();
        // 关闭  Spring 应用上下文
        applicationContext.close();
    }
}
