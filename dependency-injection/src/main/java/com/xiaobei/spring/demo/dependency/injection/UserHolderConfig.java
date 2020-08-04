package com.xiaobei.spring.demo.dependency.injection;

import com.xiaobei.spring.demo.dependency.domain.SuperUser;
import com.xiaobei.spring.demo.dependency.domain.User;
import com.xiaobei.spring.demo.dependency.domain.UserHolder;
import org.springframework.context.annotation.Bean;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/4 22:02
 */
public class UserHolderConfig {

    /**
     * setter方法注入
     * @param user
     * @return
     */
    @Bean
    public UserHolder userHolder(User user) {
        UserHolder holder = new UserHolder();
        holder.setUser(user);
        return holder;
    }

    @Bean
    public User user() {
        User user = new User();
        user.setAge(55);
        user.setName("55 Setter方法依赖注入：Setter注入的原理是什么？");
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
