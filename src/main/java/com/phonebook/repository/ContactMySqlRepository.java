package com.phonebook.repository;

import com.phonebook.model.Contact;
import com.phonebook.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Zabudskyi Oleksandr zabudskyioleksandr@gmail.com
 */
public interface ContactMySqlRepository extends JpaRepository<Contact, Long>{
}
