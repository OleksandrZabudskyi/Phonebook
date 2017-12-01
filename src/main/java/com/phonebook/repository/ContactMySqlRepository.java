package com.phonebook.repository;

import com.phonebook.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Zabudskyi Oleksandr zabudskyioleksandr@gmail.com
 * @see JpaRepository
 */
public interface ContactMySqlRepository extends JpaRepository<Contact, Long> {
}
