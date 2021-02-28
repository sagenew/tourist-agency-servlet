package com.tourism.controller.dto;

public class RegistrationUserDTO {
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String bio;

    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder {
        private String username;
        private String password;
        private String fullName;
        private String email;
        private String bio;

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

        public RegistrationUserDTO build() {
            RegistrationUserDTO userDTO = new RegistrationUserDTO();
            userDTO.setUsername(username);
            userDTO.setPassword(password);
            userDTO.setFullName(fullName);
            userDTO.setEmail(email);
            userDTO.setBio(bio);
            return userDTO;
        }
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
}
