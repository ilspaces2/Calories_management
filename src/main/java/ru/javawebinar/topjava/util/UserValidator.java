package ru.javawebinar.topjava.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.web.SecurityUtil;

@Component
public class UserValidator implements Validator {

    private final UserService userService;

    private final Validator validator;

    public UserValidator(UserService userService, Validator validator) {
        this.userService = userService;
        this.validator = validator;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserTo.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserTo userTo = (UserTo) target;
        ValidationUtils.invokeValidator(validator, userTo, errors);
        if (!errors.hasErrors()) {
            AuthorizedUser authUser = SecurityUtil.safeGet();
            User user = userService.getByEmailForValidate(userTo.getEmail());
            if (user != null && (authUser == null || !authUser.getUserTo().getEmail().equals(user.getEmail()))) {
                errors.rejectValue("email", "error.email.duplicate");
            }
        }
    }
}
