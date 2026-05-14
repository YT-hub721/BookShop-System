//package com.example.bookshop.repository;
//
//import com.example.bookshop.entity.Book;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface BookRepository extends JpaRepository<Book, Long> {
//    @Query("SELECT b FROM Book b WHERE (:category IS NULL OR b.category = :category) AND (:keyword IS NULL OR b.title LIKE %:keyword% OR b.author LIKE %:keyword%)")
//    List<Book> findBooks(@Param("category") String category, @Param("keyword") String keyword);
//}
package com.example.bookshop.repository;

import com.example.bookshop.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // 自定义查询：支持分类筛选 + 书名/作者模糊搜索
    @Query("SELECT b FROM Book b " +
            "WHERE (:category IS NULL OR b.category = :category) " +
            "AND (:keyword IS NULL OR b.title LIKE CONCAT('%', :keyword, '%') OR b.author LIKE CONCAT('%', :keyword, '%'))")
    List<Book> findBooks(
            @Param("category") String category,
            @Param("keyword") String keyword
    );
}