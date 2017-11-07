package com.phonebook.service;

import com.phonebook.model.Contact;
import com.phonebook.model.User;

import java.io.IOException;
import java.util.List;

/**
 * @author Zabudskyi Oleksandr zabudskyioleksandr@gmail.com
 */
public interface UserService {

    User findUserByUsername(String username);

    void saveUser(User user);

    void saveContact(Contact contact, User user);

    void deleteContact(Long contactId, User user);
}
