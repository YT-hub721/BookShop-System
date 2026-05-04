package com.example.bookshop.controller;

import com.example.bookshop.dto.OrderSettlementRequest;
import com.example.bookshop.dto.OrderSettlementResponse;
import com.example.bookshop.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/settle")
    public ResponseEntity<OrderSettlementResponse> settleOrder(@Valid @RequestBody OrderSettlementRequest request) {
        // 订单结算接口：调用 service 处理结算，返回成功或错误信息
        try {
            OrderSettlementResponse response = orderService.settleOrder(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new OrderSettlementResponse() {{
                setMessage(e.getMessage());
            }});
        }
    }
}
