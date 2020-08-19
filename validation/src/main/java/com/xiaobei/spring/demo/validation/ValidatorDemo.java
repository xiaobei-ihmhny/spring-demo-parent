package com.xiaobei.spring.demo.validation;

import com.xiaobei.spring.demo.validation.domain.ValidationDomain;
import org.springframework.context.MessageSource;
import org.springframework.context.support.StaticMessageSource;
import org.springframework.validation.*;

import java.util.Locale;

/**
 * 自定义 {@link Validator} 的实现
 *
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-08-19 12:40:40
 *
 * @see Validator
 * @see Errors
 * @see ValidationUtils
 * @see MessageSource
 *
 */
public class ValidatorDemo {

    /**
     * 运行结果：
     * id不能为空
     * name长度超过限制
     *
     * TODO 这里的 {@link MessageSource} 和 {@link Errors} 的联系总觉得不是很紧密？？？？
     *
     * @param args
     */
    public static void main(String[] args) {
        Validator validator = new ValidationDomainValidator();
        ValidationDomain domain = new ValidationDomain();
        domain.setName("小贝_xiaobei-ihmhny");
        // 创建 Errors 对象
        Errors errors = new BeanPropertyBindingResult(domain, "domain");
        validator.validate(domain, errors);
        MessageSource messageSource = createMessageSource();
        for (ObjectError error : errors.getAllErrors()) {
            String message = messageSource.getMessage(error.getCode(), error.getArguments(), Locale.getDefault());
            System.out.println(message);
        }

    }

    private static MessageSource createMessageSource() {
        StaticMessageSource messageSource = new StaticMessageSource();
        messageSource.addMessage("id.required", Locale.getDefault(), "id不能为空");
        messageSource.addMessage("name.required", Locale.getDefault(), "name不能为空");
        messageSource.addMessage("field.max.length", Locale.getDefault(), "name长度超过限制");
        return messageSource;
    }

    static class ValidationDomainValidator implements Validator {

        private static final int NAME_MAX_LENGTH = 6;

        @Override
        public boolean supports(Class<?> clazz) {
            return ValidationDomain.class.equals(clazz);
        }

        @Override
        public void validate(Object target, Errors errors) {
            ValidationDomain domain = (ValidationDomain) target;
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "id.required");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.required");
            if(domain.getName() != null
                    && domain.getName().trim().length() > NAME_MAX_LENGTH) {
                errors.rejectValue("name", "field.max.length",
                        new Object[]{NAME_MAX_LENGTH},
                        "The name must less then ["+ NAME_MAX_LENGTH +"] characters in length.");
            }
        }
    }

}