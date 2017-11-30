package com.phonebook.config;

import com.phonebook.repository.*;
import com.phonebook.service.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


/**
 * @author Zabudskyi Oleksandr zabudskyioleksandr@gmail.com
 */
@Configuration
@ComponentScan
public class SourceConfig {

    @Bean(name = "userServType")
    public UserService userService(@Value("${app.dbType}") String databaseType, ApplicationContext context) {
        if ("JSON".equalsIgnoreCase(databaseType)) {
            return context.getBean(UserServiceJsonImpl.class);
        } else {
            return context.getBean(UserServiceMySqlImpl.class);
        }
    }

    @Bean(name = "contactServType")
    public ContactService contactService(@Value("${app.dbType}") String databaseType, ApplicationContext context) {
        if ("JSON".equalsIgnoreCase(databaseType)) {
            return context.getBean(ContactServiceJsonImpl.class);
        } else {
            return context.getBean(ContactServiceMySqlImpl.class);
        }
    }

    @Bean(name = "userRepoType")
    public UserRepository userRepository(@Value("${app.dbType}") String databaseType, ApplicationContext context) {
        if ("JSON".equalsIgnoreCase(databaseType)) {
            return context.getBean(UserJsonFileRepository.class);
        } else {
            return context.getBean(UserMySqlRepository.class);
        }
    }
}