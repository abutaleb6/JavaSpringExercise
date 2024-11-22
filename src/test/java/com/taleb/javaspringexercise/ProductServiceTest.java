package com.taleb.javaspringexercise;

import com.taleb.javaspringexercise.entity.Category;
import com.taleb.javaspringexercise.entity.Product;
import com.taleb.javaspringexercise.repository.CategoryRepository;
import com.taleb.javaspringexercise.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void testCreateProduct() {
        Category category = new Category(1L, "Electronics");
        category = categoryRepository.save(category);

        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(100);
        product.setCategory(category);
        Product saved = productService.createProduct(product);
        Assertions.assertNotNull(saved.getId());
    }
}
