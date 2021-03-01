package com.tourism.util;

import com.tourism.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Util class for command functions
 */
public class CommandUtils {
    /**
     * Get locale from session set by LocalizationFilter
     */
    public static Locale getLocaleFromSession(HttpServletRequest request) {
        String lang = (String) request.getSession().getAttribute("lang");
        return lang != null ? Locale.forLanguageTag(lang) : Locale.getDefault();
    }
    /**
     * Get user from session set by LoginCommand
     */
    public static User getUserFromSession(HttpServletRequest request) {
        return (User) request.getSession().getAttribute("authUser");
    }
}

