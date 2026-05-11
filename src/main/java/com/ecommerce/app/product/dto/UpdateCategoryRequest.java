package com.ecommerce.app.product.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCategoryRequest {

    @NotBlank(message = "Category name is required")
    private String name;

    private String description;

    private Boolean isActive;

    private Long parentCategoryId;
}