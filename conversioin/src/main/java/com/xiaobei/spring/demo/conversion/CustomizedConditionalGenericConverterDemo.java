package com.xiaobei.spring.demo.conversion;

import com.xiaobei.spring.demo.conversion.domain.ConditionalGenericConverterDomain;
import org.springframework.beans.AbstractNestablePropertyAccessor;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.PropertyEditorRegistrySupport;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;
import org.springframework.core.convert.converter.GenericConverter;

/**
 * 自定义类型转换器加载及调用链路
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
     *
     * <h2>自定义类型转换器加载流程</h2>
     *
     * <h3>spring应用上下文启动时加载自定义类型转换器</h3>
     * {@link AbstractApplicationContext#refresh()} 其中的 {@code finishBeanFactoryInitialization(beanFactory);}
     * -> {@link AbstractApplicationContext#finishBeanFactoryInitialization(ConfigurableListableBeanFactory)}
     * -> 当存在名称为 {@link ConfigurableApplicationContext#CONVERSION_SERVICE_BEAN_NAME} 且类型为 {@link ConversionService} 的 Bean时，
     * 就将相应的Bean通过方法 {@link AbstractBeanFactory#setConversionService(ConversionService)} 设置到 {@link AbstractBeanFactory#conversionService} 变量中
     *
     * <h3>当通过依赖查找或依赖注入获取相应的Bean时</h3>
     * {@link AbstractApplicationContext#getBean(java.lang.String, java.lang.Class<T>)}
     * {@link AbstractBeanFactory#getBean(java.lang.String, java.lang.Class<T>)}
     * {@link AbstractBeanFactory#doGetBean}
     * {@link AbstractAutowireCapableBeanFactory#createBean(java.lang.String, RootBeanDefinition, java.lang.Object[])}
     * {@link AbstractAutowireCapableBeanFactory#doCreateBean}
     *        {@code instanceWrapper = createBeanInstance(beanName, mbd, args);}
     *    {@link AbstractAutowireCapableBeanFactory#createBeanInstance}
     *    {@link AbstractAutowireCapableBeanFactory#instantiateBean}
     *        {@code initBeanWrapper(bw);}
     *        {@code bw.setConversionService(getConversionService());}
     *        {@link PropertyEditorRegistrySupport#setConversionService}
     * 		将应用上下文启动时保存到 {@code AbstractBeanFactory#conversionService} 中的类型转换器取出，并设置到当前的 {@link BeanWrapperImpl}对象中
     *    {@code populateBean(beanName, mbd, instanceWrapper);}
     *    {@link AbstractAutowireCapableBeanFactory#populateBean}
     *        {@code applyPropertyValues(beanName, mbd, bw, pvs);}
     *        {@link AbstractAutowireCapableBeanFactory#applyPropertyValues}
     * 			convertedValue = convertForProperty(resolvedValue, propertyName, bw, converter);
     *            {@link AbstractAutowireCapableBeanFactory#convertForProperty}
     *            {@code return ((BeanWrapperImpl) converter).convertForProperty(value, propertyName);}
     *            {@link BeanWrapperImpl#convertForProperty}
     *            {@link AbstractNestablePropertyAccessor#convertForProperty}
     *            {@link AbstractNestablePropertyAccessor#convertIfNecessary}
     *            {@link org.springframework.beans.TypeConverterDelegate#convertIfNecessary(java.lang.String, java.lang.Object, java.lang.Object, java.lang.Class<T>, .TypeDescriptor)}
     *
     *
     * AbstractApplicationContext -> name="conversionService"且类型为ConversionService的Bean
     * -> ConfigurableBeanFactory#setConversionService(ConversionService)
     * -> AbstractAutowireCapableBeanFactory#doCreateBean
     * -> AbstractAutowireCapableBeanFactory#initializeBean(java.lang.String, java.lang.Object, RootBeanDefinition)
     * -> BeanFactory -> AbstractBeanFactory#getConversionService
     * -> BeanDefinition -> BeanWrapper -> 属性转换（数据来源：PropertyValues）
     * -> setPropertyValues(PropertyValues) -> TypeConverter#convertIfNecessary
     * -> TypeConverterDelegate#convertIfNecessary -> PropertyEditor or ConversionService
     *
     *
     *
     *
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
