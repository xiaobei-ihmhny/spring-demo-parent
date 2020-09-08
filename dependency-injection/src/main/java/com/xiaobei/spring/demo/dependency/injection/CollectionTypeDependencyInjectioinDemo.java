package com.xiaobei.spring.demo.dependency.injection;

import com.xiaobei.spring.demo.dependency.conversion.BooleanArrayEditor;
import com.xiaobei.spring.demo.dependency.conversion.BooleanArrayEditorRegistrar;
import com.xiaobei.spring.demo.dependency.domain.CollectionTypeDemo;
import com.xiaobei.spring.demo.dependency.domain.EnumType;
import org.junit.Test;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.convert.TypeDescriptor;

import java.beans.PropertyEditorSupport;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.SortedSet;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/5 20:15
 */
public class CollectionTypeDependencyInjectioinDemo {

    /**
     * 示例集合类型的注入
     * TODO {@link List<Boolean>} 的注入不能使用","号来分隔，需要自行实现相应的转换器！！
     * CollectionTypeDemo{
     *   byteArray=[97, 98, 99, 100, 101, 102, 103],
     *   booleanList=[true, false, true],
     *   enumTypeList=[BEIJING, ZHUHAI],
     *   enumTypeArray=[BEIJING, ZHUHAI],
     *   properties={1=name,2:age},
     *   clazzSortedSet=[com.xiaobei.spring.demo.dependency.domain.User, java.lang.Math]
     *   }
     * @see CustomCollectionEditor#setAsText(java.lang.String)
     * @see BooleanArrayEditor#setAsText(java.lang.String)
     * @see PropertyEditorRegistrar
     * @see PropertyEditorRegistry#registerCustomEditor(java.lang.Class, java.beans.PropertyEditor)
     * @see org.springframework.beans.TypeConverterDelegate#convertIfNecessary(String, Object, Object, Class, TypeDescriptor)
     * @see PropertyEditorSupport
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

    /**
     * <h2>运行结果：</h2>
     * config
     * ：CollectionDependencyInjectionConfig{
     *   byteArray=[49, 44, 50, 44, 51],
     *   enumTypeArray=[BEIJING, ZHUHAI],
     *   booleanList=[true, false, true],
     *   enumTypeList=[BEIJING, ZHUHAI],
     *   clazzSortedSet=[ java.lang.Math, com.xiaobei.spring.demo.dependency.domain.User],
     *   properties={1=name,2:age}
     *   }
     * @see CustomCollectionEditor#setAsText(java.lang.String)
     * @see BooleanArrayEditor#setAsText(java.lang.String)
     * @see PropertyEditorRegistrar
     * @see PropertyEditorRegistry#registerCustomEditor(java.lang.Class, java.beans.PropertyEditor)
     * @see org.springframework.beans.TypeConverterDelegate#convertIfNecessary(String, Object, Object, Class, TypeDescriptor)
     * @see PropertyEditorSupport
     */
    @Test
    public void collectionDependencyInjectionWithAnnotation() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(CollectionDependencyInjectionConfig.class);
        applicationContext.refresh();
        CollectionDependencyInjectionConfig config = applicationContext.getBean(CollectionDependencyInjectionConfig.class);
        System.out.printf("config \n：%s\n", config);
        applicationContext.close();
    }

    @Configuration
    static class CollectionDependencyInjectionConfig {

        /**
         * 原生类型——byte
         */
        @Value("1,2,3")
        private byte[] byteArray;

        /**
         * 原生类型
         */
        @Value("BEIJING, ZHUHAI")
        private EnumType[] enumTypeArray;

        /**
         * 集合类型——Boolean
         */
        @Value("true, false, true")
        private List<Boolean> booleanList;

        /**
         * 集合类型——枚举类型
         */
        @Value("BEIJING, ZHUHAI")
        private List<EnumType> enumTypeList;

        /**
         * 集合类型- Set
         */
        @Value("com.xiaobei.spring.demo.dependency.domain.User, java.lang.Math")
        private SortedSet<Class> clazzSortedSet;

        /**
         * Spring类型
         */
        @Value("1=name,2:age")
        private Properties properties;

        //===================== 配置自定义转换器转换 List<Boolean> 类型 =====================//
        @Bean
        public static BooleanArrayEditor booleanArrayEditor() {
            return new BooleanArrayEditor(List.class);
        }

        @Bean
        public static BooleanArrayEditorRegistrar registrar() {
            return new BooleanArrayEditorRegistrar(booleanArrayEditor());
        }

        @Bean
        public static CustomEditorConfigurer customEditorConfigurer() {
            CustomEditorConfigurer customEditorConfigurer = new CustomEditorConfigurer();
            customEditorConfigurer.setPropertyEditorRegistrars(new PropertyEditorRegistrar[]{registrar()});
            return customEditorConfigurer;
        }
        //===================== 配置自定义转换器转换 List<Boolean> 类型 =====================//

        @Override
        public String toString() {
            return "CollectionDependencyInjectionConfig{" +
                    "\n  byteArray=" + Arrays.toString(byteArray) +
                    ", \n  enumTypeArray=" + Arrays.toString(enumTypeArray) +
                    ", \n  booleanList=" + booleanList +
                    ", \n  enumTypeList=" + enumTypeList +
                    ", \n  clazzSortedSet=" + clazzSortedSet +
                    ", \n  properties=" + properties +
                    "\n  }\n";
        }
    }
}
