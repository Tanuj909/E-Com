package com.ecommerce.app.product.dto;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponse {

    private Long id;

    private String name;

    private String description;

    private Boolean isActive;

    // Parent Info
    private Long parentCategoryId;

    private String parentCategoryName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}