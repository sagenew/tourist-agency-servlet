package com.tourism.controller.command.auth;

import com.tourism.controller.command.Command;
import com.tourism.model.entity.User;
import com.tourism.model.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class LoginCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private final UserService userService;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (!ObjectUtils.allNotNull(username, password)) {
            return "/login.jsp";
        }

        log.info("User try to log in with username: " + username);

        Optional<User> userOptional = userService.findUserByUsername(username);
        if (userOptional.isEmpty()) {
            log.warn("No such user " + username + " in database");
            request.setAttribute("error", true);
            return "/login.jsp";
        }

        User user = userOptional.get();

        if (!user.isEnabled()) {
            log.info("User is banned " + username);
            request.setAttribute("error", true);
            return "/login.jsp";
        }

        if (user.getPassword().equals(DigestUtils.md5Hex(password))) {
            HttpSession session = request.getSession();
            session.setAttribute("authUser", user);
            log.info("User " + username + " successfully logged in");
            return "redirect:/index";
        } else {
            log.info("Invalid credentials for user " + username);
            request.setAttribute("error", true);
            return "/login.jsp";
        }
    }
}
