package com.tourism.controller.command.user;

import com.tourism.controller.command.Command;
import com.tourism.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class UsersCommand implements Command {
    private static final Logger log = LogManager.getLogger();
    private final UserService userService;

    public UsersCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        int page = 0;
        int size = 5;
        if (request.getParameter("page") != null) {
            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                log.warn("Can not parse number from request parameter");
                return "/WEB-INF/error/404.jsp";
            }
        }
        if (request.getParameter("size") != null) {
            try {
                size = Integer.parseInt(request.getParameter("size"));
            } catch (NumberFormatException e) {
                log.warn("Can not parse number from request parameter");
                return "/WEB-INF/error/404.jsp";
            }
        }
        long numberOfRecords = userService.getNumberOfRecords();
        long totalPages = (long) Math.ceil((double) numberOfRecords / size);

        request.setAttribute("users", userService.findAllUsersPageable(page, size));
        request.setAttribute("currentPage", page);
        request.setAttribute("pageSize", size);
        request.setAttribute("totalPages", totalPages);
        return "/WEB-INF/pages/users.jsp";
    }
}
