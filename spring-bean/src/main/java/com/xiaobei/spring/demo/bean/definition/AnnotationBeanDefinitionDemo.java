package com.xiaobei.spring.demo.bean.definition;

import com.xiaobei.spring.demo.bean.definition.register.BeanAnnotation;
import com.xiaobei.spring.demo.bean.definition.register.ComponentAnnotation;
import com.xiaobei.spring.demo.bean.definition.register.ImportAnnotation;
import com.xiaobei.spring.demo.ioc.overview.domain.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Map;

/**
 * Java注解配置元信息示例
 *
 *
 * Spring中不会重复注册！！
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/1 15:47
 */
public class AnnotationBeanDefinitionDemo {

    public static void main(String[] args) {
        // 创建BeanFactory容器
        AnnotationConfigApplicationContext beanFactory = new AnnotationConfigApplicationContext();
//        registerByBeanAnnotation(beanFactory);
//        registerByComponentAnnotation(beanFactory);
        registerByImportAnnotation(beanFactory);
    }

    /**
     * 通过{@link org.springframework.context.annotation.Import} 注解来注册Spring BeanDefinition示例
     * 运行结果：
     * 容器中BeanAnnotation类型的所有Bean为：{com.xiaobei.spring.demo.bean.definition.register.BeanAnnotation=com.xiaobei.spring.demo.bean.definition.register.BeanAnnotation@5bb21b69}
     * 容器中User类型的所有Bean为：{user=User{id=35, name='35 注册Spring Bean：如何将BeanDefinition注册到IoC容器？'}}
     * 容器中ComponentAnnotation类型的所有Bean为：{com.xiaobei.spring.demo.bean.definition.register.ComponentAnnotation=ComponentAnnotation{user=null}}
     * 容器中ImportAnnotation类型的所有Bean为：{importAnnotation=ImportAnnotation{componentAnnotation=null}}
     * @param beanFactory
     */
    private static void registerByImportAnnotation(AnnotationConfigApplicationContext beanFactory) {
        beanFactory.register(ImportAnnotation.class);
        // 启动应用上下文
        getAllBeans(beanFactory);
    }

    /**
     * 通过{@link org.springframework.stereotype.Component} 注解来注册Spring BeanDefinition 示例
     * 运行结果：
     * 容器中BeanAnnotation类型的所有Bean为：{}
     * 容器中User类型的所有Bean为：{}
     * 容器中ComponentAnnotation类型的所有Bean为：{componentAnnotation=ComponentAnnotation{user=null}}
     * 容器中ImportAnnotation类型的所有Bean为：{}
     *
     * @param beanFactory
     */
    private static void registerByComponentAnnotation(AnnotationConfigApplicationContext beanFactory) {
        beanFactory.scan("com.xiaobei.spring.demo.bean.definition.register");
        getAllBeans(beanFactory);
    }


    /**
     * 通过 {@link Bean} 的方式注册spring BeanDefinition 元信息
     *
     * 运行结果为：
     * 容器中BeanAnnotation类型的所有Bean为：{beanAnnotation=com.xiaobei.spring.demo.bean.definition.register.BeanAnnotation@42f93a98}
     * 容器中User类型的所有Bean为：{user=User{id=35, name='35 注册Spring Bean：如何将BeanDefinition注册到IoC容器？'}}
     * 容器中ComponentAnnotation类型的所有Bean为：{}
     * 容器中ImportAnnotation类型的所有Bean为：{}
     *
     * @param beanFactory
     */
    private static void registerByBeanAnnotation(AnnotationConfigApplicationContext beanFactory) {
        beanFactory.register(BeanAnnotation.class);
        // 启动应用上下文
        getAllBeans(beanFactory);
    }

    private static void getAllBeans(AnnotationConfigApplicationContext beanFactory) {
        beanFactory.refresh();
        // 按类型进行依赖查找
        Map<String, BeanAnnotation> beanAnnotationMap
                = beanFactory.getBeansOfType(BeanAnnotation.class);
        Map<String, User> userMap = beanFactory.getBeansOfType(User.class);
        Map<String, ComponentAnnotation> componentMap = beanFactory.getBeansOfType(ComponentAnnotation.class);
        Map<String, ImportAnnotation> importMap = beanFactory.getBeansOfType(ImportAnnotation.class);
        System.out.println("容器中BeanAnnotation类型的所有Bean为：" + beanAnnotationMap);
        System.out.println("容器中User类型的所有Bean为：" + userMap);
        System.out.println("容器中ComponentAnnotation类型的所有Bean为：" + componentMap);
        System.out.println("容器中ImportAnnotation类型的所有Bean为：" + importMap);
        // 显式的关闭Spring应用上下文
        beanFactory.close();
    }
}
