package com.xiaobei.spring.demo.generic.domain;

import com.xiaobei.spring.demo.generic.GenericDemo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/23 15:34
 */
public class ResolvableTypeDomain extends GenericMap<Integer, GenericDemo> implements Comparable<Integer>, GenericList<String> {

    private GenericMap<Integer, List<String>> genericMap;

    private GenericList<List<Map<String, Integer>>> list;

    private List<String> list2;

    public ResolvableTypeDomain(GenericMap<Integer, List<String>> genericMap,
                                GenericList<List<Map<String, Integer>>> list,
                                List<String> list2) {
        this.genericMap = genericMap;
        this.list = list;
        this.list2 = list2;
    }

    public GenericMap<String, Boolean> genericMap() {
        return null;
    }

    public GenericList<List<String>> genericList() {
        return null;
    }

    public GenericMap<String, Boolean> genericMap(List<Map<String, BigDecimal>> list, String name) {
        return null;
    }

    public GenericList<List<String>> genericList(GenericMap<Double, Float> genericMap) {
        return null;
    }


    @Override
    public int compareTo(Integer o) {
        return 0;
    }
}
