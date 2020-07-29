package com.xiaobei.spring.demo.ioc.overview.dependency.lookup;

import com.xiaobei.spring.demo.ioc.overview.dependency.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/7/29 7:09
 */
public class DependencyLookupDemo {

    public static void main(String[] args) {
        // 配置xml配置文件
        // 启动spring应用上下文
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-lookup.xml");
        User user = (User)beanFactory.getBean("user");
        System.out.println(user);

    }
}
