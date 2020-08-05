package com.xiaobei.spring.demo.dependency.injection;

import com.xiaobei.spring.demo.dependency.domain.User;
import com.xiaobei.spring.demo.dependency.domain.UserHolder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

/**
 * 方法注入
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-08-05 09:38:38
 */
public class DependencyMethodInjectionDemo {

    private UserHolder userHolder;

    private UserHolder userHolder2;

    private static UserHolder userHolderStatic1;

    private static UserHolder userHolderStatic2;

    @Autowired
    public void initUserHolder(UserHolder userHolder) {
        this.userHolder = userHolder;
    }

    @Resource
    public void initUserHolder2(UserHolder userHolder) {
        this.userHolder2 = userHolder;
    }

    @Autowired
    public void initUserHolderStatic1(UserHolder userHolder) {
        userHolderStatic1 = userHolder;
    }

    @Resource
    public void initUserHolderStatic2(UserHolder userHolder) {
        userHolderStatic2 = userHolder;
    }

//    @Autowired
    public static void initUserHolderStatic11(UserHolder userHolder) {
        userHolderStatic1 = userHolder;
    }

//    @Resource
    public static void initUserHolderStatic22(UserHolder userHolder) {
        userHolderStatic2 = userHolder;
    }



    @Bean
    public UserHolder userHolder(User user) {
        return new UserHolder().setUser(user);
    }

    @Bean
    public User user() {
        return new User().setAge(58).setName("58 方法注入：方法注入是@Autowired专利吗？");
    }


    /**
     * 注意：{@link Autowired}不会处理静态方法，只会处理实例方法
     * {@link Autowired} 标注在静态方法上时，会忽略静态方法
     * {@link Resource} 标注在静态方法上时，会抛出异常 {@link IllegalStateException} @Resource annotation is not supported on static methods
     * 示例
     * UserHolder{user=User{age=58, name='58 方法注入：方法注入是@Autowired专利吗？'}}
     * UserHolder{user=User{age=58, name='58 方法注入：方法注入是@Autowired专利吗？'}}
     * UserHolder{user=User{age=58, name='58 方法注入：方法注入是@Autowired专利吗？'}}
     * UserHolder{user=User{age=58, name='58 方法注入：方法注入是@Autowired专利吗？'}}
     * true
     * true
     */
    @Test
    public void fieldInjection() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 执行register操作后 DependencyFieldInjectionDemo 就会变成一个bean
        applicationContext.register(DependencyMethodInjectionDemo.class);
        applicationContext.refresh();
        DependencyMethodInjectionDemo bean = applicationContext.getBean(DependencyMethodInjectionDemo.class);
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