package com.xiaobei.spring.demo.dependency.injection;

import com.xiaobei.spring.demo.dependency.domain.User;
import com.xiaobei.spring.demo.dependency.domain.UserHolder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-08-05 09:19:19
 */
public class DependencyFieldInjectionDemo {

    @Autowired
    private UserHolder userHolder;

    @Autowired
    private static UserHolder userHolderStatic1;

    @Resource
    private UserHolder userHolder2;

//    @Resource
    private static UserHolder userHolderStatic2;

    @Bean
    public UserHolder userHolder(User user) {
        return new UserHolder().setUser(user);
    }

    @Bean
    public User user() {
        return new User().setAge(57).setName("57 字段注入：为什么Spring官方文档没有单独列举这种注入方式？");
    }


    /**
     * 注意：{@link Autowired}不会处理静态字段，只会处理对象字段或实例字段
     * {@link Autowired} 标注在静态字段上时，会忽略静态字段
     * {@link Resource} 标注在静态字段上时，会抛出异常 {@link IllegalStateException} @Resource annotation is not supported on static fields
     * 示例
     * UserHolder{user=User{age=57, name='57 字段注入：为什么Spring官方文档没有单独列举这种注入方式？'}}
     * UserHolder{user=User{age=57, name='57 字段注入：为什么Spring官方文档没有单独列举这种注入方式？'}}
     * true
     * true
     */
    @Test
    public void fieldInjection() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 执行register操作后 DependencyFieldInjectionDemo 就会变成一个bean
        applicationContext.register(DependencyFieldInjectionDemo.class);
        applicationContext.refresh();
        DependencyFieldInjectionDemo bean = applicationContext.getBean(DependencyFieldInjectionDemo.class);
        System.out.println(bean.userHolder);
        System.out.println(bean.userHolder2);
        System.out.println(userHolderStatic1);
        System.out.println(userHolderStatic2);
        UserHolder userHolder = applicationContext.getBean(UserHolder.class);
        System.out.println(bean.userHolder == bean.userHolder2);
        System.out.println(bean.userHolder == userHolder);
        applicationContext.close();
    }
}