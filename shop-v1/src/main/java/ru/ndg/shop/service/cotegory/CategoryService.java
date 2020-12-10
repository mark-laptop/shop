package ru.ndg.shop.service.cotegory;

import ru.ndg.shop.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getAll();
    CategoryDto getById(Long id);
    CategoryDto save(CategoryDto categoryDto);
    CategoryDto update(CategoryDto categoryDto);
    void delete(Long id);
}
