package com.xiaobei.spring.demo.conversion;

import com.xiaobei.spring.demo.conversion.domain.ConditionalGenericConverterDomain;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;
import org.springframework.core.convert.converter.GenericConverter;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/22 17:03
 */
public class CustomizedConditionalGenericConverterDemo {

    /**
     * <h2>运行结果</h2>
     * ConditionalGenericConverterDomain{id=170, name='扩展Spring类型转换器', properties={id=1,name=xiaobei},
     * propertiesAsText='#Sat Aug 22 17:32:09 CST 2020
     * name=Spring\u7C7B\u578B\u8F6C\u6362\u5668
     * id=170
     * '}
     * @param args
     *
     * @see org.springframework.beans.TypeConverterDelegate#convertIfNecessary(java.lang.String, java.lang.Object, java.lang.Object, java.lang.Class, TypeDescriptor)
     * @see AbstractApplicationContext#refresh()
     * {@code finishBeanFactoryInitialization(beanFactory);}
     * @see AbstractApplicationContext#finishBeanFactoryInitialization(ConfigurableListableBeanFactory)
     * @see ConfigurableApplicationContext#CONVERSION_SERVICE_BEAN_NAME
     * @see PropertiesToStringConverter
     * @see ConditionalGenericConverter
     * @see GenericConverter.ConvertiblePair
     * @see ConversionServiceFactoryBean
     *
     *
     */
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("classpath:META-INF/conditional-generic-converter-context.xml");
        ConditionalGenericConverterDomain propertyDomain =
                applicationContext.getBean("conditionalGenericConverterDomain", ConditionalGenericConverterDomain.class);
        System.out.println(propertyDomain);
        applicationContext.close();
    }
}
