package com.xiaobei.spring.demo.bean.definition.factory;

import com.xiaobei.spring.demo.bean.definition.domain.Person;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/1 21:55
 */
public interface PersonFactory {

    default Person getInstance() {
        Person person = new Person();
        person.setAge(36);
        person.setName("36 实例化Spring Bean：通过Bean工厂(实例工厂)方法，XML文件");
        return person;
    }
}
