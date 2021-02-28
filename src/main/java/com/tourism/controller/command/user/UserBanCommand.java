package com.tourism.controller.command.user;

import com.tourism.controller.command.Command;
import com.tourism.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class UserBanCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private final UserService userService;

    public UserBanCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        long id;
        try {
            id = Long.parseLong(request.getParameter("id"));
        } catch (NumberFormatException e) {
            log.warn("Can not parse number from request parameter");
            return "/WEB-INF/error/404.jsp";
        }
        userService.banUser(id);
        return "redirect:/users";
    }
}
