package com.phonebook;

import com.phonebook.model.Contact;
import com.phonebook.model.User;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Zabudskyi Oleksandr zabudskyioleksandr@gmail.com
 */
public class DataFactory {

    public static List<Contact> createContactList(int count, User user) {
        List<Contact> contacts = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            Contact contact = new Contact("Ivan" + i, "Popov" + i, "Viktorovich" + i, "+38(096)111111" + i, user);
            contact.setId((long) i);
            contacts.add(contact);
        }
        return contacts;
    }
}
