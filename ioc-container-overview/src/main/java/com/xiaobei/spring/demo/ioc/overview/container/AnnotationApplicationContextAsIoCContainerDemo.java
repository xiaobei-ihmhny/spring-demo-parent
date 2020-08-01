package com.xiaobei.spring.demo.ioc.overview.container;

import com.xiaobei.spring.demo.ioc.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.AliasFor;

import java.util.Map;

/**
 * 不使用ApplicationContext，只使用BeanFactory时，同样可以使用IoC容器
 * 注解能力 {@link org.springframework.context.ApplicationContext} 作为IoC容器示例
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-07-31 17:05:05
 */
public class AnnotationApplicationContextAsIoCContainerDemo {

    public static void main(String[] args) {
        // 创建ApplicationContext容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 将当前类注册为 (Component Class)
        applicationContext.register(AnnotationApplicationContextAsIoCContainerDemo.class);
        // 启动应用上下文
        applicationContext.refresh();
        AnnotationApplicationContextAsIoCContainerDemo demo
                = applicationContext.getBean(AnnotationApplicationContextAsIoCContainerDemo.class);
        lookupCollectionByTypeInRealTime(applicationContext);
        applicationContext.close();
    }

    /**
     * 通过java注解方式定义一个Bean
     * @return
     */
    @Bean
    public User user() {
        User user = new User();
        user.setId(2L);
        user.setName("小贝贝");
        return user;
    }

    private static void lookupCollectionByTypeInRealTime(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> users = listableBeanFactory.getBeansOfType(User.class);
            System.out.println("查找到所有的 User 集合对象为：" + users);
        }
    }
}