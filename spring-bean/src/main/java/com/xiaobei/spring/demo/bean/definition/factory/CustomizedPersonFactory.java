package com.xiaobei.spring.demo.bean.definition.factory;

import com.xiaobei.spring.demo.bean.definition.domain.Person;

/**
 * 自定义的 PersonFactory实现
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/9/6 11:27
 */
public class CustomizedPersonFactory implements PersonFactory {

    @Override
    public Person getInstance() {
        Person customizedPerson = new Person();
        customizedPerson.setAge(96);
        customizedPerson.setName("xiaoxiao");
        return customizedPerson;
    }
}
