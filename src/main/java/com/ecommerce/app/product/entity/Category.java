package com.ecommerce.app.product.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔹 Category Name
    @Column(nullable = false, unique = true)
    private String name;

    // 🔹 Optional Description
    @Column(columnDefinition = "TEXT")
    private String description;

    // 🔹 Self Reference (Parent Category)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parentCategory;

    // 🔹 Child Categories
    @OneToMany(mappedBy = "parentCategory",
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    @Builder.Default
    private Set<Category> subCategories = new HashSet<>();

    // 🔹 Active / Inactive
    @Column(nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    // 🔹 Audit Fields
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}