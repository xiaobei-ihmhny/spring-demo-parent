package com.xiaobei.spring.demo.bean.definition.register;

import com.xiaobei.spring.demo.ioc.overview.domain.User;
import org.springframework.context.annotation.Bean;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/1 15:56
 */
public class BeanAnnotation {

    @Bean
    public User user () {
        User user = new User();
        user.setId(35L);
        user.setName("35 注册Spring Bean：如何将BeanDefinition注册到IoC容器？");
        return user;
    }
}
