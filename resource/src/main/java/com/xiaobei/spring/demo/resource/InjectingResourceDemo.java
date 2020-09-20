package com.xiaobei.spring.demo.resource;

import com.xiaobei.spring.demo.resource.utils.ResourceUtils;
import org.junit.Test;
import org.springframework.beans.*;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.beans.support.ResourceEditorRegistrar;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceEditor;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.lang.Nullable;
import org.springframework.util.ReflectionUtils;

import javax.annotation.PostConstruct;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/16 21:35
 */
public class InjectingResourceDemo {

    /**
     * 注入单个资源对象
     */
    @Value("classpath:/META-INF/dev.properties")
    private Resource devResource;

    @Value("classpath*:/META-INF/*.properties")// 类型转换器为：ResourceArrayPropertyEditor
    private Resource[] propertiesResources;

    /**
     * 多个资源信息无法正常集合类型？？？？
     * 原因：Spring 内建的类型转换器 {@link CustomCollectionEditor} 不支持统配符，
     * 故可自定义类型转换器，参考 {@link ResourceCollectionEditor}、{@link ResourceCollectionEditorRegistrar}
     */
    @Value("classpath*:/META-INF/*.properties") // 类型转换器为：CustomCollectionEditor
    private List<Resource> propertiesResourceList;

    @PostConstruct
    public void init() {
        System.out.println(ResourceUtils.getContext(devResource));
        System.out.println("====================================");
        Stream.of(propertiesResources).map(ResourceUtils::getContext).forEach(System.out::println);
        System.out.println("====================================");
        if(propertiesResourceList != null) propertiesResourceList.stream().map(ResourceUtils::getContext).forEach(System.out::println);

    }

    /**
     * 处理 {@link Collection<Resource> } 类型的类型转换
     */
    static class ResourceCollectionEditor extends CustomCollectionEditor implements ApplicationContextAware, EnvironmentAware {

        private ResourcePatternResolver resourcePatternResolver;

        @Nullable
        private PropertyResolver propertyResolver;

        private boolean ignoreUnresolvablePlaceholders = true;

        private Class<? extends Collection> collectionType;

        public ResourceCollectionEditor(Class<? extends Collection> collectionType) {
            super(collectionType);
            this.collectionType = collectionType;
        }

        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            String pattern = resolvePath(text).trim();
            try {
                Resource[] resources = this.resourcePatternResolver.getResources(pattern);
                // 将 数据转换为 集合
                Collection collection = null;
                if (List.class == collectionType) {
                    collection =  new ArrayList<>(resources.length);
                }
                for (int i = 0; i < resources.length; i++) {
                    collection.add(resources[i]);
                }
                super.setValue(collection);
            }
            catch (IOException ex) {
                throw new IllegalArgumentException(
                        "Could not resolve resource location pattern [" + pattern + "]: " + ex.getMessage());
            }
        }

        protected String resolvePath(String path) {
            if (this.propertyResolver == null) {
                this.propertyResolver = new StandardEnvironment();
            }
            return (this.ignoreUnresolvablePlaceholders ? this.propertyResolver.resolvePlaceholders(path) :
                    this.propertyResolver.resolveRequiredPlaceholders(path));
        }

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            this.resourcePatternResolver = applicationContext;
        }

        @Override
        public void setEnvironment(Environment environment) {
            this.propertyResolver = environment;
        }
    }

    static class ResourceCollectionEditorRegistrar implements PropertyEditorRegistrar {

        private final ResourceCollectionEditor resourceCollectionEditor;

        public ResourceCollectionEditorRegistrar(ResourceCollectionEditor booleanArrayEditor) {
            this.resourceCollectionEditor = booleanArrayEditor;
        }

        @Override
        public void registerCustomEditors(PropertyEditorRegistry registry) {
            registry.registerCustomEditor(Collection.class, resourceCollectionEditor);
        }
    }

    @Configuration
    static class ResourceCollectionEditorConfig {

        @Bean
        public ResourceCollectionEditor editor() {
            return new ResourceCollectionEditor(List.class);
        }

        @Bean
        public ResourceCollectionEditorRegistrar registrar() {
            return new ResourceCollectionEditorRegistrar(editor());
        }

        @Bean
        public CustomEditorConfigurer customEditorConfigurer() {
            CustomEditorConfigurer configurer = new CustomEditorConfigurer();
            configurer.setPropertyEditorRegistrars(new ResourceCollectionEditorRegistrar[]{registrar()});
            return configurer;
        }

    }

    /**
     * <h2>运行结果：</h2>
     * name=131
     * ====================================
     * name=131
     * name=\u4F9D\u8D56\u6CE8\u5165Spring Resource
     *
     * <p>{@link Value @Value} 加载原理 </p>
     * {@link AbstractAutowireCapableBeanFactory#populateBean(String, RootBeanDefinition, BeanWrapper)}
     * {@link AutowiredAnnotationBeanPostProcessor#postProcessProperties(PropertyValues, Object, String)}
     * {@link InjectionMetadata#inject(Object, String, PropertyValues)}
     * {@link AutowiredAnnotationBeanPostProcessor.AutowiredFieldElement#inject(Object, String, PropertyValues)}
     * {@link DefaultListableBeanFactory#resolveDependency(DependencyDescriptor, String, java.util.Set, TypeConverter)}
     * {@link DefaultListableBeanFactory#doResolveDependency(DependencyDescriptor, String, java.util.Set, TypeConverter)}
     * {@link TypeConverterSupport#convertIfNecessary(Object, Class, TypeDescriptor)}
     * {@link TypeConverterDelegate#convertIfNecessary(String, Object, Object, Class, TypeDescriptor)}
     * {@link TypeConverterDelegate#doConvertValue(Object, Object, Class, PropertyEditor)}
     * {@link TypeConverterDelegate#doConvertTextValue(Object, String, PropertyEditor)}
     * {@link ResourceEditor#setAsText(String)}
     */
    @Test
    public void injectResource() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册当前类
        applicationContext.register(InjectingResourceDemo.class);
        // 注册 Collection<Resource> 转换器
        applicationContext.register(ResourceCollectionEditorConfig.class);
        // 启动应用上下文
        applicationContext.refresh();
        // 关闭应用上下文
        applicationContext.close();
    }

    /**
     * <h2>Spring 内建类型转换器的加载过程可分为两步：</h2>
     * <h3>第一步：{@link PropertySourcesPlaceholderConfigurer} 的 BeanPostProcessor 后置处理及 bean 创建阶段</h3>
     * 该阶段完成对 系统环境变量、java环境变量的读取 以及 {@link PropertyEditorRegistrySupport#overriddenDefaultEditors} 属性的配置工作
     * {@link AbstractApplicationContext#refresh()}
     * {@code invokeBeanFactoryPostProcessors(beanFactory);}
     * {@link AbstractApplicationContext#invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory)}
     * {@code PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors(beanFactory, getBeanFactoryPostProcessors()); }
     * {@link org.springframework.context.support.PostProcessorRegistrationDelegate#invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory, List)}
     * {@code priorityOrderedPostProcessors.add(beanFactory.getBean(ppName, BeanFactoryPostProcessor.class));}
     * // 接下来就是 getBean 的操作
     * // 此外的 bean 的名称为：{@code org.springframework.context.support.PropertySourcesPlaceholderConfigurer#0}
     * {@link AbstractBeanFactory#getBean(String, Class)}
     * {@link AbstractBeanFactory#doGetBean(String, Class, Object[], boolean)}
     * {@link AbstractAutowireCapableBeanFactory#createBean(String, RootBeanDefinition, Object[])}
     * {@link AbstractAutowireCapableBeanFactory#createBeanInstance(String, RootBeanDefinition, Object[])}
     * {@link AbstractAutowireCapableBeanFactory#instantiateBean(String, RootBeanDefinition)}
     * {@link AbstractBeanFactory#initBeanWrapper(BeanWrapper)}
     * 注册 Editors {@code registerCustomEditors(bw);}
     * {@link AbstractBeanFactory#registerCustomEditors(PropertyEditorRegistry)}
     * 注册内建的 {@link PropertyEditorSupport} {@link ResourceEditorRegistrar#registerCustomEditors(PropertyEditorRegistry)}
     * {@link ResourceEditorRegistrar#doRegisterEditor(PropertyEditorRegistry, Class, PropertyEditor)}
     * {@link PropertyEditorRegistrySupport#overrideDefaultEditor(Class, PropertyEditor)}
     * 将相关转换器设置到属性 {@link PropertyEditorRegistrySupport#overriddenDefaultEditors} 中 目前有 12 个
     *
     * <h3>第二步：业务 bean 的 属性赋值阶段</h3>
     * ......
     * {@link AbstractAutowireCapableBeanFactory#applyPropertyValues(String, BeanDefinition, BeanWrapper, PropertyValues)}
     * {@code convertedValue = convertForProperty(resolvedValue, propertyName, bw, converter);}
     * {@link AbstractAutowireCapableBeanFactory#convertForProperty(Object, String, BeanWrapper, TypeConverter)}
     * {@code ((BeanWrapperImpl) converter).convertForProperty(value, propertyName);}
     * {@link BeanWrapperImpl#convertForProperty(Object, String)}
     * {@code return convertForProperty(propertyName, null, value, td); }
     * {@link AbstractNestablePropertyAccessor#convertForProperty(String, Object, Object, TypeDescriptor)}
     * {@link AbstractNestablePropertyAccessor#convertIfNecessary(String, Object, Object, Class, TypeDescriptor)}
     * {@link TypeConverterDelegate#convertIfNecessary(java.lang.String, java.lang.Object, java.lang.Object, java.lang.Class, org.springframework.core.convert.TypeDescriptor)}
     * {@code editor = findDefaultEditor(requiredType);}
     * {@link TypeConverterDelegate#findDefaultEditor(java.lang.Class)}
     * {@link PropertyEditorRegistrySupport#getDefaultEditor(java.lang.Class)}
     * 加载Spring 默认提供的各种类型转换器 {@link PropertyEditorRegistrySupport#createDefaultEditors()} 共计 47 个
     */
    @Test
    public void defaultEditorsLoadingProcess() {

    }
}
