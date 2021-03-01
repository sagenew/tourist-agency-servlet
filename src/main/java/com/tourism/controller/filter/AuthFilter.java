package com.tourism.controller.filter;

import com.tourism.model.entity.User;
import com.tourism.model.entity.enums.Authority;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Authentication filter
 */
public class AuthFilter implements Filter {
    private static final Logger log = LogManager.getLogger();
    /**
     * Paths that can visit user with ADMIN authority
     */
    private final List<String> adminPaths = Arrays.asList(
            "/",
            "/index",
            "/logout",
            "/profile",
            "/users",
            "/users/delete",
            "/users/update",
            "/users/ban",
            "/users/unban",
            "/tours",
            "/tours/add",
            "/tours/update",
            "/tours/delete",
            "/tours/mark-hot",
            "/tours/orders",
            "/tours/orders/add",
            "/tours/orders/mark-paid",
            "/tours/orders/mark-denied",
            "/tours/orders/delete",
            "/tours/orders/set-discount");
    /**
     * Paths that can visit user with MANAGER authority
     */
    private final List<String> managerPaths = Arrays.asList(
            "/",
            "/index",
            "/logout",
            "/profile",
            "/tours",
            "/tours/mark-hot",
            "/tours/orders",
            "/tours/orders/add",
            "/tours/orders/mark-paid",
            "/tours/orders/mark-denied",
            "/tours/orders/delete",
            "/tours/orders/set-discount");
    /**
     * Paths that can visit user with USER authority
     */
    private final List<String> userPaths = Arrays.asList(
            "/",
            "/index",
            "/logout",
            "/profile",
            "/tours",
            "/tours/orders/add");
    /**
     * Paths that can visit unauthenticated user
     *
     */
    private final List<String> defaultPaths = Arrays.asList(
            "/",
            "/index",
            "/login",
            "/registration",
            "/tours");
    /**
     * Map with paths that user with various authorities can visit
     */
    private final Map<Authority, List<String>> authPaths = new HashMap<>();

    /**
     * Init filter and authentication paths
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        authPaths.put(Authority.USER, userPaths);
        authPaths.put(Authority.MANAGER, managerPaths);
        authPaths.put(Authority.ADMIN, adminPaths);
    }

    /**
     * Filter method to check if user is allowed to visit entered path
     * If not, redirect to login page
     * If path is invalid, forward to error page
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession();
        String requestURI = request.getRequestURI().replaceFirst(request.getContextPath() + "/app", "");
        log.info("Current request URI: " + requestURI);

        User user = (User) session.getAttribute("authUser");

        if (Objects.isNull(user)) {
            if (defaultPaths.contains(requestURI)) {
                filterChain.doFilter(request, response);
            } else {
                response.sendRedirect(request.getContextPath() +
                        request.getServletPath() +
                        "/login");
            }
            return;
        }

        List<String> paths = user.getAuthorities()
                .stream()
                .flatMap(authority -> authPaths.get(authority).stream())
                .distinct()
                .collect(Collectors.toList());

        if (paths.contains(requestURI)) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(403);
            request.getRequestDispatcher("/WEB-INF/error/403.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
