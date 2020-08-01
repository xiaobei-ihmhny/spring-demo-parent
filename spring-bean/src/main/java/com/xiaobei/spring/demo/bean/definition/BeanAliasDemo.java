package com.xiaobei.spring.demo.bean.definition;

import com.xiaobei.spring.demo.ioc.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/1 13:17
 */
public class BeanAliasDemo {

    public static void main(String[] args) {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/bean-definitions-context.xml");
        // 通过别名xiaobei-user的方式获取曾用名user的bean
        User xiaobeiUser = beanFactory.getBean("xiaobei-user", User.class);
        User user = beanFactory.getBean("user", User.class);
        System.out.println("xiaobeiUser与user是否相等：" + (user == xiaobeiUser));
    }
}
