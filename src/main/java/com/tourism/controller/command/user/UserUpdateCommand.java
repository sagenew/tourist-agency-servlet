package com.tourism.controller.command.user;

import com.tourism.controller.command.Command;
import com.tourism.controller.dto.UpdateUserDTO;
import com.tourism.model.entity.User;
import com.tourism.model.entity.enums.Authority;
import com.tourism.model.service.UserService;
import com.tourism.util.CommandUtils;
import com.tourism.util.exception.UsernameNotUniqueException;
import com.tourism.util.validator.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

public class UserUpdateCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private final UserService userService;
    private ResourceBundle rb;

    public UserUpdateCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        rb = ResourceBundle.getBundle("i18n.messages",
                CommandUtils.getLocaleFromSession(request));

        long id;
        try {
            id = Long.parseLong(request.getParameter("id"));
        } catch (NumberFormatException e) {
            log.warn("Can not parse number from request parameter");
            return "/WEB-INF/error/404.jsp";
        }

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String bio = request.getParameter("bio");
        String discount = request.getParameter("discount");
        String[] authorities = request.getParameterValues("authorities");

        if (!ObjectUtils.allNotNull(username, password, fullName, bio)) {
            User user = userService.findUserById(id);
            request.setAttribute("user", user);
            request.setAttribute("authorities", Authority.values());
            return "/WEB-INF/pages/user-update.jsp";
        }

        UpdateUserDTO userDTO = UpdateUserDTO.builder()
                .id(id)
                .username(username)
                .password(password)
                .fullName(fullName)
                .email(email)
                .bio(bio)
                .discount(Double.parseDouble(discount))
                .authorities(extractAuthorities(authorities))
                .build();

        Map<String, String[]> validationErrorsMap = getValidationErrorsMap(userDTO);
        if (!validationErrorsMap.isEmpty()) {
            request.setAttribute("user", userDTO);
            request.setAttribute("authorities", Authority.values());
            request.setAttribute("errors", validationErrorsMap);
            return "/WEB-INF/pages/user-update.jsp";
        }

        try {
            userService.updateUser(userDTO);
        } catch (UsernameNotUniqueException e) {
            e.printStackTrace();
            request.setAttribute("user", userDTO);
            request.setAttribute("authorities", Authority.values());
            request.setAttribute("usernameUniqueError", rb.getString("users.registration.login.not_unique"));
            return "/WEB-INF/pages/user-update.jsp";
        }
        return "redirect:/users";
    }

    private Set<Authority> extractAuthorities(String[] authorities) {
        if (authorities != null) {
            return Arrays.stream(authorities).map(Authority::valueOf).collect(Collectors.toSet());
        } else {
            return Collections.emptySet();
        }
    }

    private Map<String, String[]> getValidationErrorsMap(UpdateUserDTO userDTO) {
        Map<String, String[]> validationErrorsMap = new HashMap<>();

        CompositeValidator<String> usernameValidator = new CompositeValidator<> (
                new NotBlankValidator(rb.getString("validation.user.username.not_blank")),
                new SizeValidator(5, 40, rb.getString("validation.user.username.size"))
        );
        CompositeValidator<String> passwordValidator = new CompositeValidator<> (
                new SizeOrBlankValidator(5, 40, rb.getString("validation.user.password.size"))
        );
        CompositeValidator<String> fullNameValidator = new CompositeValidator<> (
                new NotBlankValidator(rb.getString("validation.user.full_name.not_blank")),
                new SizeValidator(2, 40, rb.getString("validation.user.full_name.size"))
        );
        CompositeValidator<String> emailValidator = new CompositeValidator<> (
                new NotBlankValidator(rb.getString("validation.user.email.not_blank")),
                new EmailAddressValidator(rb.getString("validation.user.email.invalid"))
        );
        CompositeValidator<Double> discountValidator = new CompositeValidator<> (
                new RangeDoubleValidator(0, 100, rb.getString("validation.user.discount.range"))
        );
        CompositeValidator<String> bioValidator = new CompositeValidator<> (
                new SizeOrBlankValidator(0, 200, rb.getString("validation.user.bio.size"))
        );

        Result result = usernameValidator.validate(userDTO.getUsername());
        if (!usernameValidator.validate(userDTO.getUsername()).isValid()) {
            validationErrorsMap.put("usernameErrors", result.getMessage().split("\n"));
        }
        result = passwordValidator.validate(userDTO.getPassword());
        if (!passwordValidator.validate(userDTO.getPassword()).isValid()) {
            validationErrorsMap.put("passwordErrors", result.getMessage().split("\n"));
        }
        result = fullNameValidator.validate(userDTO.getFullName());
        if (!fullNameValidator.validate(userDTO.getFullName()).isValid()) {
            validationErrorsMap.put("fullNameErrors", result.getMessage().split("\n"));
        }
        result = emailValidator.validate(userDTO.getEmail());
        if (!emailValidator.validate(userDTO.getEmail()).isValid()) {
            validationErrorsMap.put("emailErrors", result.getMessage().split("\n"));
        }
        result = bioValidator.validate(userDTO.getBio());
        if (!bioValidator.validate(userDTO.getBio()).isValid()) {
            validationErrorsMap.put("bioErrors", result.getMessage().split("\n"));
        }
        if (!discountValidator.validate(userDTO.getDiscount()).isValid()) {
            validationErrorsMap.put("discountErrors", result.getMessage().split("\n"));
        }
        return validationErrorsMap;
    }
}
