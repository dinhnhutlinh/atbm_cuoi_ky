package com.nhom10.broadstore.beans;

import java.time.LocalDateTime;

public class User {
    private String id;
    private String lastName;
    private String firstName;
    private String avatar;

    private String password;
    private String address;
    private String phone;
    private String email;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private int active;
    private int role;
    private String pubKey;

    public User() {
    }

    public User(String id, String lastName, String firstName, String avatar, String password, String address, String phone, String email, LocalDateTime createAt, LocalDateTime updateAt, int active, int role, String pubKey) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.avatar = avatar;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.active = active;
        this.role = role;
        this.pubKey = pubKey;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.lastName + " " + this.firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getPubKey() {
        return pubKey;
    }

    public void setPubKey(String pubKey) {
        this.pubKey = pubKey;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", avatar='" + avatar + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", mail='" + email + '\'' +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                ", active=" + active +
                ", role=" + role +
                ", pubKey='" + pubKey + '\'' +
                '}';
    }
}
