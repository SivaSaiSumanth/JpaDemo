package com.example.demo.repository;

import com.example.demo.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // 1. Find by single property
    List<Product> findByCategory(String category);

    // 2. Find using multiple properties with AND
    List<Product> findByCategoryAndPriceLessThan(String category, double price);

    // 3. FInd using IGNORE CASE and ORDER BY
    List<Product> findByNameContainingIgnoreCaseOrderByPriceDesc(String keyword);

    // 4. Find the top N results
    List<Product> findTop3ByCategoryOrderByPriceAsc(String category);

    // 5. Custom query using JPQL (JPA Query Language)
    @Query("select p from Product p where p.price > :minPrice")
    List<Product> findProductsMoreExpensiveThan(@Param("minPrice") double price);

    // 6. Custom query using native SQL
    @Query(value = "select * from product where category = ?1 and price = ?2", nativeQuery = true)
    List<Product> findProductsByCategoryAndPriceNative(String category, double price);

    // 7. Find all with custom sorting
    List<Product> findAll(Sort sort);

    // 8. Find all with pagination
    Page<Product> findAll(Pageable pageable);

}
