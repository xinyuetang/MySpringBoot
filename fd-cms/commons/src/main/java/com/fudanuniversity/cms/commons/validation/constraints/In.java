package com.fudanuniversity.cms.commons.validation.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 判断校验的字段是否在某个取值列表中
 * <p>
 * Created by tidu at 2018-07-23 19:27:47
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {})
public @interface In {

    /**
     * String数组取值列表，如果校验的字段为数组或其他，则转成字符串作比较
     */
    String[] value();

    /**
     * 空字符串是否校验通过
     */
    boolean allowEmptyString() default true;

    String message() default "{com.fudanuniversity.cms.commons.validation.constraints.In.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * Defines several annotation constraints on the same element.
     */
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {

        In[] value();
    }
}