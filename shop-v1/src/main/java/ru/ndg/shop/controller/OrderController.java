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
import ru.ndg.shop.dto.OrderDto;
import ru.ndg.shop.service.order.OrderService;

import java.util.Map;

@Controller
@RequestMapping(value = "/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String howOrdersPage(Model model,
                                @RequestParam(name = "p", required = false) Integer page,
                                @RequestParam(required = false) Map<String, String> params) {
        model.addAttribute("ordersPage", orderService.getAll(page, params));
        model.addAttribute("filters_out", params.get("filtersOut"));
        return "orders";
    }

    @GetMapping(value = "/{id}")
    public String showUpdateOrderPage(Model model, @PathVariable(name = "id") Long id) {
        model.addAttribute("order", orderService.getById(id));
        return "update_order";
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteOrder(@PathVariable(name = "id") Long id) {
        orderService.delete(id);
        return "redirect:/orders";
    }

    @PostMapping(value = "/new")
    public String createOrder(@ModelAttribute OrderDto orderDto) {
        orderService.saveOrUpdate(orderDto);
        return "redirect:/orders";
    }

    @PostMapping(value = "/update")
    public String updateOrder(@ModelAttribute OrderDto orderDto) {
        orderService.saveOrUpdate(orderDto);
        return "redirect:/orders";
    }
}
