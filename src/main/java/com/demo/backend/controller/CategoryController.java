package com.demo.backend.controller;

import com.demo.backend.dto.ApiResponseDto;
import com.demo.backend.model.Category;
import com.demo.backend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    CategoryService service;

    @PostMapping("/categories")
    public ResponseEntity<ApiResponseDto> addProduct(@Valid @RequestBody Category category) {
         service.saveProduct(category);
        return new ResponseEntity<ApiResponseDto>(new ApiResponseDto(true, "Category has been added"), HttpStatus.CREATED);

    }

    @GetMapping("/categories")
    public List<Category> listAllCategories(){
        return service.listAllCategories();
    }


}
