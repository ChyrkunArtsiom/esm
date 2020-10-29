package com.epam.esm.controller.config;


import com.epam.esm.service.impl.TagService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class TagServiceTestConfiguration {
    @Bean
    @Primary
    public TagService service() {
        return Mockito.mock(TagService.class);
    }


}
