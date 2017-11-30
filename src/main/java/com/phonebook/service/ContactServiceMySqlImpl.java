package com.phonebook.service;

import com.phonebook.model.Contact;
import com.phonebook.model.User;
import com.phonebook.repository.ContactMySqlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <code>ContactServiceMySqlImpl</code> class is the <code>ContactService</code> interface
 * implementation.
 *
 * @author Zabudskyi Oleksandr zabudskyioleksandr@gmail.com
 * @see ContactService
 */
@Service
public class ContactServiceMySqlImpl implements ContactService {
    private ContactMySqlRepository contactRepository;

    @Autowired
    public ContactServiceMySqlImpl(ContactMySqlRepository contactRepository) {
        this.contactRepository = contactRepository;
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
