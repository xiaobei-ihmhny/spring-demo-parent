package com.xiaobei.spring.demo.bean.lifecycle.domain;

import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/9 15:17
 */
public class LifeCycleDomain implements InitializingBean {

    private Long id;

    private String name;

    private City city;

    public Long getId() {
        return id;
    }

    public LifeCycleDomain setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public LifeCycleDomain setName(String name) {
        this.name = name;
        return this;
    }

    public City getCity() {
        return city;
    }

    public LifeCycleDomain setCity(City city) {
        this.city = city;
        return this;
    }

    @Override
    public String toString() {
        return "LifeCycleDomain{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city=" + city +
                '}';
    }

    /**
     * 该方法最先执行，前提是当前的BeanFactory得有注解驱动能力！！
     * @see org.springframework.beans.factory.BeanFactory
     * @see org.springframework.context.annotation.CommonAnnotationBeanPostProcessor
     */
    @PostConstruct
    public void postConstruct() {
        this.name = "xiaobei-ihmhny";
        System.out.println("postConstruct() -> " + this);
    }

    /**
     * 该方法在 {@link PostConstruct}之后执行
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        this.name = "xiaobei";
        System.out.println("afterPropertiesSet() -> " + this);
    }

    /**
     * 该方法在{@link InitializingBean#afterPropertiesSet()}之后。
     * 该方法最后执行
     */
    public void init() {
        this.name = "xiaobei_ihmhny";
        System.out.println("postConstruct() -> " + this);
    }
}
