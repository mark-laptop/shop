package ru.ndg.shop.service.customer;

import org.springframework.data.domain.Page;
import ru.ndg.shop.dto.CustomerDto;

import java.util.Map;

public interface CustomerService {
    Page<CustomerDto> getAll(Integer page, Map<String, String> params);
    CustomerDto getById(Long id);
    CustomerDto saveOrUpdate(CustomerDto customerDto);
    void delete(Long id);
}
