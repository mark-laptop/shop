package ru.ndg.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ndg.shop.dto.ProductDto;
import ru.ndg.shop.service.cotegory.CategoryService;
import ru.ndg.shop.service.product.ProductService;

import java.util.Map;

@Controller
@RequestMapping(value = "/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String showProductsPage(Model model,
                                   @RequestParam(name = "p", required = false) Integer page,
                                   @RequestParam(required = false) Map<String, String> params) {
        model.addAttribute("productsPage", productService.getAll(page, params));
        model.addAttribute("filters_out", params.get("filtersOut"));
        return "products";
    }

    @GetMapping(value = "/new")
    public String showNewProductPage(Model model) {
        model.addAttribute("product", new ProductDto());
        model.addAttribute("categories", categoryService.getAll());
        return "new_product";
    }

    @GetMapping(value = "/{id}")
    public String showUpdateProductPage(Model model, @PathVariable(name = "id") Long id) {
        model.addAttribute("product", productService.getById(id));
        model.addAttribute("categories", categoryService.getAll());
        return "update_product";
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") Long id) {
        productService.delete(id);
        return "redirect:/products";
    }

    @PostMapping(value = "/new")
    public String createProduct(@ModelAttribute ProductDto productDto) {
        productService.saveOrUpdate(productDto);
        return "redirect:/products";
    }

    @PostMapping(value = "/update")
    public String updateProduct(@ModelAttribute ProductDto productDto) {
        productService.saveOrUpdate(productDto);
        return "redirect:/products";
    }
}
