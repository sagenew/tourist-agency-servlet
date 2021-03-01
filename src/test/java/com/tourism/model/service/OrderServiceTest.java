package com.tourism.model.service;

import com.tourism.controller.dto.DiscountDTO;
import com.tourism.model.dao.*;
import com.tourism.model.entity.Discount;
import com.tourism.model.entity.Order;
import com.tourism.model.entity.Tour;
import com.tourism.model.entity.User;
import com.tourism.model.entity.enums.Authority;
import com.tourism.model.entity.enums.HotelType;
import com.tourism.model.entity.enums.OrderStatus;
import com.tourism.model.entity.enums.TourType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest( { OrderService.class, LogManager.class, DaoFactory.class } )
public class OrderServiceTest {
    OrderService orderService;
    Logger logMock;
    DaoFactory daoFactoryMock;

    @Before
    public void create() {
        mockStatic(LogManager.class);
        logMock = mock(Logger.class);
        when(LogManager.getLogger()).thenReturn(logMock);

        mockStatic(DaoFactory.class);
        daoFactoryMock = mock(DaoFactory.class);
        when(DaoFactory.getInstance()).thenReturn(daoFactoryMock);

        DaoConnection daoConnectionMock = mock(DaoConnection.class);
        when(daoFactoryMock.getConnection()).thenReturn(daoConnectionMock);
        when(daoFactoryMock.getConnection()).thenReturn(daoConnectionMock);

        orderService = new OrderService();
    }

    @Test
    public void createOrder() {
        UserDao userDaoMock = mock(UserDao.class);
        TourDao tourDaoMock = mock(TourDao.class);
        OrderDao orderDaoMock = mock(OrderDao.class);

        when(daoFactoryMock.createUserDao(any(DaoConnection.class))).thenReturn(userDaoMock);
        when(daoFactoryMock.createTourDao(any(DaoConnection.class))).thenReturn(tourDaoMock);
        when(daoFactoryMock.createOrderDao(any(DaoConnection.class))).thenReturn(orderDaoMock);

        User user = initTestUser();
        Tour tour = initTestTour();

        when(userDaoMock.findById(user.getId())).thenReturn(Optional.of(user));
        when(tourDaoMock.findById(tour.getId())).thenReturn(Optional.of(tour));

        orderService.createOrder(user.getId(), tour.getId());

        Order order = Order.builder()
                .tour(tour)
                .user(user)
                .status(OrderStatus.PENDING)
                .price(tour.getPrice())
                .discount(user.getDiscount())
                .build();

        verify(userDaoMock, times(1)).findById(user.getId());
        verify(tourDaoMock, times(1)).findById(tour.getId());
        verify(orderDaoMock, times(1)).create(order);
    }

    @Test
    public void markOrderAsPending() {
        OrderDao orderDaoMock = mock(OrderDao.class);
        UserDao userDaoMock = mock(UserDao.class);
        DiscountDao discountDaoMock = mock(DiscountDao.class);

        when(daoFactoryMock.createOrderDao(any(DaoConnection.class))).thenReturn(orderDaoMock);
        when(daoFactoryMock.createUserDao(any(DaoConnection.class))).thenReturn(userDaoMock);
        when(daoFactoryMock.createDiscountDao(any(DaoConnection.class))).thenReturn(discountDaoMock);

        User user = initTestUser();
        Tour tour = initTestTour();
        Discount discount = initTestDiscount();

        Order order = Order.builder()
                .id(1L)
                .user(user)
                .tour(tour)
                .status(OrderStatus.PENDING)
                .price(tour.getPrice())
                .discount(user.getDiscount())
                .build();

        when(orderDaoMock.findById(order.getId())).thenReturn(Optional.of(order));
        when(discountDaoMock.get()).thenReturn(Optional.of(discount));

        orderService.markOrderAsPaid(order.getId());

        verify(orderDaoMock, times(1)).update(order);
        verify(userDaoMock, times(1)).update(user);
        verify(orderDaoMock, times(1)).updateOrdersDiscountByUser(user);
    }

    @Test
    public void markOrderAsDenied() {
        OrderDao orderDaoMock = mock(OrderDao.class);

        when(daoFactoryMock.createOrderDao(any(DaoConnection.class))).thenReturn(orderDaoMock);

        User user = initTestUser();
        Tour tour = initTestTour();

        Order order = Order.builder()
                .id(1L)
                .user(user)
                .tour(tour)
                .status(OrderStatus.PENDING)
                .price(tour.getPrice())
                .discount(user.getDiscount())
                .build();

        when(orderDaoMock.findById(order.getId())).thenReturn(Optional.of(order));

        orderService.markOrderAsDenied(order.getId());

        verify(orderDaoMock, times(1)).update(order);
    }

    @Test
    public void deleteOrder() {
        OrderDao orderDaoMock = mock(OrderDao.class);

        when(daoFactoryMock.createOrderDao(any(DaoConnection.class))).thenReturn(orderDaoMock);

        User user = initTestUser();
        Tour tour = initTestTour();

        Order order = Order.builder()
                .id(1L)
                .user(user)
                .tour(tour)
                .status(OrderStatus.PENDING)
                .price(tour.getPrice())
                .discount(user.getDiscount())
                .build();

        orderService.deleteOrder(order.getId());

        verify(orderDaoMock, times(1)).delete(order.getId());
    }

    @Test
    public void getNumberOfRecords() {
        OrderDao orderDaoMock = mock(OrderDao.class);

        when(daoFactoryMock.createOrderDao(any(DaoConnection.class))).thenReturn(orderDaoMock);

        orderService.getNumberOfRecords();

        verify(orderDaoMock, times(1)).getNumberOfRecords();
    }

    @Test
    public void getDiscount() {
        DiscountDao discountDaoMock = mock(DiscountDao.class);
        when(daoFactoryMock.createDiscountDao(any(DaoConnection.class))).thenReturn(discountDaoMock);

        orderService.getDiscount();

        verify(discountDaoMock, times(1)).get();
    }

    @Test
    public void setDiscount() {
        DiscountDao discountDaoMock = mock(DiscountDao.class);
        when(daoFactoryMock.createDiscountDao(any(DaoConnection.class))).thenReturn(discountDaoMock);

        DiscountDTO discountDTO = DiscountDTO.builder()
                .step(1.5)
                .threshold(20)
                .build();

        orderService.setDiscount(discountDTO);

        verify(discountDaoMock, times(1)).set(any(Discount.class));
    }

    @Test
    public void calcDiscount() {
        assertEquals(24.0, OrderService.calcDiscount(19.0, 5.0, 50.0), 0);
        assertEquals(10.0, OrderService.calcDiscount(20.0, 100.0, 10.0), 0);
        assertEquals(20.0, OrderService.calcDiscount(19.0, 5.0, 20.0), 0);
    }

    private static User initTestUser() {
        return User.builder()
                .id(1L)
                .username("user")
                .fullName("Userino Userious")
                .email("user@gmail.com")
                .bio("21 y.o. designer from San Francisco")
                .discount(5.0)
                .authorities(Collections.singleton(Authority.USER))
                .build();
    }

    private static Tour initTestTour() {
        return Tour.builder()
                .id(1L)
                .name("Test tour")
                .type(TourType.EXCURSION)
                .price(200L)
                .groupSize(10)
                .hotel(HotelType.APARTMENTS)
                .description("Test tour description")
                .isHot(false)
                .build();
    }

    private static Discount initTestDiscount() {
        return Discount.builder().step(1.5).threshold(20).build();
    }
}
