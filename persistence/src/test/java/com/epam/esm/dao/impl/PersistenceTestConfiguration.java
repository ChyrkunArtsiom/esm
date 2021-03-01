package com.epam.esm.dao.impl;

import com.epam.esm.PersistenceConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(PersistenceConfiguration.class)
public class PersistenceTestConfiguration {

}
