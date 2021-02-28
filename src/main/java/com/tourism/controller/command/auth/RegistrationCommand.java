package com.tourism.controller.command.auth;

import com.tourism.controller.command.Command;
import com.tourism.controller.dto.RegistrationUserDTO;
import com.tourism.model.service.UserService;
import com.tourism.util.CommandUtils;
import com.tourism.util.exception.UsernameNotUniqueException;
import com.tourism.util.validator.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class RegistrationCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private final UserService userService;
    private ResourceBundle rb;

    public RegistrationCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        rb = ResourceBundle.getBundle("i18n.messages",
                CommandUtils.getLocaleFromSession(request));

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String bio = request.getParameter("bio");

        if (!ObjectUtils.allNotNull(username, password, fullName, email)) {
            return "/registration.jsp";
        }

        RegistrationUserDTO userDTO = RegistrationUserDTO.builder()
                .username(username)
                .password(password)
                .fullName(fullName)
                .email(email)
                .bio(bio)
                .build();

        Map<String, String[]> validationErrorsMap = getValidationErrorsMap(userDTO);

        if (!validationErrorsMap.isEmpty()) {
            request.setAttribute("user", userDTO);
            request.setAttribute("errors", validationErrorsMap);
            return "/registration.jsp";
        }

        try {
            userService.registerUser(userDTO);
        } catch (UsernameNotUniqueException e) {
            e.printStackTrace();
            request.setAttribute("user", userDTO);
            request.setAttribute("usernameUniqueError",
                    rb.getString("users.registration.login.not_unique"));
            return "/registration.jsp";
        }
        return "redirect:/login";
    }

    private Map<String, String[]> getValidationErrorsMap(RegistrationUserDTO userDTO) {
        Map<String, String[]> validationErrorsMap = new HashMap<>();

        CompositeValidator<String> usernameValidator = new CompositeValidator<> (
                new NotBlankValidator(rb.getString("validation.user.username.not_blank")),
                new SizeValidator(5, 40, rb.getString("validation.user.username.size"))
        );
        CompositeValidator<String> passwordValidator = new CompositeValidator<> (
                new NotBlankValidator(rb.getString("validation.user.password.not_blank")),
                new SizeValidator(5, 40, rb.getString("validation.user.password.size"))
        );
        CompositeValidator<String> fullNameValidator = new CompositeValidator<> (
                new NotBlankValidator(rb.getString("validation.user.full_name.not_blank")),
                new SizeValidator(2, 40, rb.getString("validation.user.full_name.size"))
        );
        CompositeValidator<String> emailValidator = new CompositeValidator<> (
                new NotBlankValidator(rb.getString("validation.user.email.not_blank")),
                new EmailAddressValidator(rb.getString("validation.user.email.invalid"))
        );
        CompositeValidator<String> bioValidator = new CompositeValidator<> (
                new SizeOrBlankValidator(0, 200, rb.getString("validation.user.bio.size"))
        );

        Result result = usernameValidator.validate(userDTO.getUsername());
        if (!result.isValid()) {
            validationErrorsMap.put("usernameErrors", result.getMessage().split("\n"));
        }
        result = passwordValidator.validate(userDTO.getPassword());
        if (!result.isValid()) {
            validationErrorsMap.put("passwordErrors", result.getMessage().split("\n"));
        }
        result = fullNameValidator.validate(userDTO.getFullName());
        if (!result.isValid()) {
            validationErrorsMap.put("fullNameErrors", result.getMessage().split("\n"));
        }
        result = emailValidator.validate(userDTO.getEmail());
        if (!result.isValid()) {
            validationErrorsMap.put("emailErrors", result.getMessage().split("\n"));
        }
        result = bioValidator.validate(userDTO.getBio());
        if (!result.isValid()) {
            validationErrorsMap.put("bioErrors", result.getMessage().split("\n"));
        }
        return validationErrorsMap;
    }
}
