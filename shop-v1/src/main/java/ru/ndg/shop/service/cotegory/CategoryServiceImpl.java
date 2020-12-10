package ru.ndg.shop.service.cotegory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ndg.shop.config.mapper.MapperFacade;
import ru.ndg.shop.dto.CategoryDto;
import ru.ndg.shop.entity.Category;
import ru.ndg.shop.repository.CategoryRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final MapperFacade mapperFacade;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, MapperFacade mapperFacade) {
        this.categoryRepository = categoryRepository;
        this.mapperFacade = mapperFacade;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> getAll() {
        return mapperFacade.mapAsList(categoryRepository.findAll(), CategoryDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDto getById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Not found element by id: " + id));
        return mapperFacade.map(category, CategoryDto.class);
    }

    @Override
    @Transactional
    public CategoryDto save(CategoryDto categoryDto) {
        Category save = categoryRepository.save(mapperFacade.map(categoryDto, Category.class));
        return mapperFacade.map(save, CategoryDto.class);
    }

    @Override
    @Transactional
    public CategoryDto update(CategoryDto categoryDto) {
        Category save = categoryRepository.save(mapperFacade.map(categoryDto, Category.class));
        return mapperFacade.map(save, CategoryDto.class);
    }

    @Override
    @Transactional
    public void delete(Long id) {

    }
}
