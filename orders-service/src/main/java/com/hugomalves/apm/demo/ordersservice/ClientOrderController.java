package com.hugomalves.apm.demo.ordersservice;

import com.hugomalves.apm.demo.ordersservice.dto.ClientOrderDTO;
import com.hugomalves.apm.demo.ordersservice.service.ClientOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class ClientOrderController {

    private final ClientOrderService clientOrderService;

    public ClientOrderController(ClientOrderService clientOrderService) {
        this.clientOrderService = clientOrderService;
    }

    @PostMapping
    public ResponseEntity<Void> createOrder(@RequestBody ClientOrderDTO clientOrderDTO) {
        clientOrderService.createOrder(clientOrderDTO);
        return ResponseEntity.ok().build();
    }
}