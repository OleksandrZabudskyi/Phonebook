package com.phonebook.web;

import com.phonebook.model.Contact;
import com.phonebook.model.User;
import com.phonebook.service.ContactService;
import com.phonebook.service.SecurityService;
import com.phonebook.service.UserService;
import com.phonebook.validator.ContactValidator;
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
 * <code>ContactController</code> realizes the controller of the MVC pattern.
 * <code>ContactController</code> handles requests for altering Contact and determine response view
 *
 * @author Zabudskyi Oleksandr zabudskyioleksandr@gmail.com.
 */
@Controller
public class ContactController {
    private ContactService contactService;
    private UserService userService;
    private SecurityService securityService;
    private ContactValidator contactValidator;

    @Autowired
    public ContactController(@Qualifier("contactServType") ContactService contactService,
                             @Qualifier("userServType") UserService userService,
                             SecurityService securityService,
                             ContactValidator contactValidator) {
        this.contactService = contactService;
        this.userService = userService;
        this.securityService = securityService;
        this.contactValidator = contactValidator;
    }

    /**
     * Process of updating selected contact or updating new one in phone book
     *
     * @param contactForm   form containing information about Contact
     * @param bindingResult binding error related with invalid data form
     * @return return Model and View in case of session expired should be returned loginForm
     * if {@code bindingResult} has error  must return home otherwise redirect to phonebook view
     */
    @RequestMapping(value = "/saveContact", method = RequestMethod.POST)
    public ModelAndView saveContact(@ModelAttribute("contactForm") Contact contactForm, BindingResult bindingResult) {
        String userName = securityService.findAuthenticatedUsername();
        User user = userService.findUserByUsername(userName);

        if (user == null) {
            return new ModelAndView("loginForm");
        }

        contactValidator.validate(contactForm, bindingResult);

        Long contactId = contactForm.getId();

        ModelAndView result = new ModelAndView();

        if (bindingResult.hasErrors()) {
            result.addObject("errorMessage", "You have entered invalid values. Please open edit popup for detail.");
            result.setViewName("home");
        } else {
            contactService.saveContact(contactForm, user);
            if (contactId != null) {
                result.addObject("message", "Selected contact was successfully updated");
            } else {
                result.addObject("message", "Contact was successfully add");
            }
            result.setViewName("redirect:/phonebook");
        }

        result.addObject("contacts", user.getContacts());
        return result;
    }

    /**
     * Process of deleting selected contact in phone book
     *
     * @param id
     * @param contactForm form containing information about Contact
     * @param model       holder for model attributes
     * @return return Model and View loginForm or redirect to phonebook
     */
    @RequestMapping(value = "/deleteContact", method = RequestMethod.POST)
    public String deleteContact(@PathParam("id") String id, @ModelAttribute("contactForm") Contact contactForm,
                                Model model) {
        String userName = securityService.findAuthenticatedUsername();
        User user = userService.findUserByUsername(userName);

        if (user == null) {
            return "loginForm";
        }

        if (id != null && !id.isEmpty()) {
            contactService.deleteContact(Long.valueOf(id), user);
            model.addAttribute("message", "Selected contact was successfully removed");
        }

        return "redirect:/phonebook";
    }
}
