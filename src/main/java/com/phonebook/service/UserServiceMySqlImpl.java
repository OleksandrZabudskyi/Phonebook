package com.phonebook.service;

import com.phonebook.model.Contact;
import com.phonebook.model.User;
import com.phonebook.repository.ContactMySqlRepository;
import com.phonebook.repository.UserMySqlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by Zabudskyi Oleksandr zabudskyioleksandr@gmail.com
 */
@Service
public class UserServiceMySqlImpl implements UserService {
    private UserMySqlRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private ContactMySqlRepository contactRepository;



    @Autowired
    public UserServiceMySqlImpl(UserMySqlRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                                ContactMySqlRepository contactRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.contactRepository = contactRepository;
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void saveContact(Contact contact, User user) {
        contact.setUser(user);
        contactRepository.save(contact);
    }

    @Override
    public void deleteContact(Long contactId, User user) {
        contactRepository.delete(contactId);
    }

}
