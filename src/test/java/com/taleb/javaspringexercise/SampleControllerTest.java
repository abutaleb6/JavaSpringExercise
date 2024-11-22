package com.taleb.javaspringexercise;

import com.taleb.javaspringexercise.entity.Category;
import com.taleb.javaspringexercise.entity.Product;
import com.taleb.javaspringexercise.repository.CategoryRepository;
import com.taleb.javaspringexercise.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SampleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void testGetAllProducts() throws Exception {
        // Mock the service response
        Category category = new Category(1L, "Electronics");
        category = categoryRepository.save(category);
        List<Product> mockProducts = Arrays.asList(
                new Product(1L, "Product 1", 100.0, category),
                new Product(2L, "Product 2", 200.0, category)
        );
        Mockito.when(productService.getAllProducts(0, 10)).thenReturn(mockProducts);

        // Perform a GET request
        mockMvc.perform(get("/api/products")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Product 1")))
                .andExpect(jsonPath("$[1].price", is(200.0)));
    }

    @Test
    void testCreateProduct() throws Exception {
        Category category = new Category(1L, "Electronics");
        category = categoryRepository.save(category);
        Product mockProduct = new Product(1L, "New Product", 150.0, category);
        Mockito.when(productService.createProduct(any(Product.class))).thenReturn(mockProduct);

        // Perform a POST request
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"New Product\", \"price\": 150.0}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("New Product")))
                .andExpect(jsonPath("$.price", is(150.0)));
    }

    @Test
    void testUpdateProduct() throws Exception {
        Category category = new Category(1L, "Electronics");
        category = categoryRepository.save(category);
        Product updatedProduct = new Product(1L, "Updated Product", 200.0, category);
        Mockito.when(productService.updateProduct(anyLong(), any(Product.class))).thenReturn(updatedProduct);

        // Perform a PUT request
        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Updated Product\", \"price\": 200.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Updated Product")))
                .andExpect(jsonPath("$.price", is(200.0)));
    }

    @Test
    void testDeleteProduct() throws Exception {
        Mockito.doNothing().when(productService).deleteProduct(anyLong());

        // Perform a DELETE request
        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent());
    }
}
