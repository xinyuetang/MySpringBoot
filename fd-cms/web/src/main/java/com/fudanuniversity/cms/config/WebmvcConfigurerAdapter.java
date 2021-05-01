package com.fudanuniversity.cms.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fudanuniversity.cms.commons.util.JsonUtils;
import com.fudanuniversity.cms.framework.converter.StringToDateConverter;
import com.fudanuniversity.cms.framework.exception.HandlerPlatformExceptionResolver;
import com.fudanuniversity.cms.framework.validation.NoOpSmartValidator;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import java.util.List;

/**
 * Created by tidu at 2021-04-16 10:16:47
 */
@Configuration
public class WebmvcConfigurerAdapter extends WebMvcConfigurationSupport {

    @Override
    public Validator getValidator() {
        return new NoOpSmartValidator();
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(StringToDateConverter.INSTANCE);
    }

    @Override
    protected void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        //DefaultHandlerExceptionResolver优先级为Ordered.LOWEST_PRECEDENCE，是最后一个异常处理，这里将该异常处理替换掉
        exceptionResolvers.removeIf(
                exceptionResolver -> exceptionResolver instanceof DefaultHandlerExceptionResolver);
        exceptionResolvers.add(new HandlerPlatformExceptionResolver());
    }

    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.addDefaultHttpMessageConverters(converters);
        //替换默认Jackson2配置
        converters.removeIf(converter -> converter instanceof MappingJackson2HttpMessageConverter);
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = JsonUtils.createGeneralObjectMapper();
        jsonConverter.setObjectMapper(objectMapper);
        converters.add(0, jsonConverter);
    }
}
