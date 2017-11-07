package com.phonebook.repository;

import com.phonebook.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Zabudskyi Oleksandr zabudskyioleksandr@gmail.com
 * @see JpaRepository
 */
public interface UserMySqlRepository extends JpaRepository<User, Long>, UserRepository {
}
