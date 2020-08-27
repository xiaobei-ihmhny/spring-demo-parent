package com.xiaobei.spring.demo.annotation.conditional;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.function.Consumer;

/**
 * {@link Conditional} 与 {@link Profile}
 *
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-08-27 17:16:16
 * @see Conditional
 * @see Profile
 */
public class ConditionalDemo {

    /**
     * 通过 {@link ConfigurableEnvironment#setDefaultProfiles(String...)} 配置默认的 {@code profiles}
     *
     * <h2>运行结果：</h2>
     * 1
     * 1
     *
     * <h2>说明</h2>
     * 此外的 {@link ConfigurableEnvironment#setDefaultProfiles(String...)} 可以通过外部化配置的方式指定
     * 比如：指定：{@code -Dspring.profiles.default=odd}
     *
     * @see AbstractEnvironment#doGetDefaultProfiles()
     * @see AbstractEnvironment#DEFAULT_PROFILES_PROPERTY_NAME
     * @see ConfigurableEnvironment
     */
    @Test
    public void profileWithDefaultProfiles() {
        testConditional(environment -> environment.setDefaultProfiles("odd"), ProfileConfig.class);
        // 自定义 {@code Conditional} 实现
        testConditional(environment -> environment.setDefaultProfiles("odd"), ConditionConfig.class);
    }

    /**
     * 通过 {@link ConfigurableEnvironment#setDefaultProfiles(String...)} 配置默认的 {@code profiles}
     * 并通过 {@link ConfigurableEnvironment#addActiveProfile(String)} 配置激活的 {@code profiles}
     * 其中的 {@code defaultProfiles} 是一个“兜底”，当{@code activeProfile} 不存在时会使用
     * <h2>运行结果：</h2>
     * 2
     * 2
     *
     * <h2>说明</h2>
     * 此外的 {@link ConfigurableEnvironment#setDefaultProfiles(String...)} 可以通过外部化配置的方式指定
     * 比如：指定：{@code -Dspring.profiles.active=odd}
     * @see org.springframework.core.env.AbstractEnvironment#doGetActiveProfiles()
     * @see AbstractEnvironment#ACTIVE_PROFILES_PROPERTY_NAME
     * @see ConfigurableEnvironment
     */
    @Test
    public void profileWithActiveProfiles() {
        testConditional(environment -> {
            environment.setDefaultProfiles("odd");
            environment.addActiveProfile("even");
        }, ProfileConfig.class);
        // 自定义 {@code Conditional} 实现
        testConditional(environment -> {
            environment.setDefaultProfiles("odd");
            environment.addActiveProfile("even");
        }, ConditionConfig.class);
    }

    /**
     * 测试 {@link Conditional} 使用
     *
     * @param environmentConsumer 对 {@link ConfigurableEnvironment} 进行相应配置
     */
    private void testConditional(Consumer<ConfigurableEnvironment> environmentConsumer, Class<?> clazz) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(clazz);
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        // 对当前的 {@code environment} 进行处理
        environmentConsumer.accept(environment);
        // 启动 Spring 应用上下文
        applicationContext.refresh();
        Integer num = applicationContext.getBean("num", Integer.class);
        System.out.println(num);
        // 关闭 Spring 应用上下文
        applicationContext.close();
    }

    /**
     * 直接使用 {@link Profile}
     */
    static class ProfileConfig {

        @Bean(name = "num")
        @Profile("odd")
        public Integer odd() {
            return 1;
        }

        @Bean(name = "num")
        @Profile("even")
        public Integer even() {
            return 2;
        }
    }

    /**
     * 通过 {@link Conditional} 自定义条件
     */
    static class ConditionConfig {

        @Bean(name = "num")
        @Conditional(OddCondition.class)
        public Integer odd() {
            return 1;
        }

        @Bean(name = "num")
        @Conditional(EvenCondition.class)
        public Integer even() {
            return 2;
        }
    }
}