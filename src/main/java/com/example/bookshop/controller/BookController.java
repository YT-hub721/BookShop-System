package com.example.bookshop.controller;

import com.example.bookshop.entity.Book;
import com.example.bookshop.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    /**
     * 获取所有书籍列表，支持 category 参数按类别筛选，支持 keyword 参数模糊匹配书名或作者
     * @param category 类别筛选参数
     * @param keyword 关键字模糊匹配参数
     * @return 书籍列表
     */
    @GetMapping
    public List<Book> getBooks(@RequestParam(required = false) String category, @RequestParam(required = false) String keyword) {
        return bookService.findBooks(category, keyword);
    }

    /**
     * 根据书籍ID查询单个商品详情
     * @param id 书籍ID
     * @return 书籍详情
     */
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookService.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
    }
}
