package ru.ndg.shop.service.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ndg.shop.config.mapper.MapperFacade;
import ru.ndg.shop.dto.CustomerDto;
import ru.ndg.shop.entity.Customer;
import ru.ndg.shop.filter.CustomerFilter;
import ru.ndg.shop.repository.CustomerRepository;

import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final MapperFacade mapperFacade;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, MapperFacade mapperFacade) {
        this.customerRepository = customerRepository;
        this.mapperFacade = mapperFacade;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CustomerDto> getAll(Integer page, Map<String, String> params) {

        page = correctPage(page);

        CustomerFilter customerFilter = new CustomerFilter(params);

        Page<Customer> customersPage = customerRepository.findAll(customerFilter.getSpecification(), PageRequest.of(page, 5));
        return customersPage.map(customer -> mapperFacade.map(customer, CustomerDto.class));
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerDto getById(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Not found customer by id: " + id));
        return mapperFacade.map(customer, CustomerDto.class);
    }

    @Override
    @Transactional
    public CustomerDto saveOrUpdate(CustomerDto customerDto) {
        Customer customer;
        if (customerDto.getId() == null) {
            customer = mapperFacade.map(customerDto, Customer.class);
        } else {
            customer = customerRepository.findById(customerDto.getId()).orElseThrow(() ->
                    new NoSuchElementException("Not found customer by id: " + customerDto.getId()));
        }
        mapperFacade.mapObject(customerDto, customer);
        return mapperFacade.map(customerRepository.save(customer), CustomerDto.class);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        customerRepository.deleteById(id);
    }

    private Integer correctPage(Integer page) {
        if (page == null) return 0;
        if (page < 1) return 0;
        return page - 1;
    }
}
