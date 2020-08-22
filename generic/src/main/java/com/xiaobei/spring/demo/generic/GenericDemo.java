package com.xiaobei.spring.demo.generic;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/22 23:15
 */
public class GenericDemo {

    /**
     * <h2>运行结果：</h2>
     * temp 和 collection 是同一个对象：true
     * [xiaobei, natie, 1]
     * @param args
     */
    public static void main(String[] args) {
        // Java 7 Diamond 语法
        Collection<String> collection = new ArrayList<>();
        collection.add("xiaobei");
        collection.add("natie");
        // 编译时错误
        // collection.add(1);
        // 泛型擦写
        Collection temp = collection;
        // 编译通过
        temp.add(1);
        System.out.println("temp 和 collection 是同一个对象：" + (temp == collection));
        System.out.println(temp);
    }
}
