package com.xiaobei.spring.demo.dependency.domain;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/4 21:08
 */
public class UserHolder {

    private User user;

    public UserHolder() {
    }

    public UserHolder(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public UserHolder setUser(User user) {
        this.user = user;
        return this;
    }

    @Override
    public String toString() {
        return "UserHolder{" +
                "user=" + user +
                '}';
    }
}
