package com.xiaobei.spring.demo.i18n;

import org.junit.Test;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/17 22:09
 * @see org.springframework.context.support.ResourceBundleMessageSource
 * @see org.springframework.context.support.ReloadableResourceBundleMessageSource
 * @see org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration
 */
@SuppressWarnings("DuplicatedCode")
@EnableAutoConfiguration
public class CustomizedMessageSourceDemo {

    /**
     * 使用 spring boot 默认的 {@link MessageSource} 实现 {@link ResourceBundleMessageSource}
     *
     * <h2>运行结果：</h2>
     *
     * org.springframework.context.support.ResourceBundleMessageSource: basenames=[messages]
     * Root bean: class [null]; scope=; abstract=false; lazyInit=null; autowireMode=3; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration; factoryMethodName=messageSource; initMethodName=null; destroyMethodName=(inferred); defined in class path resource [org/springframework/boot/autoconfigure/context/MessageSourceAutoConfiguration.class]
     *
     */
    @Test
    public void userSpringBootDefaultMessageSource() {
        ConfigurableApplicationContext applicationContext =
                new SpringApplicationBuilder(CustomizedMessageSourceDemo.class)
                        .web(WebApplicationType.NONE)
                        .run();
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        if (applicationContext.containsLocalBean(AbstractApplicationContext.MESSAGE_SOURCE_BEAN_NAME)) {
            // 说明当前应用上下文中存在类型为 {@link MessageSource} 且名称为 {@code messageSource}的bean
            MessageSource messageSource =
                    beanFactory.getBean(AbstractApplicationContext.MESSAGE_SOURCE_BEAN_NAME, MessageSource.class);
            System.out.println(messageSource);
            System.out.println(beanFactory.getBeanDefinition(AbstractApplicationContext.MESSAGE_SOURCE_BEAN_NAME));
        }
        // 关闭应用上下文
        applicationContext.close();
    }

    /**
     * 使用 {@link MessageSource} 实现 {@link ReloadableResourceBundleMessageSource} 来替换 spring boot 默认实现
     *
     * <h2>运行结果：</h2>
     *
     * org.springframework.context.support.ResourceBundleMessageSource: basenames=[messages]
     * Root bean: class [null]; scope=; abstract=false; lazyInit=null; autowireMode=3; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration; factoryMethodName=messageSource; initMethodName=null; destroyMethodName=(inferred); defined in class path resource [org/springframework/boot/autoconfigure/context/MessageSourceAutoConfiguration.class]
     *
     */
    @Test
    public void useReloadableResourceBundleMessageSource() {
        ConfigurableApplicationContext applicationContext =
                new SpringApplicationBuilder(CustomizedMessageSourceDemo.class)
                        .web(WebApplicationType.NONE)
                        // 添加 配置类 {@link ReloadableResourceBundleMessageSourceConfig 用于加载自定义配置Bean}
                        .sources(ReloadableResourceBundleMessageSourceConfig.class)
                        .run();
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        if (applicationContext.containsLocalBean(AbstractApplicationContext.MESSAGE_SOURCE_BEAN_NAME)) {
            // 说明当前应用上下文中存在类型为 {@link MessageSource} 且名称为 {@code messageSource}的bean
            MessageSource messageSource =
                    beanFactory.getBean(AbstractApplicationContext.MESSAGE_SOURCE_BEAN_NAME, MessageSource.class);
            System.out.println(messageSource);
            System.out.println(beanFactory.getBeanDefinition(AbstractApplicationContext.MESSAGE_SOURCE_BEAN_NAME));
        }
        // 关闭应用上下文
        applicationContext.close();
    }

    /**
     * 自定义配置类来替换 spring boot 默认的 {@link MessageSource}
     */
    static class ReloadableResourceBundleMessageSourceConfig {

        /**
         * 创建一个名称为 {@link AbstractApplicationContext#MESSAGE_SOURCE_BEAN_NAME}
         * 且类型为 {@link MessageSource} 的bean
         * @return
         */
        @Bean(AbstractApplicationContext.MESSAGE_SOURCE_BEAN_NAME)
        private MessageSource messageSource() {
            return new ReloadableResourceBundleMessageSource();
        }
    }

}
