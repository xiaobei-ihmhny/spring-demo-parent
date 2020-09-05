package com.xiaobei.spring.demo.ioc.overview.dependency.injection;

import com.xiaobei.spring.demo.ioc.overview.repository.LazyDomain;
import org.junit.Test;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.env.Environment;

import java.util.Map;

/**
 *
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/7/29 7:09
 */
@SuppressWarnings("DuplicatedCode")
public class DependencyInjectionByAnnotationDemo {

    /**
     * 注入容器内建的Bean对象
     * <h2>运行结果：</h2>
     * BuildInBeanConfig{
     *   environment=StandardEnvironment {activeProfiles=[], defaultProfiles=[default], propertySources=[PropertiesPropertySource {name='systemProperties'}, SystemEnvironmentPropertySource {name='systemEnvironment'}]},
     *   systemProperties={systemProperties={java.runtime.name=Java(TM) SE Runtime Environment, ...}},
     *   systemEnvironment={systemEnvironment={USERDOMAIN_ROAMINGPROFILE=DESKTOP-2NUB409, ...}},
     *   messageSource=Empty MessageSource,
     *   applicationEventMulticaster=org.springframework.context.event.SimpleApplicationEventMulticaster@37918c79
     * }
     *
     * 在 {@link AbstractApplicationContext} 中添加的单例Bean（内建Bean对象）
     */
    @Test
    public void dependencyInjectionBuildInBean() {
        AnnotationConfigApplicationContext beanFactory = new AnnotationConfigApplicationContext();
        beanFactory.register(BuildInBeanConfig.class);
        // 启动spring应用上下文
        beanFactory.refresh();
        BuildInBeanConfig buildInBeanConfig = beanFactory.getBean(BuildInBeanConfig.class);
        System.out.println(buildInBeanConfig);
        beanFactory.close();
    }

    @Configuration
    static class BuildInBeanConfig {

        @Autowired
        @Qualifier(ConfigurableApplicationContext.ENVIRONMENT_BEAN_NAME)
        public Environment environment;

        @Autowired
        @Qualifier(ConfigurableApplicationContext.SYSTEM_PROPERTIES_BEAN_NAME)
        public Map<String, Object> systemProperties;

        @Autowired
        @Qualifier(ConfigurableApplicationContext.SYSTEM_ENVIRONMENT_BEAN_NAME)
        public Map<String, Object> systemEnvironment;

        @Autowired
        @Qualifier(AbstractApplicationContext.MESSAGE_SOURCE_BEAN_NAME)
        public MessageSource messageSource;

        @Autowired
        @Qualifier(AbstractApplicationContext.APPLICATION_EVENT_MULTICASTER_BEAN_NAME)
        public ApplicationEventMulticaster applicationEventMulticaster;

        @Override
        public String toString() {
            return "BuildInBeanConfig{" +
                    " \n  environment=" + environment +
                    ", \n  systemProperties=" + systemProperties +
                    ", \n  systemEnvironment=" + systemEnvironment +
                    ", \n  messageSource=" + messageSource +
                    ", \n  applicationEventMulticaster=" + applicationEventMulticaster +
                    "\n}\n";
        }
    }

    /**
     * {@link ObjectFactory} 与 {@link ObjectProvider} 提供的是延迟加载的特性
     *
     * <h2>运行结果：</h2>
     * org.springframework.beans.factory.support.DefaultListableBeanFactory$DependencyObjectProvider@3e77a1ed
     * org.springframework.beans.factory.support.DefaultListableBeanFactory$DependencyObjectProvider@3ffcd140
     * 初始化 LazyDomain 对象...
     * LazyDomain{id=20200905, name='ObjectFactory提供的是延迟依赖查找'}
     * LazyDomain{id=20200905, name='ObjectFactory提供的是延迟依赖查找'}
     * 销毁 LazyDomain 对象...
     */
    @Test
    public void dependencyInjectionInLazy() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册指定的类为 Configuration Class
        applicationContext.register(LazyDomainConfig.class);
        // 启动 Spring 应用上下文
        applicationContext.refresh();
        LazyDomainConfig lazyDomainConfig = applicationContext.getBean(LazyDomainConfig.class);
        ObjectFactory<LazyDomain> objectFactory = lazyDomainConfig.objectFactory;
        ObjectProvider<LazyDomain> objectProvider = lazyDomainConfig.objectProvider;
        System.out.println(objectFactory);
        System.out.println(objectProvider);
        System.out.println(objectFactory.getObject());
        System.out.println(objectProvider.getObject());
        // 关闭 Spring 应用上下文
        applicationContext.close();
    }

    static class LazyDomainConfig {

        @Autowired
        private ObjectFactory<LazyDomain> objectFactory;

        @Autowired
        private ObjectProvider<LazyDomain> objectProvider;

        @Bean
        @Lazy
        public static LazyDomain domain() {
            LazyDomain domain = new LazyDomain();
            domain.setId(20200905);
            domain.setName("ObjectFactory提供的是延迟依赖查找");
            return domain;
        }

    }

}
