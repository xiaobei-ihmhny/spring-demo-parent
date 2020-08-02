package com.xiaobei.spring.demo.bean.definition.lazy;

import com.xiaobei.spring.demo.bean.definition.initialization.BeanInitialization;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/2 9:11
 */
public class LazyBeanInitializationDemo {

    /**
     * 延迟加载的bean只有在获取的时候才会被初始化..，
     * 而非延迟加载的bean会在spring上下文启动的过程中完成初始化
     * msg：非延迟加载
     * spring上下文启动成功...
     * msg：延迟加载
     * @param args
     */
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(LazyBeanInitializationConf.class);
        applicationContext.refresh();
        System.out.println("spring上下文启动成功...");
        applicationContext.getBean("beanInitializationLazy", BeanInitialization.class);
        applicationContext.getBean("beanInitialization", BeanInitialization.class);
        applicationContext.close();
    }
}
