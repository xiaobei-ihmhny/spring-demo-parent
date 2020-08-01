package com.xiaobei.spring.demo.bean.definition.register;

import com.xiaobei.spring.demo.ioc.overview.domain.User;
import org.springframework.stereotype.Component;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/1 16:04
 */
@Component
public class ComponentAnnotation {

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ComponentAnnotation{" +
                "user=" + user +
                '}';
    }
}
