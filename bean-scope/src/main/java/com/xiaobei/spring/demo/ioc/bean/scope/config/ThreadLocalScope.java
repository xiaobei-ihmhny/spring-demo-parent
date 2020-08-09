package com.xiaobei.spring.demo.ioc.bean.scope.config;

import com.xiaobei.spring.demo.ioc.bean.scope.domain.ScopeDomain;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.core.NamedThreadLocal;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO 为什么要用 {@link NamedThreadLocal}来包装{@link Map<String, Object>}对象呢？？
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/9 9:21
 */
public class ThreadLocalScope implements Scope {

    public static final String SCOPE_NAME = "thread-local";

//    private NamedThreadLocal<Map<String, Object>> threadLocal
//            = new NamedThreadLocal<Map<String, Object>>("thread-local-scope"){
//        @Override
//        protected Map<String, Object> initialValue() {
//            return new HashMap<>();
//        }
//    };
    private Map<String ,Object> threadLocal = new HashMap<>(8);


    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        Object result = threadLocal.get(name);
        if(result == null) {
            Object object = objectFactory.getObject();
            threadLocal.put(name, object);
            return object;
        }
        return result;
    }

    @Override
    public Object remove(String name) {
        return threadLocal.remove(name);
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {
        // TODO
    }

    @Override
    public Object resolveContextualObject(String key) {
        return threadLocal.get(key);
    }

    @Override
    public String getConversationId() {
        return String.valueOf(Thread.currentThread().getId());
    }
}
