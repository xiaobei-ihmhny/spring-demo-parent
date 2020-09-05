package com.xiaobei.spring.demo.ioc.overview.repository;

import com.xiaobei.spring.demo.ioc.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;

import java.util.Collection;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-07-30 12:53:53
 */
public class UserRepository {

    private User user;

    /**
     * 自定义Bean
     */
    private Collection<User> users;

    /**
     * 内建的非Bean对象（依赖）
     */
    private BeanFactory beanFactory;

    private ObjectFactory<User> userObjectFactory;

    private ApplicationContext applicationContext;

    private ObjectFactory<ApplicationContext> objectFactory;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public ObjectFactory<User> getUserObjectFactory() {
        return userObjectFactory;
    }

    public void setUserObjectFactory(ObjectFactory<User> userObjectFactory) {
        this.userObjectFactory = userObjectFactory;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public ObjectFactory<ApplicationContext> getObjectFactory() {
        return objectFactory;
    }

    public void setObjectFactory(ObjectFactory<ApplicationContext> objectFactory) {
        this.objectFactory = objectFactory;
    }

    @Override
    public String toString() {
        return "UserRepository{" +
                "user=" + user +
                ", \n  users=" + users +
                ", \n  beanFactory=" + beanFactory +
                ", \n  userObjectFactory=" + userObjectFactory +
                ", \n  applicationContext=" + applicationContext +
                ", \n  objectFactory=" + objectFactory +
                ", \n  objectFactory.getObject=" + objectFactory.getObject() +
                "\n}\n";
    }
}