package com.phonebook.validator;

import com.phonebook.model.User;
import com.phonebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * <code>UserValidator</code> class is the <code>Validator</code> interface
 * implementation.
 * To provide input-data validation
 *
 * @author Zabudskyi Oleksandr zabudskyioleksandr@gmail.com
 * @see Validator
 */
@Component
public class UserValidator implements Validator {
    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9]*$";
    private UserService userService;

    @Autowired
    public UserValidator(@Qualifier("userServType") UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (user.getUsername().length() < 3 || user.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }

        if (!user.getUsername().matches(USERNAME_PATTERN)) {
            errors.rejectValue("username", "Diff.userForm.username");
        }

        if (userService.findUserByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fullName", "NotEmpty");
        if (user.getFullName().length() < 5 || user.getFullName().length() > 32) {
            errors.rejectValue("fullName", "Size.userForm.fullName");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 5 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }
    }
}