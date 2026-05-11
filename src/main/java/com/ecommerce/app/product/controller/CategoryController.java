package com.ecommerce.app.product.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.app.product.dto.CategoryResponse;
import com.ecommerce.app.product.dto.CreateCategoryRequest;
import com.ecommerce.app.product.dto.UpdateCategoryRequest;
import com.ecommerce.app.product.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/category/")
@RequiredArgsConstructor
public class CategoryController {
	
	private final CategoryService categoryService;
	
	
//--------------------------Create category---------------------//
	@PostMapping("create")
	public ResponseEntity<CategoryResponse> createCategory(@RequestBody CreateCategoryRequest request){
		CategoryResponse response = categoryService.createCategory(request);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(response);
	}
	
//--------------------------Update Category---------------------//	
	@PutMapping("update/{categoryId}")
	public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long categoryId , @RequestBody UpdateCategoryRequest request){
		CategoryResponse response = categoryService.updateCategory(categoryId, request);
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(response);
	}
	
//--------------------------Get All Categories---------------------//	
	@GetMapping("getAll")
	public ResponseEntity<List<CategoryResponse>> getAllCategories(){
		List<CategoryResponse> responses = categoryService.getAllCategory();
		
		return ResponseEntity.ok(responses);
	}
	
//--------------------------Get Category By Id---------------------//	
	@GetMapping("get/{categoryId}")
	public ResponseEntity<CategoryResponse> getCatigoryById(@PathVariable Long categoryId){
		CategoryResponse response = categoryService.getCategoryById(categoryId);
		
		return ResponseEntity.ok(response);
	}
	
//--------------------------Disable Category By Id---------------------//
	@PatchMapping("/{id}/disable")
	public ResponseEntity<String> disableCategory(
	        @PathVariable Long id) {

	    categoryService.disableCategory(id);

	    return ResponseEntity.ok("Category disabled successfully");
	}
}
