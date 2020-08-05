package com.xiaobei.spring.demo.dependency.injection;

import com.xiaobei.spring.demo.dependency.domain.BasicTypeDemo;
import com.xiaobei.spring.demo.dependency.domain.CollectionTypeDemo;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/5 20:15
 */
public class CollectionTypeDependencyInjectioinDemo {

    /**
     * 示例集合类型的注入
     * TODO {@link List<Boolean>} 的注入不能使用","号来分隔？？
     * TODO {@link List<? extends Enum>} 的注入不能使用","号来分隔？？
     * CollectionTypeDemo{
     *   byteArray=[97, 98, 99, 100, 101, 102, 103],
     *   booleanList=[true, false, true],
     *   enumTypeList=[ZHUHAI, BEIJING],
     *   enumTypeArray=[BEIJING, ZHUHAI],
     *   properties={1=name,2:age},
     *   clazzSortedSet=[com.xiaobei.spring.demo.dependency.domain.User, java.lang.Math]
     * }
     */
    @Test
    public void collectionDependencyInjectionByXml() {
        String location = "classpath:/META-INF/collection-type-dependency-injection.xml";
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(location);
        // 启动应用上下文
        applicationContext.start();
        // 依赖查找
        CollectionTypeDemo collectionTypeDemo = applicationContext.getBean(CollectionTypeDemo.class);
        System.out.println(collectionTypeDemo);
        applicationContext.close();
    }
}
