package com.ecommerce.app.product.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ecommerce.app.product.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

    boolean existsByName(String name);

    List<Category> findByParentCategoryIsNull();
}
