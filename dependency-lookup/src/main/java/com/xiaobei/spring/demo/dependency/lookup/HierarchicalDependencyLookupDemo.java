package com.xiaobei.spring.demo.dependency.lookup;

import com.xiaobei.spring.demo.dependency.domain.*;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.HierarchicalBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 层次性依赖查找示例
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/2 21:11
 */
@SuppressWarnings("DuplicatedCode")
public class HierarchicalDependencyLookupDemo {

    @Test
    public void parentsBeanFactoryDemo() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ListableBeanConf.class);
        // HierarchicalBeanFactory <- ConfigurableBeanFactory <- ConfigurableListableBeanFactory
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
//        System.out.println(beanFactory.getParentBeanFactory());
        HierarchicalBeanFactory parentBeanFactory = getBeanFactoryWithXml();
        beanFactory.setParentBeanFactory(parentBeanFactory);
//        System.out.println(parentBeanFactory);
        applicationContext.refresh();
        displayContainsLocalBean(beanFactory, "history");
        displayContainsLocalBean(parentBeanFactory, "history");
        displayContainsBean(beanFactory, "history");
        displayContainsBean(parentBeanFactory, "history");

        applicationContext.close();
    }

    private void displayContainsBean(HierarchicalBeanFactory beanFactory, String beanName) {
        System.out.printf("当前BeanFactory[%s]是否包含bean[%s] : %s\n", beanFactory, beanName,
                containsBean(beanFactory, beanName));
    }

    private boolean containsBean(HierarchicalBeanFactory beanFactory, String beanName) {
        BeanFactory parentBeanFactory = beanFactory.getParentBeanFactory();
        if(parentBeanFactory instanceof HierarchicalBeanFactory) {
            HierarchicalBeanFactory hierarchicalBeanFactory = (HierarchicalBeanFactory) parentBeanFactory;
            return containsBean(hierarchicalBeanFactory, beanName);
        }
        return beanFactory.containsLocalBean(beanName);
    }

    private void displayContainsLocalBean(HierarchicalBeanFactory beanFactory, String beanName) {
        System.out.printf("当前BeanFactory[%s]是否包含Local bean[%s] : %s\n", beanFactory, beanName,
                beanFactory.containsLocalBean(beanName));
    }

    /**
     * 通过xml构造另一个应用上下文
     * @return
     */
    public ConfigurableListableBeanFactory getBeanFactoryWithXml() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        // xml配置文件classpath路径
        String location = "classpath:/META-INF/dependency-parents.xml";
        reader.loadBeanDefinitions(location);
        return beanFactory;
    }
    /**
     * 通过java代码构造另一个应用上下文
     * @return
     */
    public ConfigurableListableBeanFactory getBeanFactoryWithAnnotation() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AncestorsBeanConf.class);
        applicationContext.refresh();
        return applicationContext.getBeanFactory();
    }

    /**
     * 先从子应用上下文中查找相应的bean，如果子上下文中没有，再在父上下文中查找
     */
    @Test
    public void getBeanByNameAndTypeFromAllParents() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ListableBeanConf.class);
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        HierarchicalBeanFactory parentBeanFactory = getBeanFactoryWithXml();
        beanFactory.setParentBeanFactory(parentBeanFactory);
        applicationContext.refresh();
        Lesson history = getBean(beanFactory, "history", Lesson.class);
        System.out.println(history);
        Lesson chinese = getBean(beanFactory, "chinese", Lesson.class);
        System.out.println(chinese);
        Lesson english = getBeanFromAllApplicationContext(beanFactory, "english", Lesson.class);
        System.out.println(english);
        applicationContext.close();
    }


    /**
     * 从所有的spring应用上下文中查找对应名称和类型的bean
     * @param beanFactory
     * @param beanName
     * @param requiredType
     * @param <T>
     * @return
     */
    <T> T getBeanFromAllApplicationContext(HierarchicalBeanFactory beanFactory, String beanName, Class<T> requiredType) {
        Assert.notNull(beanFactory, "HierarchicalBeanFactory must not be null");
        T bean = null;
        if(beanFactory.containsLocalBean(beanName)) {
            bean = beanFactory.getBean(beanName, requiredType);
        }
        T parentBean = getBean(beanFactory, beanName, requiredType);
        return parentBean == null ? bean : parentBean;
    }

    <T> T getBean(HierarchicalBeanFactory beanFactory, String beanName, Class<T> requiredType) {
        if(beanFactory.getParentBeanFactory() instanceof HierarchicalBeanFactory) {
            HierarchicalBeanFactory parentHierarchicalBeanFactory = (HierarchicalBeanFactory) beanFactory.getParentBeanFactory();
            if(!parentHierarchicalBeanFactory.containsLocalBean(beanName)) {
                return getBean(parentHierarchicalBeanFactory, beanName, requiredType);
            } else {
                return parentHierarchicalBeanFactory.getBean(beanName, requiredType);
            }
        }
        return null;
    }

    /**
     * 使用{@link BeanFactoryUtils#beanOfTypeIncludingAncestors(ListableBeanFactory, java.lang.Class)}
     * 在所有上下文中查询指定类型的bean。若父类及子类中同时含有，则会返回父类中的bean
     *
     * 执行结果：
     * CustomLesson{id=44, name='集合类型依赖查找——自定义课程'}
     * LessonPrototype{id=45, name='computer'}
     */
    @Test
    public void getBeanByTypeFromAllParent() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ListableBeanConf.class);
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        HierarchicalBeanFactory parentBeanFactory = getBeanFactoryWithXml();
        beanFactory.setParentBeanFactory(parentBeanFactory);
        applicationContext.refresh();
        // 查询当前上下文中的bean
        CustomLesson customLesson = BeanFactoryUtils.beanOfTypeIncludingAncestors(beanFactory, CustomLesson.class);
        System.out.println(customLesson);
        // 查询父上下文中的bean
        LessonPrototype lessonPrototype = BeanFactoryUtils.beanOfTypeIncludingAncestors(beanFactory, LessonPrototype.class);
        System.out.println(lessonPrototype);
        applicationContext.close();
    }
    /**
     * TODO 这个是 “双亲委派” 吗？怎么感觉这个和双亲的逻辑是反的呢？
     * 根据Bean类型查找实例列表
     * 使用{@link BeanFactoryUtils#beansOfTypeIncludingAncestors(ListableBeanFactory, java.lang.Class)}
     * 查找所有应用上下文中含有的指定类型的bean实例集合
     */
    @Test
    public void getBeanByTypeIncludingAncestors() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ListableBeanConf.class);
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        HierarchicalBeanFactory parentBeanFactory = getBeanFactoryWithXml();
        beanFactory.setParentBeanFactory(parentBeanFactory);
        applicationContext.refresh();
        // 查询所有应用上下文中的bean
        Map<String, Lesson> lessonMap = BeanFactoryUtils.beansOfTypeIncludingAncestors(beanFactory, Lesson.class);
        System.out.println(lessonMap);
        applicationContext.close();
    }

    /**
     * TODO 如果父、子应用上下文中含有同类型、同名称的bean，但只有父上下文中存在注解标记，那需要查出来吗？目前的逻辑是查不出来的！
     * 使用{@link BeanFactoryUtils#beanNamesForAnnotationIncludingAncestors(ListableBeanFactory, java.lang.Class)}
     * 来获取所有应用上下文中含有指定注解标记的bean
     */
    @Test
    public void getBeanNamesForTypeIncludingAncestors() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ListableBeanConf.class);
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        HierarchicalBeanFactory parentBeanFactory = getBeanFactoryWithAnnotation();
        beanFactory.setParentBeanFactory(parentBeanFactory);
        applicationContext.refresh();
        // 查询所有应用上下文中的含有@MyLesson标记的bean
        String[] beanNames = BeanFactoryUtils.beanNamesForAnnotationIncludingAncestors(beanFactory, MyLesson.class);
        System.out.println(Arrays.toString(beanNames));
        applicationContext.close();
    }
}
