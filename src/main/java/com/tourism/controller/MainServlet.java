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
/**
 * Main servlet handling all requests to server
 * and distributes them on commands
 *
 */
public class MainServlet extends HttpServlet {
    /**
     * Log4j2 logger
     */
    private static final Logger log = LogManager.getLogger();
    /**
     * Command manager that gets the suitable command
     *
     * @see CommandManager
     */
    private final CommandManager commandManager = CommandManager.getInstance();

    /**
     * Handle POST requests to server
     *
     * @param request  User http request to server
     * @param response Http response from server to user
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        process(request, response);
    }

    /**
     * Handle GET requests to server
     *
     * @param request  User http request to server
     * @param response Http response from server to user
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        process(request, response);
    }

    /**
     * Parse user request and find suitable command to execute using Command Manager
     *
     * @param request  User http request to server
     * @param response Http response from server to user
     */
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