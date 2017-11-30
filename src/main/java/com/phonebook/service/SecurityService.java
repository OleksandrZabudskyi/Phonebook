package com.phonebook.service;


import com.phonebook.model.User;

/**
 * To provide current logged in user and auto login user after registration an account.
 *
 * @author Zabudskyi Oleksandr zabudskyioleksandr@gmail.com
 */
public interface SecurityService {
    /**
     *Extracting user name from Security Context
     *
     * @return username
     */
    String findAuthenticatedUsername();

    /**
     * User authentication in Security Context
     *
     * @param username username
     * @param password password
     */
    void autoLogin(String username, String password);
}
