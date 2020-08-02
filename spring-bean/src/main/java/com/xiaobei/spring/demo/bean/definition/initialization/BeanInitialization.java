package com.xiaobei.spring.demo.bean.definition.initialization;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/2 7:37
 */
public class BeanInitialization {

    /**
     * 自定义初始化方法——xml方法
     */
    public void initBeanMethodWithXml() {
        System.out.println("自定义初始化方法：XML配置：<bean init-method=\"init\" .../>");
    }

    /**
     * 自定义初始化方法——注解方式
     */
    public void initBeanMethodWithAnnotation() {
        System.out.println("自定义初始化方法：Java注解：@Bean(initMethod=\"init\")");
    }

    /**
     * 自定义初始化方法——java api
     */
    public void initBeanMethodByApi() {
        System.out.println("自定义初始化方法：通过java api的方式完成初始化");
    }


}
