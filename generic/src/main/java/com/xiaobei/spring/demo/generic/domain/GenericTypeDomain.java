package com.xiaobei.spring.demo.generic.domain;

import com.xiaobei.spring.demo.generic.GenericDemo;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/23 10:59
 */
public class GenericTypeDomain extends GenericMap<Integer, GenericDemo> implements Comparable<Integer>, GenericList<String> {

    @Override
    public int compareTo(Integer o) {
        return 0;
    }
}
