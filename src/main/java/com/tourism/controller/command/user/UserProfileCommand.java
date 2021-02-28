package com.tourism.controller.command.user;

import com.tourism.controller.command.Command;
import com.tourism.model.entity.User;
import com.tourism.model.service.UserService;
import com.tourism.util.CommandUtils;

import javax.servlet.http.HttpServletRequest;

public class UserProfileCommand implements Command {
    private final UserService userService;

    public UserProfileCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        User user = CommandUtils.getUserFromSession(request);
        request.setAttribute("user", userService.findUserById(user.getId()));
        return "/WEB-INF/pages/user-profile.jsp";
    }
}
