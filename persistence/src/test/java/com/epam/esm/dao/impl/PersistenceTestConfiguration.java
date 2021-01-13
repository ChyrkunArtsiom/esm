package com.epam.esm.dao.impl;

import com.epam.esm.PersistenceConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
/*@EnableAutoConfiguration
@ComponentScan*/
@Import(PersistenceConfiguration.class)
public class PersistenceTestConfiguration {
}
