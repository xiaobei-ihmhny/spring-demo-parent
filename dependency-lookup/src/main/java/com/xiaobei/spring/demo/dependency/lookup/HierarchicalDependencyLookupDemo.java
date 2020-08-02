package com.xiaobei.spring.demo.dependency.lookup;

import com.xiaobei.spring.demo.dependency.domain.CustomLesson;
import com.xiaobei.spring.demo.dependency.domain.Lesson;
import com.xiaobei.spring.demo.dependency.domain.LessonPrototype;
import com.xiaobei.spring.demo.dependency.domain.ListableBeanConf;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.HierarchicalBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

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
     * 先从父应用上下文中查找相应的bean，如果父上下文中没有，再在子类中查找
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
     * TODO 单一类型查找，报错
     */
    @Test
    public void getBeanByTypeFromAllParent() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ListableBeanConf.class);
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        HierarchicalBeanFactory parentBeanFactory = getBeanFactoryWithXml();
        beanFactory.setParentBeanFactory(parentBeanFactory);
        applicationContext.refresh();
        // 查询父上下文中的bean
        LessonPrototype lessonPrototype = BeanFactoryUtils.beanOfType(beanFactory, LessonPrototype.class);
        System.out.println(lessonPrototype);
        // 查询当前上下文中的bean
        CustomLesson customLesson = BeanFactoryUtils.beanOfType(beanFactory, CustomLesson.class);
        System.out.println(customLesson);
        applicationContext.close();
    }
}
