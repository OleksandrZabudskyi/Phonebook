package com.phonebook.web;

import com.phonebook.model.Contact;
import com.phonebook.service.SecurityService;
import com.phonebook.service.UserService;
import com.phonebook.validator.ContactValidator;
import com.phonebook.validator.UserValidator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedList;
import java.util.List;

import com.phonebook.model.User;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by Zabudskyi Oleksandr on 8/15/17.
 */
public class UserControllerTest {
    @Mock
    private UserService mockUserService;
    @Mock
    private SecurityService mockSecurityService;

    private UserController controller;

    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
        UserValidator userValidator = new UserValidator(mockUserService);
        ContactValidator contactValidator = new ContactValidator();
        controller = new UserController(mockUserService, mockSecurityService,
                userValidator, contactValidator);
    }

    @Test
    public void showRegistrationForm() throws Exception {
        UserController controller = new UserController();
        MockMvc mockMvc = standaloneSetup(controller).build();
        User user = new User();
        mockMvc.perform(get("/registration"))
                .andExpect(model().attribute("userForm", user))
                .andExpect(view().name("registrationForm"));
    }

    @Test
    public void showWelcomePageInProcessRegistrationIfUserFormIsValid() throws Exception {
        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(post("/registration")
                .param("username", "Jone")
                .param("fullName", "Jone Bishop")
                .param("password", "12345")
                .param("passwordConfirm", "12345"))
                .andExpect(redirectedUrl("/welcome"));
    }

    @Test
    public void showRegistrFormInProcessRegistrationIfUserFormIsNotValid() throws Exception {
        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(post("/registration")
                .param("username", "")
                .param("fullName", "")
                .param("password", "12345")
                .param("passwordConfirm", "12345"))
                .andExpect(view().name("registrationForm"));
    }

    @Test
    public void showLoginForm() throws Exception {
        UserController controller = new UserController();
        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(get("/"))
                .andExpect(view().name("loginForm"));
        mockMvc.perform(get("/login"))
                .andExpect(view().name("loginForm"));

        mockMvc.perform(get("/login").param("error", "true"))
                .andExpect(model().attribute("error", "Your username and password is invalid."))
                .andExpect(view().name("loginForm"));

        mockMvc.perform(get("/login").param("logout", "true"))
                .andExpect(model().attribute("message", "You have been logged out successfully."))
                .andExpect(view().name("loginForm"));

    }

    @Test
    public void showPhoneBook() throws Exception {
        User user = new User("Jone", "12345", "Jone Bishop");
        List<Contact> contacts = createContactList(4, user);
        user.setContacts(contacts);
        when(mockSecurityService.findAuthenticatedUsername()).thenReturn("Jone");
        when(mockUserService.findUserByUsername("Jone")).thenReturn(user);

        MockMvc mockMvc = standaloneSetup(controller).build();

        Contact contact = new Contact();
        mockMvc.perform(get("/welcome"))
                .andExpect(model().attribute("contacts", user.getContacts()))
                .andExpect(model().attribute("contactForm", contact))
                .andExpect(view().name("home"));
        verify(mockSecurityService, times(1)).findAuthenticatedUsername();
        verify(mockUserService, times(1)).findUserByUsername("Jone");
    }

    @Test
    public void showLoginFormIfUserIsNotAuthenticated() throws Exception {
        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(get("/welcome"))
                .andExpect(view().name("loginForm"));
        mockMvc.perform(post("/welcome"))
                .andExpect(view().name("loginForm"));
        mockMvc.perform(post("/rm"))
                .andExpect(view().name("loginForm"));
    }

    @Test
    public void saveContact() throws Exception {
        User user = new User("Jone", "12345", "Jone Bishop");
        List<Contact> contacts = createContactList(4, user);
        user.setContacts(contacts);
        when(mockSecurityService.findAuthenticatedUsername()).thenReturn("Jone");
        when(mockUserService.findUserByUsername("Jone")).thenReturn(user);

        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(post("/welcome")
                .param("firstName", "Bill")
                .param("lastName", "Longer")
                .param("additionalName", "Milovich")
                .param("mobilePhone", "+38(096)1111111"))
                .andExpect(model().attribute("contacts", user.getContacts()))
                .andExpect(view().name("home"));
        verify(mockSecurityService, times(1)).findAuthenticatedUsername();
        verify(mockUserService, times(1)).findUserByUsername("Jone");
    }

    @Test
    public void updateContact() throws Exception {
        User user = new User("Jone", "12345", "Jone Bishop");
        List<Contact> contacts = createContactList(4, user);
        user.setContacts(contacts);
        when(mockSecurityService.findAuthenticatedUsername()).thenReturn("Jone");
        when(mockUserService.findUserByUsername("Jone")).thenReturn(user);

        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(post("/welcome")
                .param("id", "0")
                .param("firstName", "NewIvan")
                .param("lastName", "Bolton")
                .param("additionalName", "Milovich")
                .param("mobilePhone", "+38(096)1111111"))
                .andExpect(model().attribute("contacts", user.getContacts()))
                .andExpect(view().name("home"));
        verify(mockSecurityService, times(1)).findAuthenticatedUsername();
        verify(mockUserService, times(1)).findUserByUsername("Jone");
    }

    @Test
    public void deleteContact() throws Exception {
        User user = new User("Mike", "12345", "Jone Bishop");
        List<Contact> contacts = createContactList(4, user);
        user.setContacts(contacts);
        when(mockSecurityService.findAuthenticatedUsername()).thenReturn("Mike");
        when(mockUserService.findUserByUsername("Mike")).thenReturn(user);
        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(post("/rm"))
                .andExpect(redirectedUrl("/welcome"));
        verify(mockSecurityService, times(1)).findAuthenticatedUsername();
        verify(mockUserService, times(1)).findUserByUsername("Mike");
    }

    private List<Contact> createContactList(int count, User user) {
        List<Contact> contacts = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            Contact contact = new Contact("Ivan" + i, "Popov" + i, "Viktorovich" + i, "+38(096)111111" + i, user);
            contact.setId((long) i);
            contacts.add(contact);
        }
        return contacts;
    }

}