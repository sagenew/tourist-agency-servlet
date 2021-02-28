package com.tourism.model.service;

import com.tourism.controller.dto.DiscountDTO;
import com.tourism.model.dao.*;
import com.tourism.model.entity.Discount;
import com.tourism.model.entity.Order;
import com.tourism.model.entity.Tour;
import com.tourism.model.entity.User;
import com.tourism.model.entity.enums.OrderStatus;
import com.tourism.util.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;

public class OrderService {
    private static final Logger log = LogManager.getLogger();
    private final DaoFactory daoFactory = DaoFactory.getInstance();

    public List<Order> findAllOrdersPageable(int page, int size, String sortCol, String sortDir) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            OrderDao orderDao = daoFactory.createOrderDao(connection);
            return orderDao.findAllPageable(page, size, sortCol, sortDir);
        } catch (DaoException e) {
            log.warn("Can not get all orders", e);
        }
        return Collections.emptyList();
    }

    public Order findOrderById(long orderId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            OrderDao orderDao = daoFactory.createOrderDao(connection);
            return orderDao.findById(orderId).orElseThrow(() ->
                    new IllegalArgumentException("Invalid order id: " + orderId));
        } catch (DaoException e) {
            log.warn("Can not find order");
        }
        return null;
    }

    public void createOrder(long userId, long tourId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.createUserDao(connection);
            TourDao tourDao = daoFactory.createTourDao(connection);
            OrderDao orderDao = daoFactory.createOrderDao(connection);

            connection.beginTransaction();

            User user = userDao.findById(userId).orElseThrow(() ->
                    new IllegalArgumentException("Invalid user id: " + userId));
            Tour tour = tourDao.findById(tourId).orElseThrow(() ->
                    new IllegalArgumentException("Invalid tour id: " + tourId));
            Order order = Order.builder()
                    .user(user)
                    .tour(tour)
                    .status(OrderStatus.PENDING)
                    .price(tour.getPrice())
                    .discount(user.getDiscount())
                    .build();
            orderDao.create(order);

            connection.commit();
        } catch (DaoException e) {
            log.warn("Can not create order", e);
        }
    }

    public void markOrderAsPaid(long orderId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            OrderDao orderDao = daoFactory.createOrderDao(connection);
            UserDao userDao = daoFactory.createUserDao(connection);

            connection.beginTransaction();

            Order order = findOrderById(orderId);
            User user = order.getUser();
            Discount discount = getDiscount();

            order.setStatus(OrderStatus.PAID);
            user.setDiscount(calcDiscount(user.getDiscount(), discount.getStep(), discount.getThreshold()));

            orderDao.update(order);
            userDao.update(user);
            orderDao.updateOrdersDiscountByUser(user);

            connection.commit();
        } catch (DaoException e) {
            log.warn("Can not mark order as paid");
        }
    }

    public void markOrderAsDenied(long orderId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            OrderDao orderDao = daoFactory.createOrderDao(connection);

            connection.beginTransaction();

            Order order = findOrderById(orderId);
            order.setStatus(OrderStatus.DENIED);
            orderDao.update(order);

            connection.commit();
        } catch (DaoException e) {
            log.warn("Can not mark order as paid");
        }
    }

    public void deleteOrder(long id) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            OrderDao orderDao = daoFactory.createOrderDao(connection);
            orderDao.delete(id);
        } catch (DaoException e) {
            log.warn("Can not delete order", e);
        }
    }

    public long getNumberOfRecords() {
        try (DaoConnection connection = daoFactory.getConnection()) {
            OrderDao orderDao = daoFactory.createOrderDao(connection);
            return orderDao.getNumberOfRecords();
        } catch (DaoException e) {
            log.warn("Can not get number of orders", e);
        }
        return 0;
    }


    public Discount getDiscount() {
        try (DaoConnection connection = daoFactory.getConnection()) {
            DiscountDao discountDao = daoFactory.createDiscountDao(connection);
            return discountDao.get().orElseThrow(DaoException::new);
        } catch (DaoException e) {
            log.warn("Can not get discount", e);
        }
        return null;
    }

    public void setDiscount(DiscountDTO discountDTO) {
        Discount discount = Discount.builder()
                .step(discountDTO.getStep())
                .threshold(discountDTO.getThreshold())
                .build();
        try (DaoConnection connection = daoFactory.getConnection()) {
            DiscountDao discountDao = daoFactory.createDiscountDao(connection);
            discountDao.set(discount);
        } catch (DaoException e) {
            log.warn("Can not create tour", e);
        }

    }

    private static Double calcDiscount(double currentDiscount, double step, double threshold) {
        return Math.min(currentDiscount + step, threshold);
    }
}
