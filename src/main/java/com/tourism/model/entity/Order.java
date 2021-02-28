package com.tourism.model.entity;

import com.tourism.model.entity.enums.OrderStatus;

import java.util.Objects;

public class Order {
    private long id;
    private Tour tour;
    private User user;
    private OrderStatus status;
    private double price;
    private double discount;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private long id;
        private Tour tour;
        private User user;
        private OrderStatus status;
        private double price;
        private double discount;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder tour(Tour tour) {
            this.tour = tour;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder status(OrderStatus status) {
            this.status = status;
            return this;
        }

        public Builder price(double price) {
            this.price = price;
            return this;
        }

        public Builder discount(double discount) {
            this.discount = discount;
            return this;
        }

        public Order build() {
            Order order = new Order();
            order.setId(id);
            order.setTour(tour);
            order.setUser(user);
            order.setStatus(status);
            order.setPrice(price);
            order.setDiscount(discount);
            return order;
        }
    }

    public boolean isPending() {
        return status == OrderStatus.PENDING;
    }

    public boolean isPaid() {
        return status == OrderStatus.PAID;
    }

    public boolean isDenied() {
        return status == OrderStatus.DENIED;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getFixedPrice() {
        return price * (1 - (discount * 0.01));
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", tour=" + tour +
                ", user=" + user +
                ", status=" + status +
                ", price=" + price +
                ", discount=" + discount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id &&
                Double.compare(order.price, price) == 0 &&
                Double.compare(order.discount, discount) == 0 &&
                tour.equals(order.tour) &&
                user.equals(order.user) &&
                status == order.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tour, user, status, price, discount);
    }
}
