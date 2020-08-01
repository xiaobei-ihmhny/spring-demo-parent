package com.xiaobei.spring.demo.bean.definition.factory;

import com.xiaobei.spring.demo.bean.definition.domain.Person;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/1 22:04
 */
public class PersonFactoryBean implements FactoryBean<Person> {
    @Override
    public Person getObject() throws Exception {
        Person person = new Person();
        person.setAge(36);
        person.setName("36 实例化Spring Bean：通过FactoryBean，XML文件");
        return person;
    }

    @Override
    public Class<?> getObjectType() {
        return Person.class;
    }
}
