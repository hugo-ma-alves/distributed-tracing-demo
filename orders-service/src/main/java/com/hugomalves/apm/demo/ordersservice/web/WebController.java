package com.hugomalves.apm.demo.ordersservice.web;

import com.hugomalves.apm.demo.ordersservice.dto.OrderDto;
import com.hugomalves.apm.demo.ordersservice.service.ClientOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {

    private final ClientOrderService clientOrderService;

    private static final Logger LOGGER = LoggerFactory.getLogger(WebController.class);

    public WebController(ClientOrderService clientOrderService) {
        this.clientOrderService = clientOrderService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/orders")
    public String showOrders(Model model, @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "5") int size) {
        PageRequest pageRequest = PageRequest.of(page, size).withSort(Sort.by("id").descending());
        Page<OrderDto> orders = clientOrderService.getLastOrders(pageRequest);
        model.addAttribute("orderPage", orders);
        return "orders";
    }

}