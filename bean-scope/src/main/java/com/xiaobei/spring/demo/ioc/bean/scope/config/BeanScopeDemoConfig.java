package com.xiaobei.spring.demo.ioc.bean.scope.config;

import com.xiaobei.spring.demo.ioc.bean.scope.domain.ScopeDomain;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/8 18:50
 */
public class BeanScopeDemoConfig implements DisposableBean {

    @Autowired
    @Qualifier("singletonDomain")
    public ScopeDomain singletonDomain;

    @Autowired
    @Qualifier("singletonDomain")
    public ScopeDomain singletonDomain2;

    @Autowired
    @Qualifier("prototypeDomain")
    public ScopeDomain prototypeDomain;

    @Autowired
    @Qualifier("prototypeDomain")
    public ScopeDomain prototypeDomain2;

    @Autowired
    @Qualifier("prototypeDomain")
    public ScopeDomain prototypeDomain3;

    @Autowired
    public Map<String, ScopeDomain> scopeDomains;

    /**
     * 注入 Resolvable Dependency 类型
     */
    @Autowired
    private ConfigurableListableBeanFactory beanFactory;

    /**
     * 默认情况下是单例模式
     * @return
     */
    @Bean
    public ScopeDomain singletonDomain () {
        return createBeanScopeDemo();
    }

    /**
     * 指定生成原型bean
     * @return
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public ScopeDomain prototypeDomain () {
        return createBeanScopeDemo();
    }

    public ScopeDomain createBeanScopeDemo() {
        return new ScopeDomain()
                .setId(System.currentTimeMillis() + Math.abs(ThreadLocalRandom.current().nextInt() % 100))
                .setName("");
    }

    @Override
    public void destroy() throws Exception {
        this.prototypeDomain.destroy();
        this.prototypeDomain2.destroy();
        this.prototypeDomain3.destroy();
        scopeDomains.forEach((beanName, ScopeDomain) -> {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
            if(beanDefinition.isPrototype()) {
                com.xiaobei.spring.demo.ioc.bean.scope.domain.ScopeDomain scopeDomain = scopeDomains.get(beanName);
                scopeDomain.destroy();

            }
        });
    }
}
