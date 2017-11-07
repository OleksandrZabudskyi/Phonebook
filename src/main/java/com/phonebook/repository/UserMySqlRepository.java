package com.phonebook.repository;

import com.phonebook.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Zabudskyi Oleksandr zabudskyioleksandr@gmail.com
 */
public interface UserMySqlRepository extends JpaRepository<User, Long>, UserRepository {
}
