package com.xiaobei.spring.demo.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.ResolvableType;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 层次性 Spring 事件传播示例
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-08-24 09:43:43
 */
public class HierarchicalApplicationEventPropagateDemo {

    /**
     * <h2>运行结果：</h2>
     * 收到应用上下文 [parent-context] 的事件
     * 收到应用上下文 [current-context] 的事件
     *
     * <p>
     * 在层次性 Spring 应用上下文中当前应用上下文中的事件会传播到其父应用上下文，直到Root应用上下文
     *
     * {@link AbstractApplicationContext#refresh()} 方法中的 {@code finishRefresh();}
     * {@link AbstractApplicationContext#finishRefresh()} 发布事件 {@code publishEvent(new ContextRefreshedEvent(this));}
     * {@link AbstractApplicationContext#publishEvent(ApplicationEvent)}
     * {@link AbstractApplicationContext#publishEvent(java.lang.Object, ResolvableType)}
     * 其中的 {@code if (this.parent != null) {}
     * @param args
     */
    public static void main(String[] args) {
        // 1. 定义父应用上下文
        AnnotationConfigApplicationContext parentContext = new AnnotationConfigApplicationContext();
        parentContext.setId("parent-context");
        parentContext.register(MyApplicationListener.class);
        // 2. 定义当前应用上下文
        AnnotationConfigApplicationContext currentContext = new AnnotationConfigApplicationContext();
        currentContext.setId("current-context");
        // 3. 关联父子应用上下文
        currentContext.setParent(parentContext);
        // 4. 注册 ApplicationEvent 事件
        currentContext.register(MyApplicationListener.class);
        // 启动 parent 应用上下文
        parentContext.refresh();
        // 启动当前应用上下文
        currentContext.refresh();

        currentContext.close();
        parentContext.close();
    }

    static class MyApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

        /**
         * 添加过滤条件，确保同一个事件只能被触发一次
         */
        private static Set<ApplicationEvent> processed = new LinkedHashSet<>();

        @Override
        public void onApplicationEvent(ContextRefreshedEvent event) {
            if(processed.add(event)) {
                System.out.printf("收到应用上下文 [%s] 的事件\n", event.getApplicationContext().getId());
            }
        }
    }

}