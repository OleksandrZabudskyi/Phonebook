package com.phonebook.service;

import com.phonebook.model.User;

/**
 * Created by Zabudskyi Oleksandr zabudskyioleksandr@gmail.com
 */
public interface SecurityService {
    String findAuthenticatedUsername();

    void autologin(String username, String password);
}
