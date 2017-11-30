package com.phonebook.service;

import com.phonebook.model.Contact;
import com.phonebook.model.User;

/**
 *  Provide service layer to manipulate with data
 *
 * @author Zabudskyi Oleksandr zabudskyioleksandr@gmail.com.
 */
public interface ContactService {
    /**
     * Save contact for user
     *
     * @param contact contact
     * @param user user
     */
    void saveContact(Contact contact, User user);

    /**
     * Delete contact for user
     *
     * @param contactId contactId
     * @param user user
     */
    void deleteContact(Long contactId, User user);
}
