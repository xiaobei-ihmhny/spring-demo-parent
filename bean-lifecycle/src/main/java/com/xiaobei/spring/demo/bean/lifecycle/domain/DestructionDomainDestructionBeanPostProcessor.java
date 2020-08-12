package com.xiaobei.spring.demo.bean.lifecycle.domain;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.util.ObjectUtils;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/12 20:32
 */
public class DestructionDomainDestructionBeanPostProcessor implements DestructionAwareBeanPostProcessor {

    /**
     * Spring Bean 销毁前阶段的回调
     * @param bean
     * @param beanName
     * @throws BeansException
     */
    @Override
    public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {
        // 拦截 id="lifeCycleDomain" 的 bean，并做一定的修改
        if(ObjectUtils.nullSafeEquals("destructionDomain", beanName)
                && DestructionDomain.class.equals(bean.getClass())) {
            System.out.println("开始执行 lifeCycleDomain在销毁前的回调...");
            // 修改相应bean中的信息
            DestructionDomain domain = (DestructionDomain) bean;
            // 将bean中的id属性设置为98L
            domain.setId(102L);
        }
    }
}
