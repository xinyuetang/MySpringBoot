package com.fudanuniversity.cms.commons.validation.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 校验字段串是否为指定的枚举类型
 * <p>
 * Created by tidu at 2018-07-23 19:27:47
 */
@Documented
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
public @interface EnumString {
    /**
     * 枚举class
     */
    Class<? extends Enum<?>> value();

    /**
     * 是否区分大小写
     */
    boolean ignoreCase() default false;

    /**
     * 空字符串是否校验通过
     */
    boolean allowEmptyString() default true;

    String message() default "{com.fudanuniversity.cms.commons.validation.constraints.EnumString.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * Defines several annotation constraints on the same element.
     */
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {

        EnumString[] value();
    }
}