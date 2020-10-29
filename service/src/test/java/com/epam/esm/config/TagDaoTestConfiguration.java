package com.epam.esm.config;

import com.epam.esm.dao.impl.TagDAO;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class TagDaoTestConfiguration {
    @Bean
    @Primary
    public TagDAO dao() {
        return Mockito.mock(TagDAO.class);
    }
}
