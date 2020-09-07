package com.xiaobei.spring.demo.dependency.injection;

import com.xiaobei.spring.demo.dependency.domain.User;
import com.xiaobei.spring.demo.dependency.domain.UserHolder;
import org.junit.Test;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.context.annotation.*;

import javax.annotation.Resource;
import javax.inject.Inject;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-08-05 09:19:19
 */
public class DependencyFieldInjectionDemo {

    @Configuration
    static class UserConfig {

        @Bean
        public User user() {
            return new User().setAge(20200907).setName("naTie");
        }

        @Bean
        public UserHolder userHolder(User user) {
            return new UserHolder(user);
        }
    }

    @Configuration
    @Import(UserConfig.class)
    static class AutowiredAnnotationConfig {

        @Autowired
        private UserHolder userHolder;

        @Autowired
        private static UserHolder userHolderStatic;

    }

    /**
     * {@link Autowired} 注入时只能是对象字段（或实例字段），当在静态字段上使用时会直接忽略
     * <h2>运行结果：</h2>
     * UserHolder{user=User{age=20200907, name='naTie'}}
     * null
     * @see AutowiredAnnotationBeanPostProcessor#processInjection(java.lang.Object)
     */
    @Test
    public void fieldInjectionByAutowiredAnnotation() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 执行register操作后 DependencyFieldInjectionDemo 就会变成一个bean
        applicationContext.register(AutowiredAnnotationConfig.class);
        applicationContext.refresh();
        AutowiredAnnotationConfig autowiredConfig = applicationContext.getBean(AutowiredAnnotationConfig.class);
        System.out.println(autowiredConfig.userHolder);
        System.out.println(AutowiredAnnotationConfig.userHolderStatic);
    }


    @Configuration
    @Import(UserConfig.class)
    static class ResourceAnnotationConfig {

        @Resource
        private UserHolder userHolder;

        /**
         * 将会抛出异常
         */
        @Resource
        private static UserHolder userHolderStatic;

    }

    /**
     * {@link Resource} 注入时只能是对象字段（或实例字段），当在静态字段上使用时将抛出异常：
     * {@code java.lang.IllegalStateException: @Resource annotation is not supported on static fields}
     * <h2>运行结果：</h2>
     * UserHolder{user=User{age=20200907, name='naTie'}}
     * null
     * @see CommonAnnotationBeanPostProcessor#postProcessProperties(PropertyValues, Object, String)
     */
    @Test
    public void fieldInjectionByResourceAnnotation() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 执行register操作后 DependencyFieldInjectionDemo 就会变成一个bean
        applicationContext.register(ResourceAnnotationConfig.class);
        applicationContext.refresh();
        ResourceAnnotationConfig resourceConfig = applicationContext.getBean(ResourceAnnotationConfig.class);
        System.out.println(resourceConfig.userHolder);
        System.out.println(ResourceAnnotationConfig.userHolderStatic);
    }


    @Configuration
    @Import(UserConfig.class)
    static class InjectAnnotationConfig {

        @Inject
        private UserHolder userHolder;

        /**
         * 将会抛出异常
         */
        @Inject
        private static UserHolder userHolderStatic;

    }

    /**
     * {@link Inject} 注入时只能是对象字段（或实例字段），当在静态字段上使用时会直接忽略
     * <h2>运行结果：</h2>
     * UserHolder{user=User{age=20200907, name='naTie'}}
     * null
     * @see AutowiredAnnotationBeanPostProcessor#processInjection(java.lang.Object)
     */
    @Test
    public void fieldInjectionByInjectAnnotation() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 执行register操作后 DependencyFieldInjectionDemo 就会变成一个bean
        applicationContext.register(InjectAnnotationConfig.class);
        applicationContext.refresh();
        InjectAnnotationConfig injectConfig = applicationContext.getBean(InjectAnnotationConfig.class);
        System.out.println(injectConfig.userHolder);
        System.out.println(ResourceAnnotationConfig.userHolderStatic);
    }


}