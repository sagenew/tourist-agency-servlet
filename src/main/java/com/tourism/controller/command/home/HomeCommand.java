package com.tourism.controller.command.home;

import com.tourism.controller.command.Command;

import javax.servlet.http.HttpServletRequest;

public class HomeCommand implements Command {
    public HomeCommand() { }

    @Override
    public String execute(HttpServletRequest request) {
        return "/index.jsp";
    }
}
