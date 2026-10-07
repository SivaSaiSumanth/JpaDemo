package com.example.demo.service;


import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Basic CRUD
    public Product createProduct(Product product){
        return productRepository.save(product);
    }

    public Optional<Product> getProductById(Long id){
        return productRepository.findById(id);
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

    // Derived Queries
    public List<Product> getProductsByCategory(String category){
        return productRepository.findByCategory(category);
    }

    public List<Product> getProductsByNameKeyword(String keyword){
        return productRepository.findByNameContainingIgnoreCaseOrderByPriceDesc(keyword);
    }

    //Custom Query
    public List<Product> getExpensiveProducts(double minPrice){
        return productRepository.findProductsMoreExpensiveThan(minPrice);
    }

    // Paging and Sorting
    public Page<Product> getProductsPaginated(int page, int size, String sortBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return productRepository.findAll(pageable);
    }

    // Category and price less than
    public List<Product> getProductsByCategoryAndPriceLessThan(String category, double price){
        return productRepository.findByCategoryAndPriceLessThan(category,price);
    }

    // Top 3 cheapest for a category
    public List<Product> getTop3CheapestByCategory(String category){
        return productRepository.findTop3ByCategoryOrderByPriceAsc(category);
    }

    // Products by category and price
    public List<Product> getProductsByCategoryAndPrice(String category, double price){
        return productRepository.findProductsByCategoryAndPriceNative(category,price);
    }

    // Sorted products
    public List<Product> getAllProductsSorted(String sortBy, String direction){
        Sort sort = direction.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();
        return productRepository.findAll(sort);
    }

}
