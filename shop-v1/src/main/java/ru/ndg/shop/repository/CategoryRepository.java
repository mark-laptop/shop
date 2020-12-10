package ru.ndg.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ndg.shop.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
