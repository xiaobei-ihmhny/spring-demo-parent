package com.xiaobei.spring.demo.bean.lifecycle;

import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.InitDestroyAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.util.ObjectUtils;

import javax.annotation.PreDestroy;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Bean的销毁阶段
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/12 20:14
 */
public class BeanDestructionLifecycleDemo {

    static class BeforeDestructionDomain {

        private Long id;

        private String name;

        public Long getId() {
            return id;
        }

        public BeforeDestructionDomain setId(Long id) {
            this.id = id;
            return this;
        }

        public String getName() {
            return name;
        }

        public BeforeDestructionDomain setName(String name) {
            this.name = name;
            return this;
        }

        @Override
        public String toString() {
            return "BeforeDestructionDomain{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }

    }

    /**
     * <h1>Bean销毁前阶段</h1>
     *
     * <p>运行结果：</p>
     * <br>=============================================<br>
     * 销毁前 BeforeDestructionDomain{id=1, name='销毁前的配置'}
     * 开始执行 lifeCycleDomain在销毁前的回调...
     * 销毁后BeforeDestructionDomain{id=102, name='销毁前的配置'}
     * <br>=============================================<br>
     *
     * <h2>调用流程：</h2>
     * <br>=============================================<br>
     * {@link AbstractBeanFactory#destroyBean(String, Object)}
     * {@link AbstractBeanFactory#destroyBean(String, Object, RootBeanDefinition)}
     * {@link org.springframework.beans.factory.support.DisposableBeanAdapter#destroy}
     * 的该方法 {@code processor.postProcessBeforeDestruction(this.bean, this.beanName);} 调用销毁前的阶段
     * <br>=============================================<br>
     *
     * Bean销毁前阶段的方法回调位于：
     * {@link DestructionAwareBeanPostProcessor#postProcessBeforeDestruction(java.lang.Object, java.lang.String)}
     */
    @Test
    public void postProcessBeforeDestruction() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        String location = "META-INF/life-cycle-before-destruction-domain.xml";
        reader.loadBeanDefinitions(location);
        // 添加 Spring Bean 销毁前阶段的回调
        beanFactory.addBeanPostProcessor(new DestructionAwareBeanPostProcessor() {
            @Override
            public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {
                // 拦截 id="lifeCycleDomain" 的 bean，并做一定的修改
                if(ObjectUtils.nullSafeEquals("beforeDestructionDomain", beanName)
                        && BeforeDestructionDomain.class.equals(bean.getClass())) {
                    System.out.println("开始执行 lifeCycleDomain在销毁前的回调...");
                    // 修改相应bean中的信息
                    BeforeDestructionDomain domain = (BeforeDestructionDomain) bean;
                    // 将bean中的id属性设置为98L
                    domain.setId(102L);
                }
            }
        });
        // bean依赖查找
        BeforeDestructionDomain domain = beanFactory.getBean("beforeDestructionDomain", BeforeDestructionDomain.class);
        System.out.println("销毁前 " + domain);
        // 执行bean的销毁操作
        beanFactory.destroyBean("beforeDestructionDomain", domain);
        System.out.println("销毁后" + domain);
    }


    static class DestructionDomain implements DisposableBean {

        private Long id;

        private String name;

        private final AtomicInteger count = new AtomicInteger(0);

        public Long getId() {
            return id;
        }

        public DestructionDomain setId(Long id) {
            this.id = id;
            return this;
        }

        public String getName() {
            return name;
        }

        public DestructionDomain setName(String name) {
            this.name = name;
            return this;
        }

        @Override
        public String toString() {
            return "DestructionDomain{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
        /**
         * Bean的销毁回调
         */
        @PreDestroy
        public void preDestroy() {
            System.out.printf("@PreDestroy 调用顺序：%d\n", count.addAndGet(1));
        }

        /**
         * Bean的销毁回调
         * @throws Exception
         */
        @Override
        public void destroy() throws Exception {
            System.out.printf("destroy() 调用顺序：%d\n", count.addAndGet(1));
        }
        /**
         * Bean的销毁回调
         */
        public void initDestroy() {
            System.out.printf("initDestroy() 调用顺序：%d\n", count.addAndGet(1));
        }
    }

    /**
     * <h1>Bean的销毁阶段</h1>
     * <h2>运行结果：</h2>
     * 销毁前 DestructionDomain{id=1, name='销毁的配置'}
     * @PreDestroy 调用顺序：1
     * destroy() 调用顺序：2
     * initDestroy() 调用顺序：3
     * 销毁后DestructionDomain{id=1, name='销毁的配置'}
     *
     * <h2>调用流程：</h2>
     * =============================================
     * {@link AbstractBeanFactory#destroyBean(String, Object)}
     * {@link AbstractBeanFactory#destroyBean(String, Object, RootBeanDefinition)}
     * {@link org.springframework.beans.factory.support.DisposableBeanAdapter#destroy}
     * 在该方法的{@code ((DisposableBean) this.bean).destroy();}处，
     * 先调用 {@link DisposableBean#destroy()}
     * 再调用自定义销毁方法{@code invokeCustomDestroyMethod(this.destroyMethod);}
     *
     * 其中注解{@link javax.annotation.PreDestroy @PreDestroy}的调用是在
     * {@link InitDestroyAnnotationBeanPostProcessor#postProcessBeforeDestruction(Object, String)} 中进行的
     * 由于 {@link CommonAnnotationBeanPostProcessor} 实现了 {@link InitDestroyAnnotationBeanPostProcessor}
     * 而 {@link InitDestroyAnnotationBeanPostProcessor} 又实现了 {@link DestructionAwareBeanPostProcessor}
     * 故 {@link javax.annotation.PreDestroy @PreDestroy} 会在销毁前的阶段就执行了，要早于另外的两种
     * DisposableBean's {@code destroy} and a custom destroy method
     * =============================================
     *
     */
    @Test
    public void postProcessDestruction() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        String location = "META-INF/life-cycle-destruction-domain.xml";
        reader.loadBeanDefinitions(location);
        // 处理 {@link PostConstruct}/{@link PreDestroy}注解
        beanFactory.addBeanPostProcessor(new CommonAnnotationBeanPostProcessor());
        // bean依赖查找
        DestructionDomain domain = beanFactory.getBean("destructionDomain", DestructionDomain.class);
        System.out.println("销毁前 " + domain);
        // 执行bean的销毁操作
        beanFactory.destroyBean("destructionDomain", domain);
        System.out.println("销毁后" + domain);
    }
}
