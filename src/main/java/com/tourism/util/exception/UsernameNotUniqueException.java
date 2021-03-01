package com.tourism.util.exception;

/**
 * Runtime exception that is thrown if new username already exists in the database
 *
 * @see com.tourism.controller.command.auth.RegistrationCommand
 * @see com.tourism.controller.command.user.UserUpdateCommand
 */
public class UsernameNotUniqueException extends RuntimeException {
    public UsernameNotUniqueException() {
        super();
    }

    public UsernameNotUniqueException(String message) {
        super(message);
    }

    public UsernameNotUniqueException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsernameNotUniqueException(Throwable cause) {
        super(cause);
    }
}
