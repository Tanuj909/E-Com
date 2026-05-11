package com.ecommerce.app.product.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCategoryRequest {

    @NotBlank(message = "Category name is required")
    private String name;

    private String description;

    // Optional Parent Category
    private Long parentCategoryId;
}
