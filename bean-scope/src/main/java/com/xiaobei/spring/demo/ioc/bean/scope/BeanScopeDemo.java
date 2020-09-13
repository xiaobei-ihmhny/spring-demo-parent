package com.xiaobei.spring.demo.ioc.bean.scope;

import com.xiaobei.spring.demo.ioc.bean.scope.config.ThreadLocalScope;
import com.xiaobei.spring.demo.ioc.bean.scope.domain.ScopeDomain;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.*;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/8 18:45
 */
@SuppressWarnings("DuplicatedCode")
public class BeanScopeDemo {


    /**
     * 运行结果表示：
     * {@link ConfigurableBeanFactory#SCOPE_SINGLETON}类型的bean，依赖查找时，得到的都是同一个类型的bean
     * {@link ConfigurableBeanFactory#SCOPE_PROTOTYPE}类型的bean，依赖查找时，得到的都是新生成对象，
     * 且这种类型的Bean没有完整的生命周期（只有bean的初始化回调，没有bean的销毁回调）
     *
     * <p>运行结果：
     * 当前bean [singletonDomain] 正在进行初始化...
     * singletonDomain = ScopeDomain{Thread.getId=1, id=1599919504392, name='', beanName='singletonDomain'}
     * 当前bean [prototypeDomain] 正在进行初始化...
     * prototypeDomain = ScopeDomain{Thread.getId=1, id=1599919516778, name='', beanName='prototypeDomain'}
     * singletonDomain = ScopeDomain{Thread.getId=1, id=1599919504392, name='', beanName='singletonDomain'}
     * 当前bean [prototypeDomain] 正在进行初始化...
     * prototypeDomain = ScopeDomain{Thread.getId=1, id=1599919522235, name='', beanName='prototypeDomain'}
     * 当前bean [singletonDomain] 正在进行销毁...
     */
    @Test
    public void scopeBeanByLookup() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(BeanScopeRegisterConfig.class);
        applicationContext.refresh();
        for (int i = 0; i < 2; i++) {
            ScopeDomain singletonDomain = applicationContext.getBean("singletonDomain", ScopeDomain.class);
            System.out.println("singletonDomain = " + singletonDomain);
            ScopeDomain prototypeBean = applicationContext.getBean("prototypeDomain", ScopeDomain.class);
            System.out.println("prototypeDomain = " + prototypeBean);
        }
        applicationContext.close();
    }

    @Configuration
    static class BeanScopeRegisterConfig {
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
    }

    /**
     * 从运行结果看：
     * {@link ConfigurableBeanFactory#SCOPE_SINGLETON}类型的bean，依赖注入时，都是同一个类型的bean
     * {@link ConfigurableBeanFactory#SCOPE_PROTOTYPE}类型的bean，依赖注入时，均为新生成对象，
     * 且这种类型的Bean没有完整的生命周期（只有bean的初始化回调，没有bean的销毁回调），
     * 如果依赖注入集合对象，Singleton Bean 和 Prototype Bean均会存在一个，
     * 且集合中的Prototype Bean 有别于其他地方的依赖注入
     *
     * <p>运行结果：
     * 当前bean [singletonDomain] 正在进行初始化...
     * 当前bean [prototypeDomain] 正在进行初始化...
     * 当前bean [prototypeDomain] 正在进行初始化...
     * 当前bean [prototypeDomain] 正在进行初始化...
     * 当前bean [prototypeDomain] 正在进行初始化...
     * ScopeDomain{Thread.getId=1, id=1599919906173, name='', beanName='singletonDomain'}
     * ScopeDomain{Thread.getId=1, id=1599919906173, name='', beanName='singletonDomain'}
     * ScopeDomain{Thread.getId=1, id=1599919906191, name='', beanName='prototypeDomain'}
     * ScopeDomain{Thread.getId=1, id=1599919906261, name='', beanName='prototypeDomain'}
     * ScopeDomain{Thread.getId=1, id=1599919906257, name='', beanName='prototypeDomain'}
     * {singletonDomain=ScopeDomain{Thread.getId=1, id=1599919906173, name='', beanName='singletonDomain'}, prototypeDomain=ScopeDomain{Thread.getId=1, id=1599919906207, name='', beanName='prototypeDomain'}}
     * 当前bean [singletonDomain] 正在进行销毁...
     */
    @Test
    public void scopeBeanByInject() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(BeanScopeDemoConfig.class);
        applicationContext.refresh();
        BeanScopeDemoConfig bean = applicationContext.getBean(BeanScopeDemoConfig.class);
        System.out.println(bean.singletonDomain);
        System.out.println(bean.singletonDomain2);
        System.out.println(bean.prototypeDomain);
        System.out.println(bean.prototypeDomain2);
        System.out.println(bean.prototypeDomain3);
        System.out.println(bean.scopeDomains);
        applicationContext.close();
    }


    @Configuration
    @Import(BeanScopeRegisterConfig.class)
    static class BeanScopeDemoConfig {

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

    }

    /**
     * 若为java注解的方式，可以通过实现配置类的销毁方法，
     * 在其销毁方法中手动调用对应prototype类型的bean的销毁方法；
     * 若注入的是集合类型，比如{@link Map <String, Object>}，
     * 可以在配置类中注入非spring管理依赖中的{@link BeanFactory}，
     * 通过{@link BeanFactory}获取对应集合中的所有bean的
     * {@link ConfigurableListableBeanFactory#getBeanDefinition(java.lang.String)}方法
     * 获{@link BeanDefinition}并通过判断{@link BeanDefinition#isPrototype()}来筛选类型为：
     * {@link ConfigurableBeanFactory#SCOPE_PROTOTYPE}的bean后，再调用其销毁方法。
     *
     *
     * <p>运行结果：
     * 当前bean [singletonDomain] 正在进行初始化...
     * 当前bean [prototypeDomain] 正在进行初始化...
     * 当前bean [prototypeDomain] 正在进行初始化...
     * 当前bean [prototypeDomain] 正在进行初始化...
     * 当前bean [prototypeDomain] 正在进行初始化...
     * 当前bean [prototypeDomain] 正在进行销毁...
     * 当前bean [prototypeDomain] 正在进行销毁...
     * 当前bean [prototypeDomain] 正在进行销毁...
     * 当前bean [prototypeDomain] 正在进行销毁...
     * 当前bean [singletonDomain] 正在进行销毁...
     */
    @Test
    public void destroyPrototypeBean() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(PrototypeBeanDestroyConfig.class);
        applicationContext.refresh();
        applicationContext.close();
    }

    /**
     * 对于 {@link ConfigurableBeanFactory#SCOPE_PROTOTYPE prototype} 类型的bean，手动调用其销毁方法
     */
    @Configuration
    @Import({BeanScopeRegisterConfig.class, BeanScopeDemoConfig.class})
    static class PrototypeBeanDestroyConfig implements DisposableBean {

        @Autowired
        private BeanScopeDemoConfig beanScopeDemoConfig;

        /**
         * 注入 Resolvable Dependency 类型
         */
        @Autowired
        private ConfigurableListableBeanFactory beanFactory;

        @Override
        public void destroy() throws Exception {
            beanScopeDemoConfig.prototypeDomain.destroy();
            beanScopeDemoConfig.prototypeDomain2.destroy();
            beanScopeDemoConfig.prototypeDomain3.destroy();
            beanScopeDemoConfig.scopeDomains.forEach((beanName, ScopeDomain) -> {
                BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
                if(beanDefinition.isPrototype()) {
                    ScopeDomain scopeDomain = beanScopeDemoConfig.scopeDomains.get(beanName);
                    scopeDomain.destroy();

                }
            });
        }
    }

    /**
     * 从运行结果可以看出，同一个线程会有一个单独的对象副本，实现{@link ThreadLocalScope}的效果
     *
     * <p>运行结果：
     * 当前bean [customScopeDomain] 正在进行初始化...
     * ScopeDomain{Thread.getId=13, id=1599922852264, name='pool-1-thread-2', beanName='customScopeDomain'}
     * ScopeDomain{Thread.getId=13, id=1599922852264, name='pool-1-thread-2', beanName='customScopeDomain'}
     * 当前bean [customScopeDomain] 正在进行初始化...
     * 当前bean [customScopeDomain] 正在进行初始化...
     * ScopeDomain{Thread.getId=12, id=1599922852254, name='pool-1-thread-1', beanName='customScopeDomain'}
     * ScopeDomain{Thread.getId=14, id=1599922852228, name='pool-1-thread-3', beanName='customScopeDomain'}
     * @throws InterruptedException
     */
    @Test
    public void customScopeDemo() throws InterruptedException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(CustomBeanScopeDomainConfig.class);
        // 线程级别的bean
        applicationContext.addBeanFactoryPostProcessor(beanFactory -> {
            beanFactory.registerScope(ThreadLocalScope.SCOPE_NAME, new ThreadLocalScope());
        });
        applicationContext.refresh();
        int currentCount = 4;
        CountDownLatch latch = new CountDownLatch(currentCount);
        ExecutorService pool = getPool();
        for (int i = 0; i < currentCount; i++) {
            pool.submit(() -> {
                ScopeDomain scopeDomain = applicationContext.getBean("customScopeDomain", ScopeDomain.class);
                System.out.println(scopeDomain);
                latch.countDown();
            });
        }
        latch.await();
        pool.shutdown();
        applicationContext.close();
    }

    static class CustomBeanScopeDomainConfig {

        /**
         * 自定义的线程级别的作用域
         * @return
         */
        @Bean
        @Scope(ThreadLocalScope.SCOPE_NAME)
        public ScopeDomain customScopeDomain () {
            return createBeanScopeDemo();
        }

        public ScopeDomain createBeanScopeDemo() {
            return new ScopeDomain()
                    .setId(System.currentTimeMillis() + Math.abs(ThreadLocalRandom.current().nextInt() % 100))
                    .setName(Thread.currentThread().getName());
        }
    }

    private ThreadPoolExecutor getPool() {
        return new ThreadPoolExecutor(
                3,
                5,
                10L,
                TimeUnit.SECONDS, new LinkedBlockingDeque<>());
    }

}
