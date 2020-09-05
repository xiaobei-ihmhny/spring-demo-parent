package com.xiaobei.spring.demo.ioc.overview.dependency.injection;

import com.xiaobei.spring.demo.ioc.overview.repository.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.LifecycleProcessor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;

import java.util.Map;

/**
 *
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/7/29 7:09
 */
@SuppressWarnings("DuplicatedCode")
public class DependencyInjectionByXmlDemo {


    /**
     * 根据Bean名称进行setter注入
     * <h2>运行结果：</h2>
     * UserRepository{user=User{id=1, name='xiaobei'},
     *   users=[User{id=1, name='xiaobei'}, SuperUser{address='北京'} User{id=1, name='xiaobei'}],
     *   beanFactory=null,
     *   userObjectFactory=null,
     *   applicationContext=null,
     *   objectFactory=null
     * }
     *
     * UserRepository{user=User{id=1, name='xiaobei'},
     *   users=null,
     *   beanFactory=null,
     *   userObjectFactory=null,
     *   applicationContext=null,
     *   objectFactory=null
     * }
     */
    @Test
    public void dependencyInjectionByName() {
        // 启动spring应用上下文
        ClassPathXmlApplicationContext beanFactory = new ClassPathXmlApplicationContext(
                "classpath:/META-INF/dependency-injection-byName.xml");
        beanFactory.refresh();
        /* 手动注入 */
        UserRepository userRepository = beanFactory.getBean("userRepository", UserRepository.class);
        /* 名称自动注入 */
        UserRepository userRepositoryByName = beanFactory.getBean("userRepositoryByName", UserRepository.class);
        System.out.println(userRepository);
        System.out.println(userRepositoryByName);
        beanFactory.close();
    }

    /**
     * 根据类型自动注入，自定义Bean以及非Bean对象
     * <h2>运行结果：</h2>
     * UserRepository{user=SuperUser{address='北京'} User{id=1, name='xiaobei'},
     *   users=[User{id=1, name='xiaobei'}, SuperUser{address='北京'} User{id=1, name='xiaobei'}],
     *   beanFactory=org.springframework.beans.factory.support.DefaultListableBeanFactory@fe18270: defining beans [user,superUser,userRepository]; root of factory hierarchy,
     *   userObjectFactory=org.springframework.beans.factory.support.DefaultListableBeanFactory$DependencyObjectProvider@5123a213,
     *   applicationContext=org.springframework.context.support.ClassPathXmlApplicationContext@57fa26b7, started on Sat Sep 05 17:22:25 CST 2020,
     *   objectFactory=org.springframework.beans.factory.support.DefaultListableBeanFactory$DependencyObjectProvider@3b94d659,
     *   objectFactory.getObject=org.springframework.context.support.ClassPathXmlApplicationContext@57fa26b7, started on Sat Sep 05 17:22:25 CST 2020
     * }
     *
     * 注意加载位置：
     * {@link AbstractApplicationContext#prepareBeanFactory(ConfigurableListableBeanFactory)}
     * {@code beanFactory.registerResolvableDependency(...)}
     *
     * <h2>其中对可查找依赖（非Bean对象）的说明</h2>
     * <h3>对于标准的ApplicationContext -> AbstractApplicationContext而言，可查找依赖有以下几个：
     * (加载位置在AbstractApplicationContext#prepareBeanFactory(ConfigurableListableBeanFactory))</h3>
     * <ul>
     *     <li>beanFactory.registerResolvableDependency(BeanFactory.class, beanFactory);</li>
     *     <li>beanFactory.registerResolvableDependency(ResourceLoader.class, this);</li>
     *     <li>beanFactory.registerResolvableDependency(ApplicationEventPublisher.class, this);</li>
     *     <li>beanFactory.registerResolvableDependency(ApplicationContext.class, this);</li>
     * </ul>
     * <h3>对于 GenericWebApplicationContext 除上述可查找依赖外，还会有以下依赖：
     * （加载位置：AbstractApplicationContext#postProcessBeanFactory(ConfigurableListableBeanFactory)）</h3>
     * <ul>
     *     <li>beanFactory.registerResolvableDependency(ServletRequest.class, new RequestObjectFactory());
     *     <li>beanFactory.registerResolvableDependency(ServletResponse.class, new ResponseObjectFactory());</li>
     *     <li>beanFactory.registerResolvableDependency(HttpSession.class, new SessionObjectFactory());</li>
     *     <li>beanFactory.registerResolvableDependency(WebRequest.class, new WebRequestObjectFactory());</li>
     * </ul>
     */
    @Test
    public void dependencyInjectionByType() {
        // 启动spring应用上下文
        ClassPathXmlApplicationContext beanFactory = new ClassPathXmlApplicationContext(
                "classpath:/META-INF/dependency-injection-byType.xml");
        beanFactory.refresh();
        /* 类型自动注入 */
        UserRepository userRepository = beanFactory.getBean("userRepository", UserRepository.class);
        System.out.println(userRepository);
        beanFactory.close();
    }


    @Test
    public void whoIsIoCContainer() {
        // 启动spring应用上下文
        BeanFactory beanFactory = new ClassPathXmlApplicationContext(
                "classpath:/META-INF/dependency-injection.xml");
        // 自定义Bean
        UserRepository userRepository = beanFactory.getBean("userRepository", UserRepository.class);
        // 这个表达式为什么不会成立？
        // ApplicationContext就是BeanFactory
        System.out.println(userRepository.getBeanFactory() == beanFactory);

    }

    /**
     * 展示容器内建的Bean对象
     * Spring IoC依赖来源：
     * 一：自定义Bean
     * 二：容器内建的Bean
     * 三：容器内建依赖
     */
    @Test
    public void showBeansWithInContainer() {
        // 启动spring应用上下文
        BeanFactory beanFactory = new ClassPathXmlApplicationContext(
                "classpath:/META-INF/dependency-injection.xml");
        // 其中 {@link Environment} 是外部化配置和profile的综合体
        // TODO 会在Environment抽象一节中单独讨论，情况比较复杂
        Environment environment = beanFactory.getBean(Environment.class);
        System.out.println(environment);
    }

    /**
     * 注入内建的非Bean对象（依赖）
     * TODO 依赖注入和依赖查找的来源是同一个吗？答案是：不是
     */
    @Test
    public void injectionInsideDependencyNonBean() {
        // 启动spring应用上下文
        ClassPathXmlApplicationContext beanFactory = new ClassPathXmlApplicationContext(
                "classpath:/META-INF/dependency-injection.xml");
        // 自定义Bean
        UserRepository userRepository = beanFactory.getBean("userRepository", UserRepository.class);
        // 依赖注入(内建依赖)
        System.out.println(userRepository.getBeanFactory());
        System.out.println(userRepository.getBeanFactory() == beanFactory);
        // 依赖查找（报错 NoSuchBeanDefinitionException）
//        System.out.println(beanFactory.getBean(BeanFactory.class));
        System.out.println(userRepository.getUserObjectFactory().getObject());
        // ClassPathXmlApplicationContext
        System.out.println(userRepository.getObjectFactory().getObject());
        // true，说明ApplicationContext就是BeanFactory
        System.out.println(userRepository.getObjectFactory().getObject() == beanFactory);
    }

    /**
     * 根据Bean类型注入 集合Bean对象
     */
    @Test
    public void injectionCollectionByTypeInRealTime() {
        // 启动spring应用上下文
        BeanFactory beanFactory = new ClassPathXmlApplicationContext(
                "classpath:/META-INF/dependency-injection.xml");
        UserRepository userRepository = beanFactory.getBean("userRepository", UserRepository.class);
        System.out.println(userRepository);
    }


}
