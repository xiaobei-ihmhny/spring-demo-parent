package com.xiaobei.spring.demo.ioc.overview.dependency.lookup;

import com.xiaobei.spring.demo.ioc.overview.dependency.annotation.Super;
import com.xiaobei.spring.demo.ioc.overview.dependency.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * 依赖查找
 *
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/7/29 7:09
 */
public class DependencyLookupDemo {

    public static void main(String[] args) {
        // 配置xml配置文件
        // 启动spring应用上下文
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-lookup.xml");
        lookupByNameInRealTime(beanFactory);
        lookupByNameInLazy(beanFactory);
        lookupByTypeInRealTime(beanFactory);
        lookupCollectionByTypeInRealTime(beanFactory);
        lookupByNameAndTypeInRealTime(beanFactory);
        lookupByNameAndTypeInLazy(beanFactory);
        lookupByAnnotationTypeInRealTime(beanFactory);
    }

    private static void lookupByAnnotationTypeInRealTime(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> users = (Map)listableBeanFactory.getBeansWithAnnotation(Super.class);
            System.out.println("查找到所有标注了 @Super 的 User 集合对象为：" + users);
        }
    }

    private static void lookupByNameAndTypeInLazy(BeanFactory beanFactory) {
        // TODO 根据名称 + 类型延时查找
    }

    private static void lookupByNameAndTypeInRealTime(BeanFactory beanFactory) {
        // TODO 根据名称 + 类型实时查找
    }

    /**
     * 根据类型查找bean的集合
     * 可能需要使用 {@link ListableBeanFactory}
     *
     * @param beanFactory
     */
    private static void lookupCollectionByTypeInRealTime(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> users = listableBeanFactory.getBeansOfType(User.class);
            System.out.println("查找到所有的 User 集合对象为：" + users);
        }
    }

    /**
     * 通过类型实时查找单个Bean
     *
     * @param beanFactory
     */
    private static void lookupByTypeInRealTime(BeanFactory beanFactory) {
        User user = beanFactory.getBean(User.class);
        System.out.println("通过类型实时查找：" + user);
    }

    /**
     * 根据Bean名称延时查找
     * <p>
     * ObjectFactory 不会生成新的Bean
     *
     * @param beanFactory
     */
    private static void lookupByNameInLazy(BeanFactory beanFactory) {
        ObjectFactory<User> objectFactory = (ObjectFactory<User>) beanFactory.getBean("objectFactory");
        User user = objectFactory.getObject();
        System.out.println("延迟查找：" + user);
    }

    /**
     * 根据Bean名称实时查找
     *
     * @param beanFactory
     */
    private static void lookupByNameInRealTime(BeanFactory beanFactory) {
        User user = (User) beanFactory.getBean("user");
        System.out.println("实时查找：" + user);
    }
}
