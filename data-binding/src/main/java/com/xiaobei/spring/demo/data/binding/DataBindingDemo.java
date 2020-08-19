package com.xiaobei.spring.demo.data.binding;

import com.xiaobei.spring.demo.data.binding.domain.City;
import com.xiaobei.spring.demo.data.binding.domain.DataBindingDomain;
import org.junit.Test;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/19 22:36
 */
public class DataBindingDemo {

    /**
     * 当 PropertyValues 中包含名称 x 的 PropertyValue，
     * 目标对象 B 存在 x 属性，当 bind 方法执行时的情况：
     *
     * <h2>运行结果：</h2>
     * DataBindingDomain{id=153, name='DataBinding绑定', city=null}
     */
    @Test
    public void bindingOneToOne() {
        processBinding(propertiesMap -> {
            // id 对应 domain 中的 id 属性
            propertiesMap.put("id", 153L);
            // name 对应 domain 中的 name 属性
            propertiesMap.put("name", "DataBinding绑定");
        });
    }

    /**
     * 此处涉及到 {@link DataBinder} 框架内部创建对象 {@link City}并绑定的过程
     * <p>
     * 当 PropertyValues 中包含名称 x.y 的 PropertyValue，
     * 目标对象 B 存在 x 属性（嵌套 y 属性），当 bind 方法执行时，会发生什么？
     *
     * <h2>运行结果：</h2>
     * DataBindingDomain{id=153, name='DataBinding绑定', city=City{name='北京'}}
     */
    @Test
    public void bindingOneToOneIncludeNested() {
        processBinding(propertiesMap -> {
            // id 对应 domain 中的 id 属性
            propertiesMap.put("id", 153L);
            // name 对应 domain 中的 name 属性
            propertiesMap.put("name", "DataBinding绑定");
            // 嵌套属性 city 对应 domain中的 city
            propertiesMap.put("city.name", "北京");
        });
    }

    /**
     * <h2>运行结果：</h2>
     * DataBindingDomain{id=153, name='DataBinding绑定', city=null}
     * <p>
     * 当 PropertyValues 中包含名称 x 的 PropertyValue，
     * 目标对象 B 不存在 x 属性，当 bind 方法执行时，会发生什么？
     *
     * <h2>答案：</h2>
     * 不存在的属性会直接忽略！！！
     */
    @Test
    public void bindingIncludeNoField() {
        processBinding(propertiesMap -> {
            // id 对应 domain 中的 id 属性
            propertiesMap.put("id", 153L);
            // name 对应 domain 中的 name 属性
            propertiesMap.put("name", "DataBinding绑定");
            // address 不对应 domain 中的任何属性
            propertiesMap.put("address", "北京天安门");
        });
    }

    /**
     * 绑定参数调整——强绑定
     * <p>
     * ignoreUnknownFields	是否忽略未知字段，默认值：true
     * ignoreInvalidFields	是否忽略非法字段，默认值：false
     * autoGrowNestedPaths	是否自动增加嵌套路径，默认值：true
     *
     * <h2>运行结果：</h2>
     * org.springframework.beans.NotWritablePropertyException:
     * Invalid property 'address' of bean class [com.xiaobei.spring.demo.data.binding.domain.DataBindingDomain]:
     * Bean property 'address' is not writable or has an invalid setter method.
     * Does the parameter type of the setter match the return type of the getter
     */
    @Test
    public void bindingParameterAdjustOfStrong() {
        processBinding(propertiesMap -> {
            // id 对应 domain 中的 id 属性
            propertiesMap.put("id", 153L);
            // name 对应 domain 中的 name 属性
            propertiesMap.put("name", "DataBinding绑定");
            // address 不对应 domain 中的任何属性
            propertiesMap.put("address", "北京天安门");
        }, dataBinder -> {
            // 取消忽略未知属性，当存在未知属性时，直接抛出异常
            dataBinder.setIgnoreUnknownFields(false);
        });
    }

    /**
     * {@link DataBinder#setAutoGrowNestedPaths(boolean)} 设置为 {@code false} 之后，框架不会自动创建嵌套对象
     *
     * <h2>运行结果：</h2>
     * org.springframework.beans.NullValueInNestedPathException:
     * Invalid property 'city' of bean class [com.xiaobei.spring.demo.data.binding.domain.DataBindingDomain]:
     * Value of nested property 'city' is null
     */
    @Test
    public void bindingParameterAdjustOfStrong2() {
        processBinding(propertiesMap -> {
            // id 对应 domain 中的 id 属性
            propertiesMap.put("id", 153L);
            // name 对应 domain 中的 name 属性
            propertiesMap.put("name", "DataBinding绑定");
            // 嵌套属性 city 对应 domain中的 city
            propertiesMap.put("city.name", "北京");
        }, dataBinder -> {
            // 取消忽略未知属性，当存在未知属性时，直接抛出异常
            dataBinder.setAutoGrowNestedPaths(false);
        });
    }

    /**
     * 绑定黑名单，黑名单上的数据将不会被绑定
     */
    @Test
    public void bindingParameterAdjustOfWeak() {
        DataBinder result = processBinding(
                propertiesMap -> {
                    // id 对应 domain 中的 id 属性
                    propertiesMap.put("id", 153L);
                    // name 对应 domain 中的 name 属性
                    propertiesMap.put("name", "DataBinding绑定");
                    // address 不对应 domain 中的任何属性
                    propertiesMap.put("address", "北京天安门");
                }, dataBinder -> {
                    // 将 name 属性放入黑名单中，则 name 属性将不会被绑定
                    dataBinder.setDisallowedFields("name");
                });
        System.out.println(result.getBindingResult());
    }

    /**
     * 当绑定的字段没有绑定指定时，不会抛出异常，相关的错误信息会放入绑定结果：{@link BindingResult} 中
     *
     * <h2>运行结果</h2>
     * DataBindingDomain{id=153, name='null', city=null}
     * org.springframework.validation.BeanPropertyBindingResult: 1 errors
     * Field error in object 'domain' on field 'name': rejected value [];
     * codes [required.domain.name,required.name,required.java.lang.String,required];
     * arguments [org.springframework.context.support.DefaultMessageSourceResolvable:
     * codes [domain.name,name]; arguments []; default message [name]]; default message [Field 'name' is required]
     */
    @Test
    public void bindingParameterAdjustOfWeak2() {
        DataBinder result = processBinding(
                propertiesMap -> {
                    // id 对应 domain 中的 id 属性
                    propertiesMap.put("id", 153L);
                    // address 不对应 domain 中的任何属性
                    propertiesMap.put("address", "北京天安门");
                }, dataBinder -> {
                    // 将 name 属性放入黑名单中，则 name 属性将不会被绑定
                    dataBinder.setRequiredFields("name");
                });
        System.out.println(result.getBindingResult());
    }


    /**
     * 处理Binding
     *
     * @param propertiesMapConsumer
     */
    private void processBinding(Consumer<Map<String, Object>> propertiesMapConsumer) {
        processBinding(propertiesMapConsumer, binderConsumer -> {
        });
    }

    /**
     * 处理Binding
     *
     * @param propertiesMapConsumer
     * @param binderConsumer
     * @return
     */
    private DataBinder processBinding(Consumer<Map<String, Object>> propertiesMapConsumer, Consumer<DataBinder> binderConsumer) {
        DataBindingDomain domain = new DataBindingDomain();
        DataBinder binder = new DataBinder(domain, "domain");
        Map<String, Object> propertiesMap = new HashMap<>();
        propertiesMapConsumer.accept(propertiesMap);
        PropertyValues propertyValues = new MutablePropertyValues(propertiesMap);
        binderConsumer.accept(binder);
        binder.bind(propertyValues);
        System.out.println(domain);
        return binder;
    }
}
