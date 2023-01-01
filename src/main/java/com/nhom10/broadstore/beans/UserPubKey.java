package com.nhom10.broadstore.beans;

import java.time.LocalDateTime;

public class UserPubKey {
    int id;
    String pubKey;
    int status;
    LocalDateTime createAt;
    String userId;

    public UserPubKey() {
    }

    public UserPubKey(int id, String pubKey, int status, LocalDateTime createAt) {
        this.id = id;
        this.pubKey = pubKey;
        this.status = status;
        this.createAt = createAt;
    }

    public UserPubKey(int id, String pubKey, int status, LocalDateTime createAt, String userId) {
        this.id = id;
        this.pubKey = pubKey;
        this.status = status;
        this.createAt = createAt;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPubKey() {
        return pubKey;
    }

    public void setPubKey(String pubKey) {
        this.pubKey = pubKey;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }
}
