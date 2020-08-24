package com.xiaobei.spring.demo.event;

import org.springframework.context.support.GenericApplicationContext;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/24 21:18
 */
public class CustomizedSpringEventDemo {

    /**
     * <h2>运行结果：</h2>
     * [线程 ：main] 监听到事件：com.xiaobei.spring.demo.event.MyApplicationEvent[source=Hello, World]
     * @param args
     */
    public static void main(String[] args) {
        GenericApplicationContext applicationContext = new GenericApplicationContext();
        // 1. 添加自定义 Spring 事件监听器
        applicationContext.addApplicationListener(new MyApplicationListener());
        // 2. 启动 Spring 应用上下文
        applicationContext.refresh();
        // 3. 发布自定义 Spring 事件
        applicationContext.publishEvent(new MyApplicationEvent("Hello, World"));
        // 4. 关闭 Spring 应用上下文
        applicationContext.close();
    }
}
