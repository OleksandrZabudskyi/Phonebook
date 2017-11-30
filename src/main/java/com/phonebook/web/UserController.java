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
 * <code>UserController</code> realizes the controller of the MVC pattern.
 * <code>UserController</code> handles requests for user account and determining response view
 *
 * @author Zabudskyi Oleksandr zabudskyioleksandr@gmail.com
 */
@Controller
public class UserController {

    private UserService userService;
    private SecurityService securityService;
    private UserValidator userValidator;

    public UserController() {
    }

    @Autowired
    public UserController(@Qualifier("userServType") UserService userService, SecurityService securityService,
                          UserValidator userValidator) {
        this.userService = userService;
        this.securityService = securityService;
        this.userValidator = userValidator;
    }

    /**
     * Loading registration form
     *
     * @param model model for attributes
     * @return registration form name view
     */
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String showRegistrationForm(Model model) {
        model.addAttribute("userForm", new User());
        return "registrationForm";
    }

    /**
     * Process of registration new user
     *
     * @param userForm      form containing information about User
     * @param bindingResult binding error related with invalid data form
     * @return in case of {@code bindingResult} has error return registration form otherwise redirect
     * to phone book page
     */
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String processRegistration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registrationForm";
        }

        userService.saveUser(userForm);
        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());
        return "redirect:/phonebook";
    }

    /**
     * Loading login form
     *
     * @param model  model for attributes
     * @param error  error attribute
     * @param logout logout attribute
     * @return login form name view
     */
    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {

        if (error != null) {
            model.addAttribute("error", "Your username and password is invalid.");
        }

        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
        }

        return "loginForm";
    }

    /**
     * Process of loading phonebook on home page
     *
     * @param message message attribute
     * @return model and view with name home or login form in case expired session
     */
    @RequestMapping(value = "/phonebook", method = RequestMethod.GET)
    public ModelAndView showPhoneBook(@PathParam("message") String message) {
        ModelAndView result = new ModelAndView("home");
        result.addObject("message", message);

        String userName = securityService.findAuthenticatedUsername();
        User user = userService.findUserByUsername(userName);

        if (user == null) {
            return new ModelAndView("loginForm");
        }

        result.addObject("contacts", user.getContacts());
        result.addObject("contactForm", new Contact());
        return result;

    }
}