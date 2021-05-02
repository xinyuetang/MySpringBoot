package com.fudanuniversity.cms.config;

import com.fudanuniversity.cms.framework.validation.ControllerValidationPostProcessor;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置web组件等
 * <p>
 * Created by tidu at 2021-04-19 14:06:43
 */
@Configuration
public class WebComponentConfig {

    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        return validatorFactory.getValidator();
    }

    /**
     * 为Controller补充方法执行时校验
     */
    @Bean
    public ControllerValidationPostProcessor controllerValidationPostProcessor(Validator validator) {
        return new ControllerValidationPostProcessor(validator);
    }

}
