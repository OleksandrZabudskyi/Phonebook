package com.phonebook.repository;

import com.phonebook.model.User;

/**
 * Edition contract for user JPA repository.
 *
 * @author Zabudskyi Oleksandr zabudskyioleksandr@gmail.com
 */
public interface UserRepository {
    /**
     * Extracting User from database
     *
     * @param username user name
     * @return user object model
     */
    User findByUsername(String username);
}
