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

    /**
     * 在所有上下文中是否包含指定名称的bean
     * @param beanFactory
     * @param beanName
     */
    private void displayContainsBean(HierarchicalBeanFactory beanFactory, String beanName) {
        System.out.printf("当前BeanFactory[%s]是否包含bean[%s] : %s\n", beanFactory, beanName,
                containsBean(beanFactory, beanName));
    }

    /**
     * 递归查找所有上下文中是否存在指定名称的bean
     * @param beanFactory
     * @param beanName
     * @return
     */
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
        Lesson english = getBean(beanFactory, "english", Lesson.class);
        System.out.println(english);
        applicationContext.close();
    }

    /**
     * <h2>步骤如下：</h2>
     * <p> 1. 判断当前应用上下文中是否包含指定名称的bean
     * <p> 1.2. 如果包含，直接返回
     * <p> 1.3. 如果不包含，判断父应用上下文是否属于层次性应用上下文
     * <p> 1.3.1. 如果是，就把父应用上下文转换为层次性应用上下文
     * <p> 1.3.2. 递归执行步骤 1
     * <p> 1.4. 如果不包含且父应用上下文也不属于层次性应用上下文，直接返回null
     * @param beanFactory 当前的层次性应用上下文
     * @param beanName 指定 bean 的名称
     * @param requiredType 指定 bean 的类型
     * @param <T>
     * @return
     */
    <T> T getBean(HierarchicalBeanFactory beanFactory, String beanName, Class<T> requiredType) {
        // 从当前应用上下文中获取指定的bean
        if(beanFactory.containsLocalBean(beanName)) {
            return beanFactory.getBean(beanName, requiredType);
        }
        if(beanFactory.getParentBeanFactory() instanceof HierarchicalBeanFactory) {
            HierarchicalBeanFactory parentHierarchicalBeanFactory = (HierarchicalBeanFactory) beanFactory.getParentBeanFactory();
            return getBean(parentHierarchicalBeanFactory, beanName, requiredType);
        }
        return null;
    }

    /**
     * 使用 {@link BeanFactoryUtils#beanOfTypeIncludingAncestors(ListableBeanFactory, java.lang.Class)}
     * 在所有上下文中查询指定类型的bean。若父类及子类中同时含有，则会返回子类中的bean
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
        // 查询当前上下文中的bean，直接查到，不再查询父应用上下文
        CustomLesson customLesson = BeanFactoryUtils.beanOfTypeIncludingAncestors(beanFactory, CustomLesson.class);
        System.out.println(customLesson);
        // 先查询当前应用上下文，发现没有相应的bean，再查询父上下文中的bean，找到，直接返回
        LessonPrototype lessonPrototype = BeanFactoryUtils.beanOfTypeIncludingAncestors(beanFactory, LessonPrototype.class);
        System.out.println(lessonPrototype);
        applicationContext.close();
    }
    /**
     * <p>这个也可以看成是一种“双亲委派”，只是它和classloader的“双亲委派”是相反的。
     *
     * 根据Bean类型查找实例列表
     * 使用{@link BeanFactoryUtils#beansOfTypeIncludingAncestors(ListableBeanFactory, java.lang.Class)}
     * 查找所有应用上下文中含有的指定类型的bean实例集合
     */
    @Test
    public void getBeansOfTypeIncludingAncestors() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ListableBeanConf.class);
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        HierarchicalBeanFactory parentBeanFactory = getBeanFactoryWithXml();
        beanFactory.setParentBeanFactory(parentBeanFactory);
        applicationContext.refresh();
        // 查询所有应用上下文中的bean，父子应用上下文中同时含有的，父应用上下文中的bean会被覆盖
        Map<String, Lesson> lessonMap = BeanFactoryUtils.beansOfTypeIncludingAncestors(beanFactory, Lesson.class);
        System.out.println(lessonMap);
        applicationContext.close();
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
     * TODO 如果父、子应用上下文中含有同类型、同名称的bean，但只有父上下文中存在注解标记，那需要查出来吗？目前的逻辑是查不出来的！
     * 使用{@link BeanFactoryUtils#beanNamesForAnnotationIncludingAncestors(ListableBeanFactory, java.lang.Class)}
     * 来获取所有应用上下文中含有指定注解标记的bean
     */
    @Test
    public void getBeanNamesForAnnotationIncludingAncestors() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ListableBeanConf.class);
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        HierarchicalBeanFactory parentBeanFactory = getBeanFactoryWithAnnotation();
        beanFactory.setParentBeanFactory(parentBeanFactory);
        applicationContext.refresh();
        // 查询所有应用上下文中的含有@MyLesson标记的bean
        String[] beanNames = BeanFactoryUtils.beanNamesForAnnotationIncludingAncestors(beanFactory, MyLesson.class);
        // 自已调整后的逻辑，当父
        String[] myBeanNames = beanNamesForAnnotationIncludingAncestors(beanFactory, MyLesson.class);
        System.out.println(Arrays.toString(beanNames));
        System.out.println(Arrays.toString(myBeanNames));
        applicationContext.close();
    }

    /**
     * 逻辑调整为：如果父、子应用上下文中含有同类型、同名称的bean，
     * 但只有父上下文中存在注解标记，那将父应用上下文也查出来
     * @param lbf
     * @param annotationType
     * @return
     */
    public static String[] beanNamesForAnnotationIncludingAncestors(
            ListableBeanFactory lbf, Class<? extends Annotation> annotationType) {

        Assert.notNull(lbf, "ListableBeanFactory must not be null");
        String[] result = lbf.getBeanNamesForAnnotation(annotationType);
        if (lbf instanceof HierarchicalBeanFactory) {
            HierarchicalBeanFactory hbf = (HierarchicalBeanFactory) lbf;
            if (hbf.getParentBeanFactory() instanceof ListableBeanFactory) {
                String[] parentResult = beanNamesForAnnotationIncludingAncestors(
                        (ListableBeanFactory) hbf.getParentBeanFactory(), annotationType);
                result = mergeNamesIncludeAnnotationWithParent(result, parentResult, hbf, annotationType);
            }
        }
        return result;
    }

    private static String[] mergeNamesIncludeAnnotationWithParent(String[] result,
                                                                  String[] parentResult,
                                                                  HierarchicalBeanFactory hbf,
                                                                  Class<? extends Annotation> annotationType) {
        if (parentResult.length == 0) {
            return result;
        }
        List<String> merged = new ArrayList<>(result.length + parentResult.length);
        merged.addAll(Arrays.asList(result));
        for (String beanName : parentResult) {
            if (!merged.contains(beanName) && (!hbf.containsLocalBean(beanName)
                    || (hbf instanceof ListableBeanFactory
                    && ((ListableBeanFactory) hbf).findAnnotationOnBean(beanName, annotationType) == null))) {
                merged.add(beanName);
            }
        }
        return StringUtils.toStringArray(merged);
    }

}
