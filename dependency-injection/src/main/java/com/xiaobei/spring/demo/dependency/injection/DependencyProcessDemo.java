package com.xiaobei.spring.demo.dependency.injection;

import com.xiaobei.spring.demo.dependency.domain.User;
import org.junit.Test;
import org.springframework.beans.TypeConverter;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-09-10 10:35:35
 */
public class DependencyProcessDemo {

    /**
     * <h2>依赖处理过程</h2>
     *
     * {@link DefaultListableBeanFactory#resolveDependency(DependencyDescriptor, String, Set, TypeConverter)}
     * {@code result = doResolveDependency(descriptor, requestingBeanName, autowiredBeanNames, typeConverter);}
     * {@link DefaultListableBeanFactory#doResolveDependency(DependencyDescriptor, String, Set, TypeConverter)}
     * {@code Object multipleBeans = resolveMultipleBeans(descriptor, beanName, autowiredBeanNames, typeConverter);}
     * 结果为 null，这个主要处理集合类型的Bean，比如：Map，Collection等
     * {@code Map<String, Object> matchingBeans = findAutowireCandidates(beanName, type, descriptor);} 获取所有同类型的bean
     * 其中 key 为 bean 的名称，value 为对应的 Class 类型
     * {@link DefaultListableBeanFactory#findAutowireCandidates(String, Class, DependencyDescriptor)}
     * {@code autowiredBeanName = determineAutowireCandidate(matchingBeans, descriptor);} 从集合中确定候选的bean
     * {@link DefaultListableBeanFactory#determineAutowireCandidate(Map, DependencyDescriptor)}
     *    若此时找到了多个bean的话，会进入 {@code return descriptor.resolveNotUnique(descriptor.getResolvableType(), matchingBeans);}
     *    最终会抛出 NoUniqueBeanDefinitionException
     * {@code instanceCandidate = descriptor.resolveCandidate(autowiredBeanName, type, this);}
     * 会将 {@code instanceCandidate}从 Class 改变为实际的 bean，最终返回获取到的对象。
     *
     * <h2>运行结果：</h2>
     * User{age=20200911, name='hui'}
     */
    @Test
    public void dependencyAutowiredIncludePrimaryBean() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AutowiredConfig.class);
        applicationContext.refresh();
        AutowiredConfig bean = applicationContext.getBean(AutowiredConfig.class);
        System.out.println(bean.user);
        applicationContext.close();
    }

    static class BeanConfig {

        @Bean
        public User user() {
            return new User().setAge(20200910).setName("xiao");
        }

        @Bean
        @Primary
        public User hui() {
            return new User().setAge(20200911).setName("hui");
        }
    }

    @Import(BeanConfig.class)
    @Configuration
    static class AutowiredConfig {

        @Autowired
        private User user;

    }

    /**
     *
     * <h2>依赖处理过程</h2>
     *
     * {@link DefaultListableBeanFactory#resolveDependency(DependencyDescriptor, String, Set, TypeConverter)}
     * {@code result = doResolveDependency(descriptor, requestingBeanName, autowiredBeanNames, typeConverter);}
     * {@link DefaultListableBeanFactory#doResolveDependency(DependencyDescriptor, String, Set, TypeConverter)}
     * {@code Object multipleBeans = resolveMultipleBeans(descriptor, beanName, autowiredBeanNames, typeConverter);}
     * 结果为 {@code {user=User{age=20200910, name='xiao'}, hui=User{age=20200911, name='hui'}}}，
     * 这个主要处理集合类型的Bean，比如：Map，Collection等
     * {@link DefaultListableBeanFactory#resolveMultipleBeans(DependencyDescriptor, String, Set, TypeConverter)}
     * 直接将查询出的对象集合返回
     *
     * <h2>运行结果：</h2>
     * {user=User{age=20200910, name='xiao'}, hui=User{age=20200911, name='hui'}}
     */
    @Test
    public void dependencyAutowiredCollectionBeans() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(CollectionInjectConfig.class);
        applicationContext.refresh();
        CollectionInjectConfig bean = applicationContext.getBean(CollectionInjectConfig.class);
        System.out.println(bean.users);
        applicationContext.close();
    }

    @Import(BeanConfig.class)
    @Configuration
    static class CollectionInjectConfig {

        @Autowired
        private Map<String, User> users;

    }

    /**
     *
     * <h2>依赖处理过程</h2>
     *
     * {@link DefaultListableBeanFactory#resolveDependency(DependencyDescriptor, String, Set, TypeConverter)}
     * {@code if (Optional.class == descriptor.getDependencyType()) }
     * {@code createOptionalDependency(descriptor, requestingBeanName);}
     * {@link DefaultListableBeanFactory#createOptionalDependency(DependencyDescriptor, String, Object...)}
     * {@code Object result = doResolveDependency(descriptorToUse, beanName, null, null);}
     * {@code Object multipleBeans = resolveMultipleBeans(descriptor, beanName, autowiredBeanNames, typeConverter);}
     * 结果为 null，这个主要处理集合类型的Bean，比如：Map，Collection等
     * {@code Map<String, Object> matchingBeans = findAutowireCandidates(beanName, type, descriptor);} 获取所有同类型的bean
     * 其中 key 为 bean 的名称，value 为对应的 Class 类型
     * {@link DefaultListableBeanFactory#findAutowireCandidates(String, Class, DependencyDescriptor)}
     * {@code autowiredBeanName = determineAutowireCandidate(matchingBeans, descriptor);} 从集合中确定候选的bean
     * {@link DefaultListableBeanFactory#determineAutowireCandidate(Map, DependencyDescriptor)}
     *    若此时找到了多个bean的话，会进入 {@code return descriptor.resolveNotUnique(descriptor.getResolvableType(), matchingBeans);}
     *    最终会抛出 NoUniqueBeanDefinitionException
     * {@code instanceCandidate = descriptor.resolveCandidate(autowiredBeanName, type, this);}
     * 注意：此外的 {@code descriptor} 在 {@link DefaultListableBeanFactory#createOptionalDependency(DependencyDescriptor, String, Object...)}
     * 中做了处理！！
     * 会将 {@code instanceCandidate}从 Class 改变为实际的 bean，最终返回获取到的对象。
     * 如果没有找到任何匹配的bean，也不会报错 {@link UnsatisfiedDependencyException}！！
     *
     * <h2>运行结果：</h2>
     * Optional[User{age=20200911, name='hui'}]
     */
    @Test
    public void dependencyAutowiredOptionalBean() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(OptionInjectConfig.class);
        applicationContext.refresh();
        OptionInjectConfig bean = applicationContext.getBean(OptionInjectConfig.class);
        System.out.println(bean.optionalUser);
        applicationContext.close();
    }

    @Import(BeanConfig.class)
    @Configuration
    static class OptionInjectConfig {

        @Autowired
        private Optional<User> optionalUser;

    }

}