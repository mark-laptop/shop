package ru.ndg.shop.filter;

import org.springframework.data.jpa.domain.Specification;
import ru.ndg.shop.entity.Customer;

import java.util.Map;

public class CustomerFilter {

    private Specification<Customer> specification;
    private Map<String, String> params;

    public CustomerFilter(Map<String, String> params) {
        this.params = params;
        this.specification = Specification.where(null);
    }

    public Specification<Customer> getSpecification() {
        if (params == null) return specification;

        StringBuilder filtersOut = new StringBuilder("");

        String firstName = params.get("first_name");
        if (firstName != null && !firstName.isEmpty() && !Character.isWhitespace(firstName.charAt(0))) {
            specification = specification.and((Specification<Customer>) (root, query, builder) ->
                    builder.like(root.get("firstName"), String.format("%%%s%%", firstName)));
            filtersOut.append("&first_name=").append(firstName);
        }

        String lastName = params.get("last_name");
        if (lastName != null && !lastName.isEmpty() && !Character.isWhitespace(lastName.charAt(0))) {
            specification = specification.and((Specification<Customer>) (root, query, builder) ->
                    builder.like(root.get("lastName"), String.format("%%%s%%", lastName)));
            filtersOut.append("&last_name=").append(firstName);
        }

        String email = params.get("email");
        if (email != null && !email.isEmpty() && !Character.isWhitespace(email.charAt(0))) {
            specification = specification.and((Specification<Customer>) (root, query, builder) ->
                    builder.like(root.get("email"), String.format("%%%s%%", email)));
            filtersOut.append("&email=").append(firstName);
        }

        params.put("filtersOut", filtersOut.toString());
        return specification;
    }
}
