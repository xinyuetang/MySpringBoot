package com.fudanuniversity.cms.framework.validation;

import com.fudanuniversity.cms.framework.aop.InheritedAnnotationMethodPointcut;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.AbstractAdvisingBeanPostProcessor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * <pre>
 *  校验每个Controller执行那个的方法参数、返回值等
 *
 *  参照配置：
 *     &lt;bean name="defaultValidator" class="org.springframework.validation.beanvalidation.LocalValidatorFactoryBean"/&gt;
 *     &lt;!-- spring-webmvc框架默认不做参数校验(可以通过DataBinder添加自定义校验器)--&gt;
 *     &lt;bean name="noOpValidator" class="com.fudanuniversity.cms.commons.web.validation.NoOpValidator"/&gt;
 *     &lt;!-- 参数校验统一由自定义校验拦截器处理 --&gt;
 *     &lt;bean name="mvcValidatorPostProcessor"
 *           class="com.fudanuniversity.cms.commons.web.validation.ControllerValidationPostProcessor"&gt;
 *         &lt;property name="validator" ref="defaultValidator"/&gt;
 *     &lt;/bean&gt;
 *
 *     &lt;mvc:annotation-driven
 *             content-negotiation-manager="contentNegotiationManager" validator="noOpValidator"&gt;
 *     &lt;/mvc:annotation-driven&gt;
 * </pre>
 * <p>
 * Created by tidu at 2018-07-23 19:27:47
 *
 * @see org.springframework.validation.beanvalidation.MethodValidationPostProcessor
 */
public class ControllerValidationPostProcessor
        extends AbstractAdvisingBeanPostProcessor implements InitializingBean {

    private static final long serialVersionUID = 1L;

    private Validator validator;

    public ControllerValidationPostProcessor() {
    }

    public ControllerValidationPostProcessor(ValidatorFactory validatorFactory) {
        this.setValidator(validatorFactory.getValidator());
    }

    public ControllerValidationPostProcessor(Validator validator) {
        this.setValidator(validator);
    }

    public void setValidatorFactory(ValidatorFactory validatorFactory) {
        Assert.notNull(validatorFactory, "'validatorFactory' must not be null");
        this.validator = validatorFactory.getValidator();
    }

    public void setValidator(Validator validator) {
        Assert.notNull(validator, "'validator' must not be null");
        // Unwrap to the native Validator with forExecutables support
        if (validator instanceof LocalValidatorFactoryBean) {
            this.validator = ((LocalValidatorFactoryBean) validator).getValidator();
        } else if (validator instanceof SpringValidatorAdapter) {
            this.validator = validator.unwrap(Validator.class);
        } else {
            this.validator = validator;
        }
    }


    @Override
    public void afterPropertiesSet() {
        Assert.notNull(validator, "'validator' must not be null");
        Pointcut pointcut = new InheritedAnnotationMethodPointcut(Controller.class, RequestMapping.class);
        this.advisor = new DefaultPointcutAdvisor(pointcut, createMethodValidationAdvice(this.validator));
    }

    protected Advice createMethodValidationAdvice(Validator validator) {
        return (validator == null ?
                new MethodArgumentValidationInterceptor()
                : new MethodArgumentValidationInterceptor(validator));
    }
}
