package com.phonebook.web;

import com.phonebook.model.Contact;
import com.phonebook.model.User;
import com.phonebook.service.SecurityService;
import com.phonebook.service.UserService;
import com.phonebook.validator.ContactValidator;
import com.phonebook.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.websocket.server.PathParam;

/**
 * Created by Zabudskyi Oleksandr zabudskyioleksandr@gmail.com
 */
@Controller
public class UserController {

    private UserService userService;
    private SecurityService securityService;
    private UserValidator userValidator;
    private ContactValidator contactValidator;

    public UserController() {
    }

    @Autowired
    public UserController( @Qualifier("storage") UserService userService, SecurityService securityService,
                           UserValidator userValidator, ContactValidator contactValidator) {
        this.userService = userService;
        this.securityService = securityService;
        this.userValidator = userValidator;
        this.contactValidator = contactValidator;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String showRegistrationForm(Model model) {
        model.addAttribute("userForm", new User());
        return "registrationForm";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String processRegistration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registrationForm";
        }

        userService.saveUser(userForm);
        securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());
        return "redirect:/welcome";
    }

    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "loginForm";
    }

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public ModelAndView showPhoneBook(@PathParam("message") String message) {
        ModelAndView result = new ModelAndView("home");
        result.addObject("message",message);
        String userName = securityService.findAuthenticatedUsername();
        User user = userService.findUserByUsername(userName);

        if (user == null) {
            return new ModelAndView("loginForm");
        }

        result.addObject("contacts", user.getContacts());
        result.addObject("contactForm", new Contact());
        return result;

    }

    @RequestMapping(value = "/welcome", method = RequestMethod.POST)
    public ModelAndView saveContact(@ModelAttribute("contactForm") Contact contactForm, BindingResult bindingResult) {
        ModelAndView result = new ModelAndView("home");
        String userName = securityService.findAuthenticatedUsername();
        User user = userService.findUserByUsername(userName);

        if (user == null) {
            return new ModelAndView("loginForm");
        }

        contactValidator.validate(contactForm, bindingResult);
        Long contactId = contactForm.getId();
        if (bindingResult.hasErrors()) {
            result.addObject("errorMessage","You have entered invalid values. Please try again.");
        } else {
            userService.saveContact(contactForm, user);
            if(contactId != null) {
                result.addObject("message", "Selected contact was successfully updated");
            } else {
                result.addObject("message", "Contact was successfully add");
            }

        }

        result.addObject("contacts", user.getContacts());
        return result;
    }

    @RequestMapping(value = "/rm", method = RequestMethod.POST)
    public String deleteContact(@PathParam("id") String id, @ModelAttribute("contactForm") Contact contactForm, Model model) {
        String userName = securityService.findAuthenticatedUsername();
        User user = userService.findUserByUsername(userName);

        if (user == null) {
            return "loginForm";
        }

        if (id != null) {
            userService.deleteContact(Long.valueOf(id), user);
            model.addAttribute("message", "Selected contact was successfully removed");
        }

        return "redirect:/welcome";
    }

}