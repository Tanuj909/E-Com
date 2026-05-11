package com.ecommerce.app.product.service;

import java.util.List;

import com.ecommerce.app.product.dto.CategoryResponse;
import com.ecommerce.app.product.dto.CreateCategoryRequest;
import com.ecommerce.app.product.dto.UpdateCategoryRequest;

public interface CategoryService {

	CategoryResponse createCategory(CreateCategoryRequest request);

	List<CategoryResponse> getAllCategory();

	CategoryResponse getCategoryById(Long categoryId);

	CategoryResponse updateCategory(Long categoryId, UpdateCategoryRequest request);

	void disableCategory(Long categoryId);

}
