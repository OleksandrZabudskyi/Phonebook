package com.phonebook.service;

import com.phonebook.model.Contact;
import com.phonebook.model.User;

import java.io.IOException;
import java.util.List;

/**
 * Provide service layer to manipulate with data
 *
 * @author Zabudskyi Oleksandr zabudskyioleksandr@gmail.com
 */
public interface UserService {
    /**
     *
     * @param username
     * @return
     */
    User findUserByUsername(String username);

    /**
     *
     * @param user user
     */
    void saveUser(User user);
}
