package ru.ndg.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.ndg.shop.entity.Customer;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Long id;
    private Customer customer;
    private String recipient;
    private String address;
    private BigDecimal sum;
}
