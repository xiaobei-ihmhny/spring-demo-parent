package com.xiaobei.spring.demo.dependency.injection;

import com.xiaobei.spring.demo.dependency.config.UserHolderConstructorConfig;
import com.xiaobei.spring.demo.dependency.domain.SuperUser;
import com.xiaobei.spring.demo.dependency.domain.User;
import com.xiaobei.spring.demo.dependency.domain.UserHolder;
import org.junit.Test;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.*;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.Executable;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/4 22:50
 */
public class DependencyConstructorInjectionDemo {

    /**
     * 构造器注入——手动模式：XML资源配置元信息
     * 运行结果：
     * SuperUser{address='北京'} User{age=10, name='xiaobei'}
     */
    @Test
    public void xmlDependencyConstructorInjection() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        String location = "classpath:/META-INF/dependency-constructor-injection.xml";
        beanDefinitionReader.loadBeanDefinitions(location);
        // 进行依赖查找
        UserHolder userHolder = beanFactory.getBean(UserHolder.class);
        System.out.println(userHolder.getUser());
    }

    /**
     * 构造器注入——手动模式：注解资源配置元信息
     * User{age=56, name='56 构造器依赖注入：官方为什么推荐使用构造器注入？'}
     */
    @Test
    public void annotationDependencyConstructorInjection() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(UserHolderConstructorConfig.class);
        applicationContext.refresh();
        // 进行依赖查找
        UserHolder userHolder = applicationContext.getBean(UserHolder.class);
        System.out.println(userHolder.getUser());
        applicationContext.close();
    }

    /**
     * 构造器注入——手动模式：api资源配置元信息
     * UserHolder{user=SuperUser{address='北京'} User{age=56, name='56 构造器依赖注入：官方为什么推荐使用构造器注入？'}}
     */
    @Test
    public void apiDependencyConstructorInjection() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 构建User对象的BeanDefinition
        BeanDefinitionBuilder userBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        userBuilder.addConstructorArgValue("56");
        userBuilder.addConstructorArgValue("56 构造器依赖注入：官方为什么推荐使用构造器注入？");
        AbstractBeanDefinition userBuilderBeanDefinition = userBuilder.getBeanDefinition();
        // 注册User对象到BeanFactory
        beanFactory.registerBeanDefinition("user", userBuilderBeanDefinition);
        // 构建SuperUser对象的BeanDefinition
        BeanDefinitionBuilder superUserBuilder = BeanDefinitionBuilder.genericBeanDefinition(SuperUser.class);
        superUserBuilder.addConstructorArgValue("56");
        superUserBuilder.addConstructorArgValue("56 构造器依赖注入：官方为什么推荐使用构造器注入？");
        superUserBuilder.addConstructorArgValue("北京");
        AbstractBeanDefinition superUserBuilderBeanDefinition = superUserBuilder.getBeanDefinition();
        // 注册SuperUser对象到BeanFactory
        beanFactory.registerBeanDefinition("superUser", superUserBuilderBeanDefinition);
        // 构建UserHolder的BeanDefinition
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(UserHolder.class);
        builder.addConstructorArgReference("superUser");
        AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();
        // 注册UserHolder对象到BeanFactory
        beanFactory.registerBeanDefinition("userHolder", beanDefinition);
        // 依赖查找UserHolder
        UserHolder userHolder = beanFactory.getBean(UserHolder.class);
        System.out.println(userHolder);
    }

    /**
     * 构造器注入——自动模式：constructor
     * <h2>注入原则如下：</h2>
     * 1. 同类型的bean只有一个时，直接注入
     * 2. 若同类型的bean存在多个，则查看有无 bean 名称与构造器参数名一样的，有直接注入，没有则注入失败
     * @see ConstructorResolver#createArgumentArray(java.lang.String, RootBeanDefinition, ConstructorArgumentValues,
     * BeanWrapper, Class[], String[], Executable, boolean, boolean)
     */
    @Test
    public void autoWringDependencyConstructorInjection() {
        String location = "classpath:/META-INF/autowring-dependency-constructor-injection.xml";
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(location);
        // 进行依赖查找
        UserHolder userHolder = applicationContext.getBean(UserHolder.class);
        System.out.println(userHolder.getUser());
    }
}
