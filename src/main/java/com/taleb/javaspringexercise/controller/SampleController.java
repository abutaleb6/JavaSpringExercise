package com.taleb.javaspringexercise.controller;

import com.taleb.javaspringexercise.entity.Product;
import com.taleb.javaspringexercise.service.ExternalApiService;
import com.taleb.javaspringexercise.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SampleController {

    private final ProductService productService;
    private final ExternalApiService externalApiService;

    public SampleController(ProductService productService, ExternalApiService externalApiService) {
        this.productService = productService;
        this.externalApiService = externalApiService;
    }

    @GetMapping("/products")
    @Operation(summary = "products", description = "Returns List of Product")
    public ResponseEntity<List<Product>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(productService.getAllProducts(page, size));
    }

    @PostMapping("/products/create")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(product));
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return ResponseEntity.ok(productService.updateProduct(id, product));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/call-google")
    public String callGoogleApi() {
        return externalApiService.callExternalApi();  // Call the service to get data
    }
}

