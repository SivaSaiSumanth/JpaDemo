package com.example.demo.web.rest;

import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Create a new Product
    @PostMapping
    public Product createProduct(@RequestBody Product product){
        return productService.createProduct(product);
    }

    // Get a single product by ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id){
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get all products
    @GetMapping
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    // Delete a product
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    // Get products by category
    @GetMapping("/category/{category}")
    public List<Product> getProductsByCategory(@PathVariable String category){
        return productService.getProductsByCategory(category);
    }

    // Get products by name keyword
    @GetMapping("/search")
    public List<Product> getProductsByName(@RequestParam String keyword){
        return productService.getProductsByNameKeyword(keyword);
    }

    // Get expensive products
    @GetMapping("/expensive")
    public List<Product> getExpensiveProducts(@RequestParam double minPrice){
        return  productService.getExpensiveProducts(minPrice);
    }

    // Get products with pagination
    @GetMapping("/paged")
    public Page<Product> getPaginatedProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy){
        return productService.getProductsPaginated(page,size,sortBy);
    }

    // Get cheaper products in a given category for a given price
    @GetMapping("/category/{category}/cheap")
    public List<Product> getProductsByCategoryAndPriceLessThan(
            @PathVariable String category,
            @RequestParam double maxPrice){
        return productService.getProductsByCategoryAndPriceLessThan(category,maxPrice);
    }

    // Get top 3 cheapest products in a category
    @GetMapping("/category/{category}/top3")
    public List<Product> getTop3CheapestProductsByCategory(@PathVariable String category){
        return productService.getTop3CheapestByCategory(category);
    }

    // Get products by category and price
    @GetMapping("/native-search")
    public List<Product> getProductsByCategoryAndPriceNative(
            @RequestParam String category,
            @RequestParam double price){
        return productService.getProductsByCategoryAndPrice(category,price);
    }

    // Get all products sorted by given field
    @GetMapping("/sorted")
    public List<Product> getAllProductsSorted(
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction){
        return productService.getAllProductsSorted(sortBy,direction);
    }

}
