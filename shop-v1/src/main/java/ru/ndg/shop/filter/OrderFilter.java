package ru.ndg.shop.filter;

import org.springframework.data.jpa.domain.Specification;
import ru.ndg.shop.entity.Order;

import java.util.Map;

public class OrderFilter {

    private Specification<Order> specification;
    private Map<String, String> params;

    public OrderFilter(Map<String, String> params) {
        this.params = params;
        this.specification = Specification.where(null);
    }

    public Specification<Order> getSpecification() {
        if (params == null) return specification;

        StringBuilder filtersOut = new StringBuilder("");

        String recipient = params.get("recipient");
        if (recipient != null && !recipient.isEmpty() && !Character.isWhitespace(recipient.charAt(0))) {
            specification = specification.and((Specification<Order>) (root, query, builder) ->
                    builder.like(root.get("recipient"), String.format("%%%s%%", recipient)));
            filtersOut.append("&recipient=").append(recipient);
        }

        String address = params.get("address");
        if (address != null && !address.isEmpty() && !Character.isWhitespace(address.charAt(0))) {
            specification = specification.and((Specification<Order>) (root, query, builder) ->
                    builder.like(root.get("address"), String.format("%%%s%%", address)));
            filtersOut.append("&address=").append(address);
        }

        params.put("filtersOut", filtersOut.toString());
        return specification;
    }
}
