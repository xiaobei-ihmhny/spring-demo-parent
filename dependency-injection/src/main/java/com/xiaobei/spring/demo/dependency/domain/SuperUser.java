package com.xiaobei.spring.demo.dependency.domain;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/4 21:07
 */
public class SuperUser extends User {

    private String address;

    public SuperUser() {
    }

    public SuperUser(String address) {
        this.address = address;
    }

    public SuperUser(Integer age, String name, String address) {
        super(age, name);
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public SuperUser setAddress(String address) {
        this.address = address;
        return this;
    }

    @Override
    public String toString() {
        return "SuperUser{" +
                "address='" + address + '\'' +
                "} " + super.toString();
    }
}
