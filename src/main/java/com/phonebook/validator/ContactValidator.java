package com.phonebook.validator;


import com.phonebook.model.Contact;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by Zabudskyi Oleksandr zabudskyioleksandr@gmail.com
 */
@Component
public class ContactValidator implements Validator {

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String MOBILE_PATTERN = "^[\\+38\\(0]{5}[0-9]{2}\\)[0-9]{7}$";

    @Override
    public boolean supports(Class<?> aClass) {
        return Contact.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Contact contact = (Contact) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty");
        if (contact.getFirstName().length() < 4 || contact.getFirstName().length() > 32) {
            errors.rejectValue("firstName", "Size.contactForm.firstName");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty");
        if (contact.getLastName().length() < 4 || contact.getLastName().length() > 32) {
            errors.rejectValue("lastName", "Size.contactForm.lastName");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "additionalName", "NotEmpty");
        if (contact.getAdditionalName().length() < 4 || contact.getAdditionalName().length() > 32) {
            errors.rejectValue("additionalName", "Size.contactForm.additionalName");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mobilePhone", "NotEmpty");
        if (!contact.getMobilePhone().matches(MOBILE_PATTERN)) {
            errors.rejectValue("mobilePhone", "Diff.contactForm.mobilePhone");
        }

        if (contact.getEmail() != null && !contact.getEmail().isEmpty()
                && !contact.getEmail().matches(EMAIL_PATTERN)) {
            errors.rejectValue("firstName", "Diff.contactForm.email");
        }
    }
}
