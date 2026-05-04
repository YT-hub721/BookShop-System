package com.example.bookshop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class OrderSettlementRequest {
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @NotBlank(message = "收货地址不能为空")
    private String address;

    @NotBlank(message = "支付方式不能为空")
    private String paymentMethod;

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
