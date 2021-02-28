package com.tourism.model.service;

import com.tourism.controller.dto.RegistrationUserDTO;
import com.tourism.controller.dto.UpdateUserDTO;
import com.tourism.model.dao.DaoConnection;
import com.tourism.model.dao.DaoFactory;
import com.tourism.model.dao.UserDao;
import com.tourism.model.entity.User;
import com.tourism.model.entity.enums.Authority;
import com.tourism.util.exception.DaoException;
import com.tourism.util.exception.UsernameNotUniqueException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UserService {
    private static final Logger log = LogManager.getLogger();
    private final DaoFactory daoFactory = DaoFactory.getInstance();

    public List<User> findAllUsersPageable(int page, int size) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.createUserDao(connection);
            return userDao.findAllPageable(page, size);
        } catch (DaoException e) {
            log.warn("Can not get all users", e);
            return Collections.emptyList();
        }
    }

    public User findUserById(long id) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.createUserDao(connection);
            return userDao.findById(id).orElseThrow(() ->
                    new IllegalArgumentException("Invalid user id: " + id));
        } catch (DaoException e) {
            log.warn("Can not find user with id: " + id);
        }
        return null;
    }

    public Optional<User> findUserByUsername(String username) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.createUserDao(connection);
            log.info("Trying to get user by username: " + username);
            return userDao.findByUsername(username);
        } catch (DaoException e) {
            log.warn("Can not find user with username: " + username);
            return Optional.empty();
        }
    }

    public void registerUser(RegistrationUserDTO userDTO) throws UsernameNotUniqueException {
        User user = User.builder()
                .username(userDTO.getUsername())
                .password(DigestUtils.md5Hex(userDTO.getPassword()))
                .fullName(userDTO.getFullName())
                .email(userDTO.getEmail())
                .bio(userDTO.getBio())
                .discount(0)
                .enabled(true)
                .authorities(Collections.singleton(Authority.USER))
                .build();
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.createUserDao(connection);
            userDao.create(user);
            log.info("User {} successfully registered", user.getUsername());
        } catch (DaoException e) {
            log.warn("Can not register user: " + user);
            if (e.getCause() instanceof SQLException) {
                SQLException causeException = (SQLException) e.getCause();
                if (causeException.getSQLState().equals("23505")) {
                    log.warn("Login {} not unique", user.getUsername());
                    throw new UsernameNotUniqueException();
                }
            }
        }
    }

    public void updateUser(UpdateUserDTO userDTO) throws UsernameNotUniqueException {
        User user = User.builder()
                .id(userDTO.getId())
                .username(userDTO.getUsername())
                .password(DigestUtils.md5Hex(userDTO.getPassword()))
                .fullName(userDTO.getFullName())
                .email(userDTO.getEmail())
                .bio(userDTO.getBio())
                .discount(userDTO.getDiscount())
                .authorities(userDTO.getAuthorities())
                .build();
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.createUserDao(connection);
            if (userDTO.getPassword().isBlank()) {
                String oldPassword = findUserById(userDTO.getId()).getPassword();
                user.setPassword(oldPassword);
            }
            userDao.update(user);
        } catch (DaoException e) {
            log.warn("Can not update user " + user.getUsername(), e);
            if (e.getCause() instanceof SQLException) {
                SQLException causeException = (SQLException) e.getCause();
                if (causeException.getSQLState().equals("23505")) {
                    log.warn("Login {} not unique", user.getUsername());
                    throw new UsernameNotUniqueException(e);
                }
            }
        }
    }

    public void banUser(long id) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.createUserDao(connection);
            userDao.updateEnabled(id, false);
        } catch (DaoException e) {
            log.warn("Can not ban user");
        }
    }

    public void unbanUser(long id) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.createUserDao(connection);
            userDao.updateEnabled(id, true);
        } catch (DaoException e) {
            log.warn("Can not unban user");
        }
    }

    public void deleteUserById(long id) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.createUserDao(connection);
            userDao.delete(id);
        } catch (DaoException e) {
            log.warn("Can not delete user");
        }
    }

    public long getNumberOfRecords() {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.createUserDao(connection);
            return userDao.getNumberOfRecords();
        } catch (DaoException e) {
            log.warn("Can not get number of users", e);
        }
        return 0;
    }
}
