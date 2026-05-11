package com.ecommerce.app.product.mapper;

import com.ecommerce.app.product.dto.CategoryResponse;
import com.ecommerce.app.product.entity.Category;

public class CategoryMapper {
	
    private CategoryMapper() {
    }
    
    public static CategoryResponse toResponse(Category category) {
    	
    	return CategoryResponse.builder()
    			.id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .isActive(category.getIsActive())
                .parentCategoryId(
                		category.getParentCategory()!= null
                                  ? category.getParentCategory().getId()
                		          : null)
                
                .parentCategoryName(
                        category.getParentCategory() != null
                                ? category.getParentCategory().getName()
                                : null
                )
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
    			.build();
    }
}
