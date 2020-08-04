package com.xiaobei.spring.demo.dependency.injection;

import com.xiaobei.spring.demo.dependency.domain.SuperUser;
import com.xiaobei.spring.demo.dependency.domain.User;
import com.xiaobei.spring.demo.dependency.domain.UserHolder;
import org.junit.Test;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/4 21:11
 */
public class DependencySetterInjectionDemo {

    /**
     * xml配置元信息进行手动的setter方法依赖注入示例
     * @see DefaultListableBeanFactory
     * @see XmlBeanDefinitionReader
     */
    @Test
    public void xmlDependencySetterInjection() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        String location = "classpath:/META-INF/dependency-setter-injection.xml";
        beanDefinitionReader.loadBeanDefinitions(location);
        // 进行依赖查找
        UserHolder userHolder = beanFactory.getBean(UserHolder.class);
        System.out.println(userHolder.getUser());
    }

    /**
     * 注解配置元信息进行手动的setter方法依赖注入示例
     */
    @Test
    public void annotationDependencySetterInjection() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(UserHolderConfig.class);
        applicationContext.refresh();
        // 进行依赖查找
        UserHolder userHolder = applicationContext.getBean(UserHolder.class);
        System.out.println(userHolder.getUser());
        applicationContext.close();
    }

    /**
     * API配置元信息进行手动的setter方法依赖注入示例
     */
    @Test
    public void apiDependencySetterInjection() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 构建User对象的BeanDefinition
        BeanDefinitionBuilder userBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        userBuilder.addPropertyValue("age", "55");
        userBuilder.addPropertyValue("name", "API配置元信息");
        AbstractBeanDefinition userBuilderBeanDefinition = userBuilder.getBeanDefinition();
        // 注册User对象到BeanFactory
        beanFactory.registerBeanDefinition("user", userBuilderBeanDefinition);
        // 构建SuperUser对象的BeanDefinition
        BeanDefinitionBuilder superUserBuilder = BeanDefinitionBuilder.genericBeanDefinition(SuperUser.class);
        superUserBuilder.addPropertyValue("address", "北京昌平");
        superUserBuilder.setParentName("user");
        AbstractBeanDefinition superUserBuilderBeanDefinition = superUserBuilder.getBeanDefinition();
        // 注册SuperUser对象到BeanFactory
        beanFactory.registerBeanDefinition("superUser", superUserBuilderBeanDefinition);
        // 构建UserHolder的BeanDefinition
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(UserHolder.class);
        builder.addPropertyReference("user", "superUser");
        AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();
        // 注册UserHolder对象到BeanFactory
        beanFactory.registerBeanDefinition("userHolder", beanDefinition);
        // 依赖查找UserHolder
        UserHolder userHolder = beanFactory.getBean(UserHolder.class);
        System.out.println(userHolder);
    }

    /**
     * 自动模式的setter方法依赖注入，使用byName类型
     */
    @Test
    public void autoWringByNameDependencySetterInjection() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        String location = "classpath:/META-INF/autowring-byName-dependency-setter-injection.xml";
        beanDefinitionReader.loadBeanDefinitions(location);
        // 进行依赖查找
        UserHolder userHolder = beanFactory.getBean(UserHolder.class);
        System.out.println(userHolder.getUser());
    }

    /**
     * 自动模式的setter方法依赖注入，使用byType类型
     */
    @Test
    public void autoWringByTypeDependencySetterInjection() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        String location = "classpath:/META-INF/autowring-byType-dependency-setter-injection.xml";
        beanDefinitionReader.loadBeanDefinitions(location);
        // 进行依赖查找
        UserHolder userHolder = beanFactory.getBean(UserHolder.class);
        System.out.println(userHolder.getUser());
    }


}
