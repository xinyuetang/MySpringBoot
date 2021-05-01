package com.fudanuniversity.cms.config;

import com.fudanuniversity.cms.framework.validation.ControllerValidationPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validator;

/**
 * 配置web组件等
 * <p>
 * Created by tidu at 2021-04-19 14:06:43
 */
@Configuration
public class WebComponentConfig {

    /**
     * 为Controller补充方法执行时校验
     */
    @Bean
    public ControllerValidationPostProcessor controllerValidationPostProcessor(Validator validator) {
        return new ControllerValidationPostProcessor(validator);
    }

}
