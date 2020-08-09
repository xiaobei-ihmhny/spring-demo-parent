package com.xiaobei.spring.demo.bean.lifecycle.domain;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/9 19:46
 */
public class SuperLifeCycleDomain extends LifeCycleDomain {

    private String child;

    public String getChild() {
        return child;
    }

    public SuperLifeCycleDomain setChild(String child) {
        this.child = child;
        return this;
    }

    @Override
    public String toString() {
        return "SuperLifeCycleDomain{" +
                "child='" + child + '\'' +
                "} " + super.toString();
    }
}
