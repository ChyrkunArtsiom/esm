package com.epam.esm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;

/**
 * Configuration class for web initializing web application context.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.epam.esm.controller")
public class AppConfig implements WebMvcConfigurer {

    /**
     * Creates view resolver.
     *
     * @param manager the {@link ContentNegotiationManager} manager
     * @return the {@link ViewResolver} object
     */
    @Bean
    public ViewResolver viewResolver(ContentNegotiationManager manager) {
        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
        resolver.setContentNegotiationManager(manager);
        return resolver;
    }

    /**
     * Creates validation processor to enable method validation.
     *
     * @return {@link MethodValidationPostProcessor} object
     */
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
    }
}
