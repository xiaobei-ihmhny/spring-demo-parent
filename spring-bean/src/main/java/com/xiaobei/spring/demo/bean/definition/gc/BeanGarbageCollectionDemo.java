package com.xiaobei.spring.demo.bean.definition.gc;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.TimeUnit;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/2 10:44
 */
public class BeanGarbageCollectionDemo {

    /**
     * 模拟gc回收spring bean
     *
     * 当前bean正在被gc回收...
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(BeanGarbageCollection.class);
        // 启动spring应用上下文
        applicationContext.refresh();
        // 销毁spring应用上下文
        applicationContext.close();
        TimeUnit.SECONDS.sleep(5);
        // 手动调用gc
        System.gc();
        TimeUnit.SECONDS.sleep(5);
    }
}
