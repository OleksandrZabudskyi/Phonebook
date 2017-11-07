package com.phonebook.service;

import com.phonebook.model.Contact;
import com.phonebook.model.User;
import com.phonebook.repository.UserJsonFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Zabudskyi Oleksandr zabudskyioleksandr@gmail.com
 */
@Service
public class UserServiceJsonImpl implements UserService {

    @Autowired
    private UserJsonFileRepository userJsonFileRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User findUserByUsername(String username) {
        return userJsonFileRepository.findByUsername(username);
    }

    @Override
    public void saveUser(User user) {
        if (user.getId() == null){
            user.setId((long) user.hashCode());
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        userJsonFileRepository.saveUser(user);
    }

    @Override
    public void saveContact(Contact contact, User user) {
        final Long contactId = contact.getId();
        List<Contact> contacts = user.getContacts();
        if(contacts == null) {
            contacts = new LinkedList<>();
            user.setContacts(contacts);
        }
        if(contactId != null){
            for (int i = 0; i < contacts.size() ; i++) {
                if( contactId.equals(contacts.get(i).getId())) {
                    contact.setId((long) i);
                    contacts.set(i, contact);
                }
            }
        } else {
            contact.setId((long) contacts.size());
            contacts.add(contact);
        }
        saveUser(user);
    }

    @Override
    public void deleteContact(Long contactId, User user) {
        Iterator<Contact> iterator = user.getContacts().iterator();
        while (iterator.hasNext()) {
           Contact contact = iterator.next();
            if(contactId.equals(contact.getId())) {
                iterator.remove();
            }
        }
        saveUser(user);

    }
}
