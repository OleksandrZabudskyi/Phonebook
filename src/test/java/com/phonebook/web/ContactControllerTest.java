package com.phonebook.web;

import com.phonebook.TestDataFactory;
import com.phonebook.model.Contact;
import com.phonebook.model.User;
import com.phonebook.service.ContactService;
import com.phonebook.service.SecurityService;
import com.phonebook.service.UserService;
import com.phonebook.validator.ContactValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * @author Zabudskyi Oleksandr zabudskyioleksandr@gmail.com
 */
@RunWith(MockitoJUnitRunner.class)
public class ContactControllerTest {

    @Mock
    private UserService mockUserService;
    @Mock
    private ContactService mockContactService;
    @Mock
    private SecurityService mockSecurityService;
    @Mock
    private ContactValidator mockContactValidator;

    private ContactController contactController;

    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
        contactController = new ContactController(mockContactService, mockUserService, mockSecurityService,
                mockContactValidator);
    }

    @Test
    public void updateContact() throws Exception {
        User user = new User("Jone", "12345", "Jone Bishop");
        List<Contact> contacts = TestDataFactory.createContactList(4, user);
        user.setContacts(contacts);
        when(mockSecurityService.findAuthenticatedUsername()).thenReturn("Jone");
        when(mockUserService.findUserByUsername("Jone")).thenReturn(user);

        MockMvc mockMvc = standaloneSetup(contactController).build();
        mockMvc.perform(post("/saveContact")
                .param("id", "0")
                .param("firstName", "NewIvan")
                .param("lastName", "Bolton")
                .param("additionalName", "Milovich")
                .param("mobilePhone", "+38(096)1111111"))
                .andExpect(model().attribute("contacts", user.getContacts()))
                .andExpect(view().name("redirect:/phonebook"));

        verify(mockSecurityService, times(1)).findAuthenticatedUsername();
        verify(mockUserService, times(1)).findUserByUsername("Jone");
    }

    @Test
    public void saveContact() throws Exception {
        User user = new User("Jone", "12345", "Jone Bishop");
        List<Contact> contacts = TestDataFactory.createContactList(4, user);
        user.setContacts(contacts);
        when(mockSecurityService.findAuthenticatedUsername()).thenReturn("Jone");
        when(mockUserService.findUserByUsername("Jone")).thenReturn(user);

        MockMvc mockMvc = standaloneSetup(contactController).build();
        mockMvc.perform(post("/saveContact")
                .param("firstName", "Bill")
                .param("lastName", "Longer")
                .param("additionalName", "Milovich")
                .param("mobilePhone", "+38(096)1111111"))
                .andExpect(model().attribute("contacts", user.getContacts()))
                .andExpect(view().name("redirect:/phonebook"));

        verify(mockSecurityService, times(1)).findAuthenticatedUsername();
        verify(mockUserService, times(1)).findUserByUsername("Jone");
    }

    @Test
    public void deleteContact() throws Exception {
        User user = new User("Mike", "12345", "Jone Bishop");
        List<Contact> contacts = TestDataFactory.createContactList(4, user);
        user.setContacts(contacts);
        when(mockSecurityService.findAuthenticatedUsername()).thenReturn("Mike");
        when(mockUserService.findUserByUsername("Mike")).thenReturn(user);

        MockMvc mockMvc = standaloneSetup(contactController).build();
        mockMvc.perform(post("/deleteContact"))
                .andExpect(redirectedUrl("/phonebook"));

        verify(mockSecurityService, times(1)).findAuthenticatedUsername();
        verify(mockUserService, times(1)).findUserByUsername("Mike");
    }

    @Test
    public void showLoginFormIfUserIsNotAuthenticated() throws Exception {
        MockMvc mockMvc = standaloneSetup(contactController).build();
        mockMvc.perform(post("/saveContact"))
                .andExpect(view().name("loginForm"));
        mockMvc.perform(post("/deleteContact"))
                .andExpect(view().name("loginForm"));
    }
}