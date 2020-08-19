package com.xiaobei.spring.demo.validation;

import com.xiaobei.spring.demo.validation.domain.ValidationDomain;
import org.springframework.context.MessageSource;
import org.springframework.context.support.StaticMessageSource;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * {@link Errors} 使用示例
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-08-19 10:28:28
 * @see Errors
 * @see MessageSource
 * @see StaticMessageSource
 * @see BeanPropertyBindingResult
 */
public class ErrorsMessageDemo {


    /**
     * <h2>运行结果：</h2>
     * errors.getGlobalErrors()结果为：[Error in object 'domain': codes [domain.properties.not.null.domain,domain.properties.not.null]; arguments []; default message [null]]
     * errors.getFieldErrors()结果为：[Field error in object 'domain' on field 'id': rejected value [null]; codes [id.required.domain.id,id.required.id,id.required.java.lang.Long,id.required]; arguments []; default message [null], Field error in object 'domain' on field 'name': rejected value [null]; codes [name.required.domain.name,name.required.name,name.required.java.lang.String,name.required]; arguments []; default message [null]]
     * errors.getAllErrors()结果为：[Error in object 'domain': codes [domain.properties.not.null.domain,domain.properties.not.null]; arguments []; default message [null], Field error in object 'domain' on field 'id': rejected value [null]; codes [id.required.domain.id,id.required.id,id.required.java.lang.Long,id.required]; arguments []; default message [null], Field error in object 'domain' on field 'name': rejected value [null]; codes [name.required.domain.name,name.required.name,name.required.java.lang.String,name.required]; arguments []; default message [null]]
     * Domain不能为空
     * the id of Domain must not be null
     * the name of Domain must not be null
     *
     *
     * @param args
     */
    public static void main(String[] args) {
        ValidationDomain domain = new ValidationDomain();
        // 1. 选择 Errors —— Bean
        Errors errors = new BeanPropertyBindingResult(domain, "domain");
        // 2. 调用reject 或 rejectValue
        // reject 生成 ObjectError
        // rejectValue 生成 FieldError
        errors.reject("domain.properties.not.null");
        errors.rejectValue("id", "id.required");
        errors.rejectValue("name", "name.required");

        // 3. 获取 Errors 中的 ObjectError 和 FieldError
        // FieldError is ObjectError
        List<ObjectError> globalErrors = errors.getGlobalErrors();
        List<FieldError> fieldErrors = errors.getFieldErrors();
        List<ObjectError> allErrors = errors.getAllErrors();
        System.out.printf("errors.getGlobalErrors()结果为：%s\n", globalErrors);
        System.out.printf("errors.getFieldErrors()结果为：%s\n", fieldErrors);
        System.out.printf("errors.getAllErrors()结果为：%s\n", allErrors);

        // 4. 通过 ObjectError 和 FieldError 中的 code 和 args 来关联 MessageSource 实现
        MessageSource messageSource = createMessageSource();

        allErrors.forEach(error -> {
            String message = messageSource.getMessage(Objects.requireNonNull(error.getCode()), error.getArguments(), Locale.getDefault());
            System.out.println(message);
        });
    }

    private static MessageSource createMessageSource() {
        StaticMessageSource messageSource = new StaticMessageSource();
        messageSource.addMessage("domain.properties.not.null", Locale.getDefault(), "Domain不能为空");
        messageSource.addMessage("id.required", Locale.getDefault(), "the id of Domain must not be null");
        messageSource.addMessage("name.required", Locale.getDefault(), "the name of Domain must not be null");
        return messageSource;
    }
}