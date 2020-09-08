package com.xiaobei.spring.demo.dependency.injection;

import com.xiaobei.spring.demo.dependency.domain.BasicTypeDemo;
import com.xiaobei.spring.demo.dependency.domain.EnumType;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

import java.util.TimeZone;

/**
 * 基础类型注入示例
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-08-05 17:03:03
 */
public class BasicTypeDependencyInjectionDemo {

    /**
     * <h2>运行结果：</h2>
     * BasicTypeDemo{
     *   byteType=126,
     *   basicType=true,
     *   enumType=BEIJING,
     *   resourceLocation=class path resource [META-INF/basic-type.properties],
     *   clazz=class com.xiaobei.spring.demo.dependency.config.AwareConfig
     *   }
     *
     * @see CustomCollectionEditor
     * @see org.springframework.beans.TypeConverterDelegate
     */
    @Test
    public void basicTypeDependencyInjectionByXml() {
        String location = "classpath:/META-INF/basic-type-dependency-injection.xml";
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(location);
        // 启动应用上下文
        applicationContext.start();
        // 依赖查找
        BasicTypeDemo basicTypeDemo = applicationContext.getBean(BasicTypeDemo.class);
        System.out.println(basicTypeDemo);
        applicationContext.close();
    }

    /**
     * <h2>运行结果：</h2>
     * config：
     *  BasicTypeInjectionConfig{
     *   byteType=6,
     *   basicType=true,
     *   enumType=ZHUHAI,
     *   resourceLocation=class path resource [.;D:/javaenv/java/jdk1.8.0_91/lib;D:/javaenv/java/jdk1.8.0_91/lib/tools.jar],
     *   timeZone=sun.util.calendar.ZoneInfo[id="GMT+08:00",offset=28800000,dstSavings=0,useDaylight=false,transitions=0,lastRule=null],
     *   clazz=class com.xiaobei.spring.demo.dependency.injection.BasicTypeDependencyInjectionDemo$BasicTypeInjectionConfig
     * }
     */
    @Test
    public void basicTypeDependencyInjectionByAnnotation() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(BasicTypeInjectionConfig.class);
        applicationContext.refresh();
        BasicTypeInjectionConfig config = applicationContext.getBean(BasicTypeInjectionConfig.class);
        System.out.printf("config：\n %s\n", config);
        applicationContext.close();
    }

    static class BasicTypeInjectionConfig {

        /**
         * 原生类型——byte
         */
        @Value("6")
        private byte byteType;

        /**
         * 标量类型——Boolean
         */
        @Value("true")
        private Boolean basicType;

        /**
         * 标量类型——枚举类型
         */
        @Value("ZHUHAI")
        private EnumType enumType;

        /**
         * Spring类型
         */
        @Value("${classpath:/META-INF/basic-type.properties}")
        private Resource resourceLocation;

        /**
         * 常规类型
         */
        @Value("GMT+8")
        private TimeZone timeZone;

        @Value("com.xiaobei.spring.demo.dependency.injection.BasicTypeDependencyInjectionDemo.BasicTypeInjectionConfig")
        private Class clazz;

        @Override
        public String toString() {
            return "BasicTypeInjectionConfig{" +
                    "  \n  byteType=" + byteType +
                    ", \n  basicType=" + basicType +
                    ", \n  enumType=" + enumType +
                    ", \n  resourceLocation=" + resourceLocation +
                    ", \n  timeZone=" + timeZone +
                    ", \n  clazz=" + clazz +
                    "\n}\n";
        }
    }
}