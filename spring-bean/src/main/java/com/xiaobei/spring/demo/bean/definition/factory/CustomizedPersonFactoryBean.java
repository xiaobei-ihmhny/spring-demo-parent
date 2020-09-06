package com.xiaobei.spring.demo.bean.definition.factory;

import com.xiaobei.spring.demo.bean.definition.domain.Person;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/1 22:04
 */
public class CustomizedPersonFactoryBean implements FactoryBean<Person> {
    @Override
    public Person getObject() throws Exception {
        Person person = new Person();
        person.setAge(96);
        person.setName("通过api方式利用spi加载相关bean");
        return person;
    }

    @Override
    public Class<?> getObjectType() {
        return Person.class;
    }
}
