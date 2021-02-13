package com.xiaobei.spring.aop.demo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK 动态代理生成字节码过程
 * @author <a href="mailto:legend0508@163.com">xiaobei-ihmhny</a>
 * @date 2021-02-13 19:56:56
 */
public class JdkDynamicProxyDemo {

    /**
     * <ul>其中 {@link Proxy#newProxyInstance(ClassLoader, Class[], InvocationHandler) Proxy.newProxyInstance}
     * 生成字节码的流程如下：</ul>
     *
     * <li>{@link Proxy#newProxyInstance(ClassLoader, Class[], InvocationHandler)}</li>
     * <li>{@code Class<?> cl = getProxyClass0(loader, intfs);}</li>
     * <li> {@link Proxy#getProxyClass0(ClassLoader, Class[])}</li>
     * <li>   {@code return proxyClassCache.get(loader, interfaces);}</li>
     * <li>    {@link java.lang.reflect.WeakCache#get(Object, Object)}</li>
     * <li>     {@code V value = supplier.get();}</li>
     * <li>      {@link java.lang.reflect.WeakCache.Factory#get()}</li>
     * <li>       {@code value = Objects.requireNonNull(valueFactory.apply(key, parameter));}</li>
     * <li>        {@link Proxy.ProxyClassFactory#apply(ClassLoader, Class[])}</li>
     * <li>         {@code interfaceClass = Class.forName(intf.getName(), false, loader);} 重新加载class</li>
     * <li>         {@code String proxyName = proxyPkg + proxyClassNamePrefix + num;} 拼接动态生成的类的名称：com.sun.proxy.$Proxy0</li>
     * <li>         {@code byte[] proxyClassFile = ProxyGenerator.generateProxyClass(proxyName, interfaces, accessFlags);} 生成类的字节码信息</li>
     * <li>         {@code return defineClass0(loader, proxyName,proxyClassFile, 0, proxyClassFile.length);} 调用 native 方法生成最终的代理类</li>
     * @param args
     */
    public static void main(String[] args) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Object object = Proxy.newProxyInstance(loader, new Class[]{EchoService.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return null;
            }
        });
    }
}
