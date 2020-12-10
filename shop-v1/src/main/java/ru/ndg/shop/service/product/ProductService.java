package ru.ndg.shop.service.product;

import org.springframework.data.domain.Page;
import ru.ndg.shop.dto.ProductDto;

import java.util.Map;

public interface ProductService {
    Page<ProductDto> getAll(Integer page, Map<String, String> params);
    ProductDto getById(Long id);
    ProductDto saveOrUpdate(ProductDto product);
    void delete(Long id);
}
