package com.example.bookshop.service;

import com.example.bookshop.dto.OrderSettlementRequest;
import com.example.bookshop.dto.OrderSettlementResponse;
import com.example.bookshop.entity.Book;
import com.example.bookshop.entity.Cart;
import com.example.bookshop.entity.Order;
import com.example.bookshop.repository.BookRepository;
import com.example.bookshop.repository.CartRepository;
import com.example.bookshop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public OrderSettlementResponse settleOrder(OrderSettlementRequest request) {
        Long userId = request.getUserId();

        // 获取用户购物车
        List<Cart> carts = cartRepository.findByUserId(userId);
        if (carts.isEmpty()) {
            throw new IllegalArgumentException("购物车为空");
        }

        double totalAmount = 0.0;

        // 检查库存并计算总价
        for (Cart cart : carts) {
            Book book = bookRepository.findById(cart.getBookId()).orElseThrow(() -> new IllegalArgumentException("图书不存在"));
            if (!book.getIsActive()) {
                throw new IllegalArgumentException("图书已下架: " + book.getTitle());
            }
            if (book.getStock() < cart.getQuantity()) {
                throw new IllegalArgumentException("库存不足: " + book.getTitle());
            }
            totalAmount += book.getPrice() * cart.getQuantity();
        }

        // 扣减库存
        for (Cart cart : carts) {
            Book book = bookRepository.findById(cart.getBookId()).get();
            book.setStock(book.getStock() - cart.getQuantity());
            bookRepository.save(book);
        }

        // 创建订单
        Order order = new Order();
        order.setUserId(userId);
        order.setTotalAmount(totalAmount);
        order.setStatus("待付款");
        order.setCreateTime(LocalDateTime.now());
        order = orderRepository.save(order);

        // 清空购物车
        cartRepository.deleteAll(carts);

        OrderSettlementResponse response = new OrderSettlementResponse();
        response.setOrderId(order.getOrderId());
        response.setTotalAmount(totalAmount);
        response.setMessage("订单结算成功");

        return response;
    }
}
