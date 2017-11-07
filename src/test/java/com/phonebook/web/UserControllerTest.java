package com.phonebook.web;

import com.phonebook.TestDataFactory;
import com.phonebook.model.Contact;
import com.phonebook.service.SecurityService;
import com.phonebook.service.UserService;
import com.phonebook.validator.UserValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import com.phonebook.model.User;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * @author Zabudskyi Oleksandr zabudskyioleksandr@gmail.com.
 */
@RunWith(MockitoJUnitRunner.class)
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
        controller = new UserController(mockUserService, mockSecurityService,
                userValidator);
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
                .andExpect(redirectedUrl("/phonebook"));
    }

    @Test
    public void showRegistrationFormIfUserFormIsNotValid() throws Exception {
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
        List<Contact> contacts = TestDataFactory.createContactList(4, user);
        user.setContacts(contacts);
        when(mockSecurityService.findAuthenticatedUsername()).thenReturn("Jone");
        when(mockUserService.findUserByUsername("Jone")).thenReturn(user);

        MockMvc mockMvc = standaloneSetup(controller).build();
        Contact contact = new Contact();
        mockMvc.perform(get("/phonebook"))
                .andExpect(model().attribute("contacts", user.getContacts()))
                .andExpect(model().attribute("contactForm", contact))
                .andExpect(view().name("home"));

        verify(mockSecurityService, times(1)).findAuthenticatedUsername();
        verify(mockUserService, times(1)).findUserByUsername("Jone");
    }

    @Test
    public void showLoginFormIfUserIsNotAuthenticated() throws Exception {
        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(get("/phonebook"))
                .andExpect(view().name("loginForm"));
    }
}