package com.xiaobei.springmvc.demo.databinder;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder;

import javax.servlet.ServletRequest;
import java.lang.reflect.Field;

/**
 * TODO
 *
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021-01-23 11:06:06
 */
@SuppressWarnings("ConstantConditions")
public class RequestParamBinder extends ExtendedServletRequestDataBinder {

    public RequestParamBinder(Object target) {
        super(target);
    }

    public RequestParamBinder(Object target, String objectName) {
        super(target, objectName);
    }

    @Override
    protected void addBindValues(MutablePropertyValues mpvs, ServletRequest request) {
        super.addBindValues(mpvs, request);
        System.out.println("进行绑定方法.....");
        //处理参数绑定
        Class<?> targetClass = getTarget().getClass();
        // 获取所有的属性
        Field[] fields = targetClass.getDeclaredFields();
        for (Field field : fields) {
            RequestParamName bindParam = field.getAnnotation(RequestParamName.class);
            if(bindParam != null && mpvs.contains(bindParam.name())) {
                if(!mpvs.contains(field.getName())) {
                    mpvs.add(field.getName(), mpvs.getPropertyValue(bindParam.name()).getValue());
                }
            }
        }
    }
}
