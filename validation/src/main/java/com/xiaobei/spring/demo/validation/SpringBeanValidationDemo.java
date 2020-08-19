package com.xiaobei.spring.demo.validation;

import com.xiaobei.spring.demo.validation.domain.ValidationDomain;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-08-19 14:44:44
 */
public class SpringBeanValidationDemo {

    /**
     * TODO 需要详细学习 {@link Validated} 相关东西
     *
     * <h2>运行结果：</h2>
     * Exception in thread "main" javax.validation.ConstraintViolationException: process.domain.name: 不能为null
     * @param args
     */
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext
                = new ClassPathXmlApplicationContext("classpath:/META-INF/bean-validation-context.xml");
        ValidationDomainProcessor processor = applicationContext.getBean(ValidationDomainProcessor.class);
        processor.process(new ValidationDomain());
        // 关闭应用上下文
        applicationContext.close();
    }

    @Component
    @Validated
    static class ValidationDomainProcessor {

        public void process(@Valid ValidationDomain domain) {
            System.out.println(domain);
        }
    }

    static class ValidationDomain {

        @NotNull
        private String name;

        public String getName() {
            return name;
        }

        public ValidationDomain setName(String name) {
            this.name = name;
            return this;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("ValidationDomain{");
            sb.append("name='").append(name).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }
}