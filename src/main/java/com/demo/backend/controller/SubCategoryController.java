package com.demo.backend.controller;

import com.demo.backend.dto.ApiResponseDto;
import com.demo.backend.model.SubCategory;
import com.demo.backend.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class SubCategoryController {

    @Autowired
    SubCategoryService subCategoryService;

    @GetMapping("/subcategories/{categoryId}")
    public List<SubCategory> getSubCategoriesByCategoryId(@PathVariable int categoryId){
        return subCategoryService.getSubCategoriesByCategory(categoryId);
    }

    @PostMapping("/subcategories")
    public ResponseEntity<ApiResponseDto> createSubcategory(@Valid @RequestBody SubCategory subCategory){
        subCategoryService.saveSubCategory(subCategory);
        return new ResponseEntity<ApiResponseDto>(new ApiResponseDto(true, "Sub Category has been added"), HttpStatus.CREATED);
    }

    @GetMapping("/subcategories")
    public List<SubCategory>  createSubcategory(){
        return subCategoryService.getAllSubCategories();
    }


}
