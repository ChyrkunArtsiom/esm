package com.epam.esm.controller.config;


import com.epam.esm.service.impl.GiftCertificateService;
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
    public TagService tagService() {
        return Mockito.mock(TagService.class);
    }

    @Bean
    @Primary
    public GiftCertificateService certificateService() {
        return Mockito.mock(GiftCertificateService.class);
    }

}
