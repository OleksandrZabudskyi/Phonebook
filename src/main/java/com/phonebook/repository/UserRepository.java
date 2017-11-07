package com.phonebook.repository;

import com.phonebook.model.User;
import org.springframework.stereotype.Repository;

/**
 * @author Zabudskyi Oleksandr zabudskyioleksandr@gmail.com
 */
public interface UserRepository {
    User findByUsername(String username);
}
