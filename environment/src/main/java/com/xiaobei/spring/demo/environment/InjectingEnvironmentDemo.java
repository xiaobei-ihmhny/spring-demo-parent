package com.xiaobei.spring.demo.environment;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/27 22:37
 */
public class InjectingEnvironmentDemo {

    /**
     * <h2>运行结果：</h2>
     * true
     * true
     * true
     * true
     * true
     * 说明：这些个对象都是同一个
     * @param args
     */
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        applicationContext.register(EnvironmentConfig.class);

        // 启动 Spring 应用上下文
        applicationContext.refresh();

        // 依赖查找 {@link Environment} 对象

        EnvironmentConfig environmentConfig = applicationContext.getBean(EnvironmentConfig.class);

        System.out.println(environmentConfig.environment == environmentConfig.environment2);

        System.out.println(environmentConfig.applicationContext == environmentConfig.applicationContext2);

        System.out.println(environmentConfig.environment == environmentConfig.applicationContext.getEnvironment());

        System.out.println(environmentConfig.environment == applicationContext.getEnvironment());

        System.out.println(environmentConfig.applicationContext == applicationContext);

        // 关闭 Spring 应用上下文
        applicationContext.close();
    }

    static class EnvironmentConfig implements EnvironmentAware, ApplicationContextAware {

        private Environment environment;

        @Autowired
        private Environment environment2;

        private ApplicationContext applicationContext;

        @Autowired
        private ApplicationContext applicationContext2;

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            this.applicationContext = applicationContext;
        }

        @Override
        public void setEnvironment(Environment environment) {
            this.environment = environment;
        }
    }
}
