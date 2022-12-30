package com.nhom10.broadstore.beans;

import java.time.LocalDateTime;
import java.util.Date;

public class OrderSignature {
    int id;
    String signature;
    String file;
    String orderId;
    LocalDateTime createAt;

    public OrderSignature() {
    }

    public OrderSignature(int id, String signature, String file, String orderId, LocalDateTime createAt) {
        this.id = id;
        this.signature = signature;
        this.file = file;
        this.orderId = orderId;
        this.createAt = createAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }
}
