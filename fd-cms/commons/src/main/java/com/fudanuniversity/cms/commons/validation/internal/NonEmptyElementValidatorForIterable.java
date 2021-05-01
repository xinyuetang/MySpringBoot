package com.fudanuniversity.cms.commons.validation.internal;

import com.fudanuniversity.cms.commons.validation.constraints.NonEmptyElement;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by tidu at 2018-07-23 19:27:47
 */
public class NonEmptyElementValidatorForIterable
        implements ConstraintValidator<NonEmptyElement, Iterable<?>> {

    private boolean allowEmptyString;

    @Override
    public void initialize(NonEmptyElement annotation) {
        allowEmptyString = annotation.allowEmptyString();
    }

    @Override
    public boolean isValid(
            Iterable<?> iterable, ConstraintValidatorContext ctx) {
        if (iterable == null) {
            return true;
        }

        for (Object obj : iterable) {
            if (obj == null
                    || (allowEmptyString && "".equals(obj))) {
                return false;
            }
        }

        return true;
    }

}
