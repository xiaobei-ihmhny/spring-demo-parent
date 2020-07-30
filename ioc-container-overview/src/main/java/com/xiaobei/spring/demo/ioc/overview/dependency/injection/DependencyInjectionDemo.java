package com.xiaobei.spring.demo.ioc.overview.dependency.injection;

import com.xiaobei.spring.demo.ioc.overview.repository.UserRespoitory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/7/29 7:09
 */
public class DependencyInjectionDemo {

    public static void main(String[] args) {
        // 配置xml配置文件
        // 启动spring应用上下文
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-injection.xml");

        // 根据Bean类型注入 集合Bean对象
//        injectionCollectionByTypeInRealTime(beanFactory);
        injectionInsideDependencyNonBean(beanFactory);

    }

    /**
     * 注入内建的非Bean对象（依赖）
     * TODO 依赖注入和依赖查找的来源是同一个吗？答案是：不是
     * @param beanFactory
     */
    private static void injectionInsideDependencyNonBean(BeanFactory beanFactory) {
        UserRespoitory userRepository = beanFactory.getBean("userRepository", UserRespoitory.class);
        // 依赖注入
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
     * @param beanFactory
     */
    private static void injectionCollectionByTypeInRealTime(BeanFactory beanFactory) {
        UserRespoitory userRepository = beanFactory.getBean("userRepository", UserRespoitory.class);
        System.out.println(userRepository);
    }


}
