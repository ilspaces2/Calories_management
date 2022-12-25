package ru.javawebinar.topjava.web.user;

import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.web.ExceptionInfoHandler;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.validation.Valid;
import java.util.Locale;

@Controller
@RequestMapping("/profile")
public class ProfileUIController extends AbstractUserController {

    private final MessageSource messageSource;

    public ProfileUIController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping
    public String profile() {
        return "profile";
    }

    @PostMapping
    public String updateProfile(@Valid UserTo userTo, BindingResult result, SessionStatus status) {
        if (result.hasErrors()) {
            return "profile";
        } else {
            super.update(userTo, SecurityUtil.authUserId());
            SecurityUtil.get().setTo(userTo);
            status.setComplete();
            return "redirect:/meals";
        }
    }

    @GetMapping("/register")
    public String register(ModelMap model) {
        model.addAttribute("userTo", new UserTo());
        model.addAttribute("register", true);
        return "profile";
    }

    @PostMapping("/register")
    public String saveRegister(@Valid UserTo userTo, BindingResult result, SessionStatus status, ModelMap model) {
        if (result.hasErrors()) {
            model.addAttribute("register", true);
            return "profile";
        } else {
            super.create(userTo);
            status.setComplete();
            return "redirect:/login?message=app.registered&username=" + userTo.getEmail();
        }
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    private String dataBaseEmailError(Model model) {
        String userTo = "userTo";
        UserTo userObject = SecurityUtil.safeGet() == null ? new UserTo() : SecurityUtil.get().getUserTo();
        if (userObject.isNew()) {
            model.addAttribute("register", true);
        }
        BindingResult result = new BeanPropertyBindingResult(userObject, userTo);
        result.rejectValue("email", "error",
                messageSource.getMessage(ExceptionInfoHandler.EXCEPTION_EMAIL_DUPLICATE,
                        null, Locale.getDefault()));
        model.addAttribute(userTo, userObject);
        model.addAttribute(BindingResult.MODEL_KEY_PREFIX + userTo, result);
        return "profile";
    }
}
