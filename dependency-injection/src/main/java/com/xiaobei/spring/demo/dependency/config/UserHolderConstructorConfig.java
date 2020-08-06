package com.xiaobei.spring.demo.dependency.config;

import com.xiaobei.spring.demo.dependency.domain.SuperUser;
import com.xiaobei.spring.demo.dependency.domain.User;
import com.xiaobei.spring.demo.dependency.domain.UserHolder;
import org.springframework.context.annotation.Bean;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/4 22:02
 */
public class UserHolderConstructorConfig {

    /**
     * 构造器方法注入
     * @param user
     * @return
     */
    @Bean
    public UserHolder userHolder(User user) {
        return new UserHolder(user);
    }

    @Bean
    public User user() {
        User user = new User();
        user.setAge(56);
        user.setName("56 构造器依赖注入：官方为什么推荐使用构造器注入？");
        return user;
    }

    /**
     * TODO 注解貌似无法指定 parent？？？
     * @return
     */
    @Bean
    public SuperUser superUser() {
        SuperUser superUser = new SuperUser();
        superUser.setAddress("北京");
        return superUser;
    }
}
