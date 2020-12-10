package ru.ndg.shop.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ndg.shop.config.mapper.MapperFacade;
import ru.ndg.shop.dto.ProductDto;
import ru.ndg.shop.entity.Category;
import ru.ndg.shop.entity.Product;
import ru.ndg.shop.filter.ProductFilter;
import ru.ndg.shop.repository.CategoryRepository;
import ru.ndg.shop.repository.ProductRepository;

import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final MapperFacade mapperFacade;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository,
                              MapperFacade mapperFacade) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.mapperFacade = mapperFacade;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDto> getAll(Integer page, Map<String, String> params) {

        page = correctPage(page);

        ProductFilter productFilter = new ProductFilter(params);
        Page<Product> productsPage = productRepository.findAll(productFilter.getSpecification(),
                PageRequest.of(page, 5));
        return productsPage.map(product -> mapperFacade.map(product, ProductDto.class));
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDto getById(Long id) {
        final Product product = productRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Not found by id: " + id));
        return mapperFacade.map(product, ProductDto.class);
    }

    @Override
    @Transactional
    public ProductDto saveOrUpdate(ProductDto productDto) {
        Category category = categoryRepository.findById(productDto.getCategory_id()).orElseThrow(() ->
                new NoSuchElementException("Not found category by id: " + productDto.getCategory_id()));
        Product product;
        if (productDto.getId() == null) {
            product = mapperFacade.map(productDto, Product.class);
        } else {
            product = productRepository.findById(productDto.getId()).orElseThrow(() ->
                    new NoSuchElementException("Not found category by id: " + productDto.getId()));
        }
        mapperFacade.mapObject(productDto, product);
        product.setCategory(category);
        Product save = productRepository.save(product);
        return mapperFacade.map(save, ProductDto.class);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Not found by id: " + id));
        productRepository.delete(product);
    }

    private Integer correctPage(Integer page) {
        if (page == null) {
            return 0;
        } else if (page < 1) {
            return 0;
        } else {
            return page - 1;
        }
    }
}
