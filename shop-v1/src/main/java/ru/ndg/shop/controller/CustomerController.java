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
import ru.ndg.shop.dto.CustomerDto;
import ru.ndg.shop.service.customer.CustomerService;

import java.util.Map;

@Controller
@RequestMapping(value = "/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public String showCustomersPage(Model model,
                                    @RequestParam(name = "p", required = false) Integer page,
                                    @RequestParam(required = false) Map<String, String> params) {
        model.addAttribute("customersPage", customerService.getAll(page, params));
        model.addAttribute("filters_out", params.get("filtersOut"));
        return "customers";
    }

    @GetMapping(value = "/{id}")
    public String showUpdateCustomerPage(Model model, @PathVariable(name = "id") Long id) {
        model.addAttribute("customer", customerService.getById(id));
        return "update_customer";
    }

    @GetMapping(value = "/new")
    public String showNewCustomerPage(Model model) {
        model.addAttribute("customer", new CustomerDto());
        return "new_customer";
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteCustomer(@PathVariable(name = "id") Long id) {
        customerService.delete(id);
        return "redirect:/customers";
    }

    @PostMapping(value = "/new")
    public String createCustomers(@ModelAttribute CustomerDto customerDto) {
        customerService.saveOrUpdate(customerDto);
        return "redirect:/customers";
    }

    @PostMapping(value = "/update")
    public String updateCustomers(@ModelAttribute CustomerDto customerDto) {
        customerService.saveOrUpdate(customerDto);
        return "redirect:/customers";
    }
}
