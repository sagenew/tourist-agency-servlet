package com.tourism.controller.dto;

import com.tourism.model.entity.enums.Authority;

import java.util.Set;

public class UpdateUserDTO {
    private long id;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String bio;
    private double discount;
    private Set<Authority> authorities;

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
        private Set<Authority> authorities;

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

        public Builder authorities(Set<Authority> authorities) {
            this.authorities = authorities;
            return this;
        }

        public UpdateUserDTO build() {
            UpdateUserDTO userDTO = new UpdateUserDTO();
            userDTO.setId(id);
            userDTO.setUsername(username);
            userDTO.setPassword(password);
            userDTO.setFullName(fullName);
            userDTO.setEmail(email);
            userDTO.setBio(bio);
            userDTO.setDiscount(discount);
            userDTO.setAuthorities(authorities);
            return userDTO;
        }
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

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }
}
