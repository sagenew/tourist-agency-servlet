package com.tourism.controller;

import com.tourism.controller.command.Command;
import com.tourism.controller.command.CommandManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger();
    private final CommandManager commandManager = CommandManager.getInstance();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        process(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getRequestURI().replaceFirst(request.getContextPath() + "/app", "");
        Command command = commandManager.getCommand(path);
        log.info("Current command: " + command.getClass().getSimpleName());
        String page = command.execute(request);
        if (page.contains("redirect")) {
            response.sendRedirect(request.getContextPath() +
                    request.getServletPath() +
                    page.replace("redirect:", ""));
        } else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }
}