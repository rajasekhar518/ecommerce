package com.demo.backend.controller;

import com.demo.backend.dto.ApiResponseDto;
import com.demo.backend.exception.CustomValidationException;
import com.demo.backend.model.Product;
import com.demo.backend.service.CategoryService;
import com.demo.backend.service.ProductService;
import com.demo.backend.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    SubCategoryService subCategoryService;

    @GetMapping("/products")
    public List<Product> findAllProducts() {
        return productService.getProducts();
    }

    @GetMapping("/products/{id}")
    public Product findProductById(@PathVariable int id) {
        return productService.getProductById(id);
    }

    @GetMapping("/products/product")
    public List<Product> findProductBySubCategory(@RequestParam Integer subCategoryId) {
        return productService.getProductBySubCategoryId(subCategoryId);
    }


    @PostMapping("/products")
    public ResponseEntity<ApiResponseDto> addProduct(@RequestBody Product product) {
        if(!subCategoryService.existBySubCategory(product.getSubCategoryId()))
            throw new CustomValidationException("Invalid SubCategory Id");
        productService.saveProduct(product);
        return new ResponseEntity<ApiResponseDto>(new ApiResponseDto(true, "Product has been added"), HttpStatus.CREATED);
    }

    @DeleteMapping("/products/{id}")
    public String deleteProduct(@PathVariable int id) {
        return productService.deleteProduct(id);
    }

}
