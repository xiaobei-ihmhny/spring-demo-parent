package com.xiaobei.spring.demo.ioc.overview.container;

import com.xiaobei.spring.demo.ioc.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

import java.util.Map;

/**
 * 不使用ApplicationContext，只使用BeanFactory时，同样可以使用IoC容器
 *
 * {@link BeanFactory} 作为 IoC容器示例
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-07-31 17:05:05
 */
public class BeanFactoryAsIoCContainerDemo {

    public static void main(String[] args) {
        // 创建BeanFactory容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        // xml配置文件的classpath路径
        String location = "classpath:/META-INF/dependency-injection.xml";
        // 加载配置资源
        int beanDefinitionsCount = reader.loadBeanDefinitions(location);
        System.out.println("读取到的bean的数量：" + beanDefinitionsCount);
        lookupCollectionByTypeInRealTime(beanFactory);
    }

    private static void lookupCollectionByTypeInRealTime(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> users = listableBeanFactory.getBeansOfType(User.class);
            System.out.println("查找到所有的 User 集合对象为：" + users);
        }
    }
}