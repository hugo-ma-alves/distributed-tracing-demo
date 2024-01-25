package com.hugomalves.apm.demo.ordersservice.service;

import com.hugomalves.apm.demo.ordersservice.dto.ClientOrderDTO;
import com.hugomalves.apm.demo.ordersservice.dto.ProductOrderDTO;
import com.hugomalves.apm.demo.ordersservice.metrics.OrderServiceMetric;
import com.hugomalves.apm.demo.ordersservice.model.ClientOrder;
import com.hugomalves.apm.demo.ordersservice.model.OrderProduct;
import com.hugomalves.apm.demo.ordersservice.model.Product;
import com.hugomalves.apm.demo.ordersservice.model.ShippingRequest;
import com.hugomalves.apm.demo.ordersservice.repository.ClientOrderRepository;
import com.hugomalves.apm.demo.ordersservice.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ClientOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientOrderService.class);

    private final ClientOrderRepository clientOrderRepository;

    private final ProductRepository productRepository;

    private final RestTemplate restTemplate;

    private final OrderServiceMetric orderServiceMetric;

    @Value("${shipping.service.url}")
    private String shippingServiceUrl;

    public ClientOrderService(ClientOrderRepository clientOrderRepository, ProductRepository productRepository, RestTemplate restTemplate, OrderServiceMetric orderServiceMetric) {
        this.clientOrderRepository = clientOrderRepository;
        this.productRepository = productRepository;
        this.restTemplate = restTemplate;
        this.orderServiceMetric = orderServiceMetric;
    }

    @Transactional
    public void createOrder(ClientOrderDTO clientOrderDTO) {
        LOGGER.info("Creating order for customer {}", clientOrderDTO.getClientName());
        ClientOrder clientOrder = new ClientOrder();
        clientOrder.setClient(clientOrderDTO.getClientName());

        for (ProductOrderDTO productOrderDTO : clientOrderDTO.getProducts()) {
            Product product = productRepository.findById(productOrderDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            LOGGER.debug("Order contains {} items of {}", productOrderDTO.getQuantity(), product.getDescription());

            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setProduct(product);
            orderProduct.setQuantity(productOrderDTO.getQuantity());
            clientOrder.addOrderProduct(orderProduct);
        }

        clientOrderRepository.save(clientOrder);
        LOGGER.info("Order for customer {} created", clientOrderDTO.getClientName());

        orderServiceMetric.incrementCounter();
        LOGGER.info("Shipping requested for customer {}", clientOrderDTO.getClientName());
        shipOrder(clientOrder.getId(), clientOrderDTO.getProducts());
    }

    private void shipOrder(Long orderId, List<ProductOrderDTO> productOrderDTO) {
        // Create the request payload
        ShippingRequest shippingRequest = new ShippingRequest(orderId, productOrderDTO);

        try {
            restTemplate.postForObject(shippingServiceUrl, shippingRequest, String.class);
        } catch (RestClientException restClientException) {
            LOGGER.error("Failed to request shipping, error code", restClientException);
            throw restClientException;
        }


    }
}
