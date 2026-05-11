package com.ecommerce.app.product.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.app.product.dto.CategoryResponse;
import com.ecommerce.app.product.dto.CreateCategoryRequest;
import com.ecommerce.app.product.dto.UpdateCategoryRequest;
import com.ecommerce.app.product.entity.Category;
import com.ecommerce.app.product.mapper.CategoryMapper;
import com.ecommerce.app.product.repository.CategoryRepository;
import com.ecommerce.app.product.service.CategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
	
	private final CategoryRepository categoryRepository;
//	private final CategoryMapper categoryMapper;  Hum mapper inject nhi kr skte kyuki CategoryMapper ka method static hai
	//And Static method ki beans nhi bn skti!
	
	//Create Category
	@Override
	public CategoryResponse createCategory(CreateCategoryRequest request) {
		
	    // Step 1: Duplicate Check
		if(categoryRepository.existsByName(request.getName())) {
		    throw new RuntimeException("Category already exists");
		}
		
	    // Step 2: Fetch Parent Category (Optional)
	    Category parentCategory = null;

	    if(request.getParentCategoryId() != null) {

	        parentCategory = categoryRepository.findById(
	                request.getParentCategoryId()
	        ).orElseThrow(() ->
	                new RuntimeException("Parent category not found"));
	    }
	    
	    // Step 3: Create Entity
	    Category category = Category.builder()
	    		.name(request.getName())
	    		.description(request.getDescription())
	    		.parentCategory(parentCategory)
	    		.build();
	    
	    // Step 4: Save
	    Category savedCategory = categoryRepository.save(category);
	    
	    // Step 5: Return Response DTO
	    return CategoryMapper.toResponse(savedCategory);
	}
	
	//Update Category
	@Override
	public CategoryResponse updateCategory(Long categoryId , UpdateCategoryRequest request) {
		
		//Check if it Exists or not
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(()-> new RuntimeException("Category Not Found"));
		
	    // Step 2: Duplicate Name Validation
	    if (!category.getName().equalsIgnoreCase(request.getName())
	            && categoryRepository.existsByName(request.getName())) {

	        throw new RuntimeException("Category name already exists");
	    }
	    
	    // Step 3: Parent Category Handling
	    Category parentCategory = null;
	    
	    if(request.getParentCategoryId() != null) {
	    	
	        // ❌ Prevent self-parenting
	        if (categoryId.equals(request.getParentCategoryId())) {
	            throw new RuntimeException(
	                    "Category cannot be parent of itself");
	        }
	        
	        parentCategory = categoryRepository.findById(
	                request.getParentCategoryId()
	        ).orElseThrow(() ->
	                new RuntimeException("Parent category not found"));
	    }
	    
		category.setName(request.getName());
		category.setDescription(request.getDescription());
	    category.setIsActive(request.getIsActive());
		category.setParentCategory(parentCategory);
		
		Category updatedCategory = categoryRepository.save(category);
		
		return CategoryMapper.toResponse(updatedCategory);
		
	}
	
	//Get All Category
	@Override
	public List<CategoryResponse> getAllCategory(){  
		List<Category> categories = categoryRepository.findAll();
		return categories.stream()
				.map(CategoryMapper::toResponse)
				.toList();
	}
	
	//Get Category By ID
	@Override
	public CategoryResponse getCategoryById(Long categoryId) {
		
		Category response = categoryRepository.findById(categoryId)
				.orElseThrow(()-> new RuntimeException("Category Not Found!"));
		return CategoryMapper.toResponse(response);
		
	}
	
	//Get All Parent Category
	
	//Get Sub-Categoris By Parent
	
	//Disable Category By ID
	@Override
	public void disableCategory(Long categoryId) {

	    // Step 1: Fetch Category
	    Category category = categoryRepository.findById(categoryId)
	            .orElseThrow(() ->
	                    new RuntimeException("Category not found"));

	    // Step 2: Already Disabled Check
	    if (!category.getIsActive()) {
	        throw new RuntimeException("Category already disabled");
	    }

	    // Step 3: Disable Category
	    category.setIsActive(false);

	    // Step 4: Save
	    categoryRepository.save(category);
	}
	
	//Disable Parent's Sub-Category
	
	//Delete Category By ID
	
	//Delete Parent's Sub-Category
	
}
