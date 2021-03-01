package com.tourism.controller.command;

import javax.servlet.http.HttpServletRequest;

/**
 * Command interface that maps http requests with page names
 *
 */
public interface Command {
    /**
     * @param request http request to server
     * @return page name or redirect to servlet
     */
    String execute(HttpServletRequest request);
}
