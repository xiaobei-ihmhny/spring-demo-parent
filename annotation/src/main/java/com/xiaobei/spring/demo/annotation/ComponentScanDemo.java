package com.xiaobei.spring.demo.annotation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/26 22:12
 */
@ComponentScan(basePackages = "com.xiaobei.spring.demo.annotation")
public class ComponentScanDemo {

    /**
     * <h2>运行结果：</h2>
     * UserDomain{id=null}
     *
     * TODO 详细查看 <context:component:scan/> 与 @ComponentScan 的工作原理！！！！
     * @param args
     */
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册当前类为 Configuration Class
        applicationContext.register(ComponentScanDemo.class);
        // 启动 Spring 应用上下文
        applicationContext.refresh();
        // 通过依赖查找的方式来查找自定义的 Component派生注解
        // {@code UserDomain} 标注 {@link HierarchicalCustomizedComponent}
        // {@link HierarchicalCustomizedComponent} <- {@link CustomizedComponent} <- {@link Component}
        // 从 Spring 4.0 开始支持多层次 @Component “派生”
        UserDomain userDomain = applicationContext.getBean("userDomain", UserDomain.class);
        System.out.println(userDomain);
        // 关闭 Spring 应用上下文
        applicationContext.close();
    }
}
