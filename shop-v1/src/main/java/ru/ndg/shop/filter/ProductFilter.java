package ru.ndg.shop.filter;

import org.springframework.data.jpa.domain.Specification;
import ru.ndg.shop.entity.Product;

import java.math.BigDecimal;
import java.util.Map;

public class ProductFilter {

    private Specification<Product> spec;
    private Map<String, String> params;

    public ProductFilter(Map<String, String> params) {
        this.params = params;
        this.spec = Specification.where(null);
    }

    public Specification<Product> getSpecification() {
        if (params == null) return spec;

        StringBuilder filtersOut = new StringBuilder("");

        String maxPriceParam = params.get("max_price");
        if (maxPriceParam != null && !maxPriceParam.isEmpty() && !Character.isWhitespace(maxPriceParam.charAt(0))) {
            double maxPrice;
            try {
                maxPrice = Double.parseDouble(maxPriceParam.trim());
                spec = spec.and((Specification<Product>) (root, query, builder) ->
                        builder.lessThanOrEqualTo(root.get("price"), new BigDecimal(maxPrice)));
                filtersOut.append("&max_price=").append(maxPriceParam);
            } catch (NumberFormatException ignore) {}
        }

        String minPriceParam = params.get("min_price");
        if (minPriceParam != null && !minPriceParam.isEmpty() && !Character.isWhitespace(minPriceParam.charAt(0))) {
            double minPrice;
            try {
                minPrice = Double.parseDouble(minPriceParam.trim());
                spec = spec.and((Specification<Product>) (root, query, builder) ->
                        builder.greaterThanOrEqualTo(root.get("price"), new BigDecimal(minPrice)));
                filtersOut.append("&min_price=").append(minPriceParam);
            } catch (NumberFormatException ignore) {}
        }

        String titleParam = params.get("title");
        if (titleParam != null && !titleParam.isEmpty() && !Character.isWhitespace(titleParam.charAt(0))) {
            spec = spec.and((Specification<Product>) (root, query, builder) ->
                    builder.like(root.get("title"), String.format("%%%s%%", titleParam)));
            filtersOut.append("&title=").append(titleParam);
        }

        String categoryParam = params.get("category");
        if (categoryParam != null && !categoryParam.isEmpty() && !Character.isWhitespace(categoryParam.charAt(0))) {
            spec = spec.and((Specification<Product>) (root, query, builder) ->
                    builder.like(root.get("category").get("title"), String.format("%%%s%%", categoryParam)));
            filtersOut.append("&category=").append(categoryParam);
        }

        params.put("filtersOut", filtersOut.toString());
        return spec;
    }
}
