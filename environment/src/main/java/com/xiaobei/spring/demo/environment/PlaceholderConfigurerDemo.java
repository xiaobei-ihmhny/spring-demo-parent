package com.xiaobei.spring.demo.environment;

import com.xiaobei.spring.demo.environment.domain.UserDomain;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/27 21:40
 */
public class PlaceholderConfigurerDemo {

    /**
     * <h2>运行结果：</h2>
     * UserDomain{id=216, name='Environment占位符处理'}
     */
    @Test
    public void propertyPlaceholderConfigurer() {
        String location = "classpath:/META-INF/property-placeholder-config.xml";
        testPlaceholderConfigurer(location);
    }

    /**
     * <h2>运行结果：</h2>
     * UserDomain{id=216, name='lenovo'}
     *
     * TODO 此处的 {@code ${user.name}} 取的并不是属性配置文件中的，而是系统的用户名，此处是存在优先级的！！
     *
     */
    @Test
    public void propertySourcesPlaceholderConfigurer() {
        String location = "classpath:/META-INF/property-sources-placeholder-config.xml";
        testPlaceholderConfigurer(location);
    }

    private void testPlaceholderConfigurer(String location) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext();
        applicationContext.setConfigLocation(location);
        applicationContext.refresh();
        // 依赖查找
        UserDomain userDomain = applicationContext.getBean("userDomain", UserDomain.class);
        System.out.println(userDomain);
        // 关闭应用上下文
        applicationContext.close();
    }
}
