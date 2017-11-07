package com.phonebook.service;

import com.phonebook.model.User;

/**
 * @author Zabudskyi Oleksandr zabudskyioleksandr@gmail.com
 */
public interface SecurityService {
    String findAuthenticatedUsername();

    void autoLogin(String username, String password);
}
