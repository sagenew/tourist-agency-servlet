package com.tourism.model.entity;

import com.tourism.model.entity.enums.Authority;
import com.tourism.model.entity.enums.OrderStatus;

import java.util.*;

public class User {
    private long id;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String bio;
    private double discount;
    private boolean enabled;
    private Set<Authority> authorities = new HashSet<>();
    private List<Order> orders = new ArrayList<>();

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private long id;
        private String username;
        private String password;
        private String fullName;
        private String email;
        private String bio;
        private double discount;
        private boolean enabled;
        private Set<Authority> authorities = new HashSet<>();
        private List<Order> orders = new ArrayList<>();

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder bio(String bio) {
            this.bio = bio;
            return this;
        }

        public Builder discount(double discount) {
            this.discount = discount;
            return this;
        }

        public Builder enabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public Builder authorities(Set<Authority> authorities) {
            this.authorities = authorities;
            return this;
        }

        public Builder orders(List<Order> orders) {
            this.orders = orders;
            return this;
        }

        public User build() {
            User user = new User();
            user.setId(id);
            user.setUsername(username);
            user.setPassword(password);
            user.setFullName(fullName);
            user.setEmail(email);
            user.setBio(bio);
            user.setDiscount(discount);
            user.setEnabled(enabled);
            user.setAuthorities(authorities);
            user.setOrders(orders);
            return user;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Double.compare(user.discount, discount) == 0 &&
                enabled == user.enabled &&
                username.equals(user.username) &&
                password.equals(user.password) &&
                fullName.equals(user.fullName) &&
                email.equals(user.email) &&
                Objects.equals(bio, user.bio) &&
                Objects.equals(authorities, user.authorities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, fullName, email, bio, discount, enabled, authorities);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", bio='" + bio + '\'' +
                ", discount='" + discount + '\'' +
                ", enabled='" + enabled + '\'' +
                ", authorities=" + authorities +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public double getTotalOrdersPrice() {
        return orders.stream()
                .filter((x) -> x.getStatus() == OrderStatus.PENDING)
                .mapToDouble(Order::getFixedPrice)
                .sum();
    }
}
