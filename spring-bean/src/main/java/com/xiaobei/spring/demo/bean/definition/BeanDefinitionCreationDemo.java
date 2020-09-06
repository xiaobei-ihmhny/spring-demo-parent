package com.xiaobei.spring.demo.bean.definition;

import com.xiaobei.spring.demo.ioc.overview.domain.User;
import org.junit.Test;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.GenericBeanDefinition;

/**
 * {@link org.springframework.beans.factory.config.BeanDefinition} 构建示例
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/7/31 22:50
 */
public class BeanDefinitionCreationDemo {

    /**
     * 通过 {@link AbstractBeanDefinition AbstractBeanDefinition}
     * 的派生类 {@link GenericBeanDefinition GenericBeanDefinition}
     * 来构建 {@link BeanDefinition BeanDefinition}
     *
     */
    @Test
    public void createBeanDefinitionByAbstractBeanDefinition() {
        // 2. 通过AbstractBeanDefinition的派生类 {@link org.springframework.beans.factory.support.GenericBeanDefinition}
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        // 设置Bean的类型
        beanDefinition.setBeanClass(User.class);
        // 通过{@code propertyValues}来批量操作属性
        MutablePropertyValues propertyValues = new MutablePropertyValues();
        propertyValues.add("id", 1)
                .add("name", "xiaobei");
        // 将批量操作的结果设置到BeanDefinition的属性中
        beanDefinition.setPropertyValues(propertyValues);
        System.out.println(beanDefinition);
    }

    /**
     * 通过 {@link BeanDefinitionBuilder BeanDefinitionBuilder} 来创建 {@link BeanDefinition BeanDefinition}
     */
    @Test
    public void createBeanDefinitionByBeanDefinitionBuilder() {
        // 1. 使用BeanDefinitionBuilder进行bean元信息配置
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        // 通过属性设置
        builder.addPropertyValue("id", 1)
                .addPropertyValue("name", "xiaohui");
        // 获取BeanDefinition实例
        BeanDefinition builderBeanDefinition = builder.getBeanDefinition();
        // 其中的BeanDefinition并非终态，可以自定义修改
        System.out.println(builderBeanDefinition);
    }
}
