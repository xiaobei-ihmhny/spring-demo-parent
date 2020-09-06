package com.xiaobei.spring.demo.bean.definition;

import com.xiaobei.spring.demo.ioc.overview.domain.User;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.parsing.AliasDefinition;
import org.springframework.beans.factory.support.*;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/1 13:17
 */
public class BeanAliasDemo {

    /**
     * 通过 xml 的方式设置 bean 的别名
     * <h2>运行结果：</h2>
     * [alias标签] xiaobei-user 与 user 是否相等：true
     * [name属性使用 半角,] hui-user 与 hui 是否相等：true
     * [name属性使用 半角;] natie-user 与 natie 是否相等：true
     */
    @Test
    public void BeanAliasByXml() {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/bean-definitions-context.xml");
        // 1. 通过别名标签 alias 的方式配置别名
        User xiaobeiUser = beanFactory.getBean("xiaobei-user", User.class);
        User user = beanFactory.getBean("user", User.class);
        System.out.printf("[alias标签] xiaobei-user 与 user 是否相等：%s\n", (user == xiaobeiUser));
        // 2. 通过 bean 标签的 name 属性，将多个名称之间用 半角 , 隔开，注意 id 属性只能指定唯一的名称
        User hui = beanFactory.getBean("hui", User.class);
        User huiUser = beanFactory.getBean("hui-user", User.class);
        System.out.printf("[name属性使用 半角,] hui-user 与 hui 是否相等：%s\n", (hui == huiUser));
        // 3. 通过 bean 标签的 name 属性，将多个名称之间用 半角 ; 隔开，注意 id 属性只能指定唯一的名称
        User natie = beanFactory.getBean("natie", User.class);
        User natieUser = beanFactory.getBean("natie-user", User.class);
        System.out.printf("[name属性使用 半角;] natie-user 与 natie 是否相等：%s\n", (natie == natieUser));
    }

    /**
     * 通过注解方式配置Bean的别名
     *
     * <h2>运行结果：</h2>
     * [@Bean name属性指定多个] natie-user 与 natie 是否相等：true
     * [@Bean value属性指定多个] xiao-otherUser 与 otherUser 是否相等：true
     */
    @Test
    public void BeanAliasByAnnotation() {
        AnnotationConfigApplicationContext beanFactory = new AnnotationConfigApplicationContext();
        // 注册配置类
        beanFactory.register(BeanAliasConfig.class);
        // 启动 Spring 应用上下文
        beanFactory.refresh();
        User user = beanFactory.getBean("user", User.class);
        User xiaoUser = beanFactory.getBean("xiao-user", User.class);
        System.out.printf("[@Bean name属性指定多个] natie-user 与 natie 是否相等：%s\n", (user == xiaoUser));
        User otherUser = beanFactory.getBean("otherUser", User.class);
        User xiaoOtherUser = beanFactory.getBean("xiao-otherUser", User.class);
        System.out.printf("[@Bean value属性指定多个] xiao-otherUser 与 otherUser 是否相等：%s\n", (otherUser == xiaoOtherUser));
        // 关闭 Spring 应用上下文
        beanFactory.close();
    }

    /**
     *  Bean 别名 注解配置类
     */
    @Configuration
    static class BeanAliasConfig {

        @Bean(name = {"user", "xiao-user"})
        public User user() {
            User user = new User();
            user.setId(20200906L);
            user.setName("注解方式配置 Bean 的别名");
            return user;
        }

        @Bean(value = {"otherUser", "xiao-otherUser"})
        public User otherUser() {
            User user = new User();
            user.setId(20200906L);
            user.setName("注解方式配置 Bean 的别名");
            return user;
        }

    }

    /**
     * 通过 api 的方式设置bean的别名
     *
     * <h2>运行结果：</h2>
     * [alias标签] xiaobei-user 与 user 是否相等：true
     *
     * @see ConfigurableBeanFactory#registerAlias(java.lang.String, java.lang.String)
     */
    @Test
    public void BeanAliasByApi() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        BeanDefinitionBuilder definitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        definitionBuilder.addPropertyValue("id", 20200906L)
                .addPropertyValue("name", "api方式配置 Bean 的别名");
        AbstractBeanDefinition beanDefinition = definitionBuilder.getBeanDefinition();
        beanFactory.registerBeanDefinition("user", beanDefinition);
        // 添加名称为 user 的 bean 的别名 xiao-user
        beanFactory.registerAlias("user", "xiao-user");
        User user = beanFactory.getBean("user", User.class);
        User xiaoUser = beanFactory.getBean("xiao-user", User.class);
        System.out.printf("[alias标签] xiaobei-user 与 user 是否相等：%s\n", (user == xiaoUser));
    }

}
