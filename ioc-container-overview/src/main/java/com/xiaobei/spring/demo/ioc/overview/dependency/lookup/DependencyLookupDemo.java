package com.xiaobei.spring.demo.ioc.overview.dependency.lookup;

import com.xiaobei.spring.demo.ioc.overview.annotation.Super;
import com.xiaobei.spring.demo.ioc.overview.domain.User;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.LifecycleProcessor;
import org.springframework.context.MessageSource;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;

import java.util.Map;

/**
 * 依赖查找
 *
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/7/29 7:09
 */
@SuppressWarnings("DuplicatedCode")
public class DependencyLookupDemo {

    /**
     * 根据注解查找 集合对象
     */
    @Test
    public void lookupByAnnotationTypeInRealTime() {
        // 启动spring应用上下文
        BeanFactory beanFactory = new ClassPathXmlApplicationContext(
                "classpath:/META-INF/dependency-lookup.xml");
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> users = (Map) listableBeanFactory.getBeansWithAnnotation(Super.class);
            System.out.println("查找到所有标注了 @Super 的 User 集合对象为：" + users);
        }
    }

    @Test
    public void lookupByNameAndTypeInLazy() {
        // 启动spring应用上下文
        BeanFactory beanFactory = new ClassPathXmlApplicationContext(
                "classpath:/META-INF/dependency-lookup.xml");

        // TODO 根据名称 + 类型延时查找
    }

    /**
     * 根据名称 + 类型实时查找
     */
    @Test
    public void lookupByNameAndTypeInRealTime() {
        // 启动spring应用上下文
        BeanFactory beanFactory = new ClassPathXmlApplicationContext(
                "classpath:/META-INF/dependency-lookup.xml");
        User user = beanFactory.getBean("user", User.class);
        System.out.println(user);
    }

    /**
     * 根据类型查找bean的集合
     * 可能需要使用 {@link ListableBeanFactory}
     */
    @Test
    public void lookupCollectionByTypeInRealTime() {
        // 启动spring应用上下文
        BeanFactory beanFactory = new ClassPathXmlApplicationContext(
                "classpath:/META-INF/dependency-lookup.xml");
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> users = listableBeanFactory.getBeansOfType(User.class);
            System.out.println("查找到所有的 User 集合对象为：" + users);
        }
    }

    /**
     * 通过类型实时查找单个Bean
     */
    @Test
    public void lookupByTypeInRealTime() {
        // 启动spring应用上下文
        BeanFactory beanFactory = new ClassPathXmlApplicationContext(
                "classpath:/META-INF/dependency-lookup.xml");
        User user = beanFactory.getBean(User.class);
        System.out.println("通过类型实时查找：" + user);
    }

    /**
     * 根据Bean名称延时查找
     * <p>
     * ObjectFactory 不会生成新的Bean
     */
    @Test
    public void lookupByNameInLazy() {
        // 启动spring应用上下文
        BeanFactory beanFactory = new ClassPathXmlApplicationContext(
                "classpath:/META-INF/dependency-lookup.xml");
        ObjectFactory<User> objectFactory = (ObjectFactory<User>) beanFactory.getBean("objectFactory");
        User user = objectFactory.getObject();
        System.out.println("延迟查找：" + user);
    }

    /**
     * 根据Bean名称实时查找
     */
    @Test
    public void lookupByNameInRealTime() {
        // 启动spring应用上下文
        BeanFactory beanFactory = new ClassPathXmlApplicationContext(
                "classpath:/META-INF/dependency-lookup.xml");
        User user = (User) beanFactory.getBean("user");
        System.out.println("实时查找：" + user);
    }


    /**
     * 依赖查找容器内建的Bean对象
     * <h2>运行结果：</h2>
     * spring Environment抽象：StandardEnvironment {activeProfiles=[], defaultProfiles=[default], propertySources=[PropertiesPropertySource {name='systemProperties'}, SystemEnvironmentPropertySource {name='systemEnvironment'}]}
     * java环境变量：{java.runtime.name=Java(TM) SE Runtime Environment, ... , sun.cpu.isalist=amd64}
     * 系统环境变量：{USERDOMAIN_ROAMINGPROFILE=DESKTOP-2NUB409, ... , IDEA_INITIAL_DIRECTORY=D:\Program Files\ideaIU-2020.2.win\bin}
     * 国际化相关，默认是一个空对象：Empty MessageSource
     * spring 事件发布器：org.springframework.context.event.SimpleApplicationEventMulticaster@370736d9
     * 生命周期处理：org.springframework.context.support.DefaultLifecycleProcessor@5f9d02cb
     */
    @Test
    public void dependencyLookupBuildInBean() {
        ClassPathXmlApplicationContext beanFactory = new ClassPathXmlApplicationContext();
        // 启动spring应用上下文
        beanFactory.refresh();
        // 其中 {@link Environment} 是外部化配置和profile的综合体
        // TODO 会在Environment抽象一节中单独讨论，情况比较复杂
        Environment environment = beanFactory.getBean(ConfigurableApplicationContext.ENVIRONMENT_BEAN_NAME, Environment.class);
        System.out.println("spring Environment抽象：" + environment);

        Map<String, Object> systemProperties = beanFactory.getBean(ConfigurableApplicationContext.SYSTEM_PROPERTIES_BEAN_NAME, Map.class);
        System.out.println("java环境变量：" + systemProperties);

        Map<String, Object> systemEnvironment = beanFactory.getBean(ConfigurableApplicationContext.SYSTEM_ENVIRONMENT_BEAN_NAME, Map.class);
        System.out.println("系统环境变量：" + systemEnvironment);

        MessageSource messageSource = beanFactory.getBean(AbstractApplicationContext.MESSAGE_SOURCE_BEAN_NAME, MessageSource.class);
        System.out.println("国际化相关，默认是一个空对象：" + messageSource);

        ApplicationEventMulticaster applicationEventMulticaster =
                beanFactory.getBean(AbstractApplicationContext.APPLICATION_EVENT_MULTICASTER_BEAN_NAME, ApplicationEventMulticaster.class);
        System.out.println("spring 事件发布器：" + applicationEventMulticaster);

        LifecycleProcessor lifecycleProcessor = beanFactory.getBean(AbstractApplicationContext.LIFECYCLE_PROCESSOR_BEAN_NAME, LifecycleProcessor.class);
        System.out.println("生命周期处理：" + lifecycleProcessor);
        beanFactory.close();
    }
}
