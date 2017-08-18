package com.phonebook.config;

import com.phonebook.repository.*;
import com.phonebook.service.UserService;
import com.phonebook.service.UserServiceMySqlImpl;
import com.phonebook.service.UserServiceJsonImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


/**
 * Created by Zabudskyi Oleksandr zabudskyioleksandr@gmail.com
 */
@Configuration
@ComponentScan
public class UserSourceConfig {

    @Bean(name = "storage")
    public UserService userService(@Value("${app.dbType}") String databaseType, ApplicationContext context) {
        if ("JSON".equalsIgnoreCase(databaseType)) {
            return context.getBean(UserServiceJsonImpl.class);
        } else {
            return context.getBean(UserServiceMySqlImpl.class);
        }
    }

    @Bean(name = "userRepoType")
    public UserRepository userDAO(@Value("${app.dbType}") String databaseType, ApplicationContext context) {
        if ("JSON".equalsIgnoreCase(databaseType)) {
            return context.getBean(UserJsonFileRepository.class);
        } else {
            return context.getBean(UserMySqlRepository.class);
        }
    }
}