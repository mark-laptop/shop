package ru.ndg.shop.service.order;

import org.springframework.data.domain.Page;
import ru.ndg.shop.dto.OrderDto;

import java.util.Map;

public interface OrderService {
    Page<OrderDto> getAll(Integer page, Map<String, String> params);
    OrderDto getById(Long id);
    OrderDto saveOrUpdate(OrderDto orderDto);
    void delete(Long id);
}
