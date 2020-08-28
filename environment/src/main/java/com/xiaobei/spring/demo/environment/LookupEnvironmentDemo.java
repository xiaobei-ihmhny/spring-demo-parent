package com.xiaobei.spring.demo.environment;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

/**
 * 依赖查找 {@link Environment Environment}
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/28 20:26
 */
public class LookupEnvironmentDemo implements EnvironmentAware {

    private Environment environment;

    /**
     * <h2>运行结果：</h2>
     * StandardEnvironment {activeProfiles=[], defaultProfiles=[default], propertySources=[PropertiesPropertySource {name='systemProperties'}, SystemEnvironmentPropertySource {name='systemEnvironment'}]}
     * true
     * true
     *
     * 运行结果表明：
     * 依赖查找 和 依赖注入 两种方式获取到的 {@link Environment Environment} 是同一个对象
     * @param args
     *
     * @see org.springframework.context.support.AbstractApplicationContext
     * @see AbstractApplicationContext#refresh()
     *      @see AbstractApplicationContext#prepareBeanFactory(ConfigurableListableBeanFactory)  BeanFactory的准备前阶段
     *      {@code beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));} <br>
     *              @see org.springframework.context.support.ApplicationContextAwareProcessor#invokeAwareInterfaces(java.lang.Object) 配置相关的 Aware 接口
     *      {@code if (!beanFactory.containsLocalBean(ENVIRONMENT_BEAN_NAME))} 判断当前应用上下文中是否存在名称为
     *      {@link ConfigurableApplicationContext#ENVIRONMENT_BEAN_NAME environment}的 Bean，不存在时，
     *      就注册一个{@link Environment environment} 为单例Bean <br>
     */
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册当前应用为 Configuration Class
        applicationContext.register(LookupEnvironmentDemo.class);
        // 启动 Spring 应用上下文
        applicationContext.refresh();
        // 通过名称 + 类型 的方式来依赖查找 {@code Environment}
        Environment environment =
                applicationContext.getBean(ConfigurableApplicationContext.ENVIRONMENT_BEAN_NAME, Environment.class);
        ConfigurableEnvironment environment2 = applicationContext.getEnvironment();
        LookupEnvironmentDemo demo = applicationContext.getBean(LookupEnvironmentDemo.class);
        // 对接两种方式查到的 {@code Environment} 是否是同一个对象
        System.out.println(environment);
        System.out.println(environment == environment2);
        System.out.println(environment == demo.environment);
        // 关闭 Spring 应用上下文
        applicationContext.close();
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
