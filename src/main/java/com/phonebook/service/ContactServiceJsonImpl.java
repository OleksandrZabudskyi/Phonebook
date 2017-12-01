package com.phonebook.service;

import com.phonebook.model.Contact;
import com.phonebook.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * <code>ContactServiceJsonImpl</code> class is the <code>ContactService</code> interface
 * implementation.
 *
 * @author Zabudskyi Oleksandr zabudskyioleksandr@gmail.com.
 * @see ContactService
 */
public class ContactServiceJsonImpl implements ContactService {
    @Autowired
    private UserService userService;

    @Override
    public void saveContact(Contact contact, User user) {
        final Long contactId = contact.getId();
        List<Contact> contacts = user.getContacts();
        if (contacts == null) {
            contacts = new LinkedList<>();
            user.setContacts(contacts);
        }
        if (contactId != null) {
            for (int i = 0; i < contacts.size(); i++) {
                if (contactId.equals(contacts.get(i).getId())) {
                    contact.setId((long) i);
                    contacts.set(i, contact);
                }
            }
        } else {
            contact.setId((long) contacts.size());
            contacts.add(contact);
        }
        userService.saveUser(user);
    }

    @Override
    public void deleteContact(Long contactId, User user) {
        Iterator<Contact> iterator = user.getContacts().iterator();
        while (iterator.hasNext()) {
            Contact contact = iterator.next();
            if (contactId.equals(contact.getId())) {
                iterator.remove();
            }
        }
        userService.saveUser(user);

    }
}
