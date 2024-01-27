package com.hugomalves.apm.demo.ordersservice.service;

import com.hugomalves.apm.demo.ordersservice.dto.ClientOrderRequest;
import com.hugomalves.apm.demo.ordersservice.dto.OrderDto;
import com.hugomalves.apm.demo.ordersservice.dto.ProductOrderDto;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

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
    public void createOrder(ClientOrderRequest clientOrderRequest) {
        LOGGER.info("Creating order for customer {}", clientOrderRequest.getClientName());
        ClientOrder clientOrder = new ClientOrder();
        clientOrder.setClient(clientOrderRequest.getClientName());

        for (ProductOrderDto productOrderDto : clientOrderRequest.getProducts()) {
            Product product = productRepository.findById(productOrderDto.getId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            LOGGER.debug("Order contains {} items of {}", productOrderDto.getQuantity(), product.getDescription());

            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setProduct(product);
            orderProduct.setQuantity(productOrderDto.getQuantity());
            clientOrder.addOrderProduct(orderProduct);
        }

        clientOrderRepository.save(clientOrder);
        LOGGER.info("Order for customer {} created", clientOrderRequest.getClientName());

        orderServiceMetric.incrementCounter();
        LOGGER.info("Shipping requested for customer {}", clientOrderRequest.getClientName());
        shipOrder(clientOrder.getId(), clientOrderRequest.getProducts());
    }

    private void shipOrder(Long orderId, List<ProductOrderDto> productOrderDTO) {
        ShippingRequest shippingRequest = new ShippingRequest(orderId, productOrderDTO);
        LOGGER.trace("Creating shipping request");
        try {
            restTemplate.postForObject(shippingServiceUrl, shippingRequest, String.class);
        } catch (RestClientException restClientException) {
            LOGGER.error("Failed to request shipping, error code", restClientException);
            throw restClientException;
        }
    }

    public Page<OrderDto> getLastOrders(PageRequest pageRequest) {
        LOGGER.info("getting last orders {}", pageRequest);
        return clientOrderRepository.findAll(pageRequest)
                .map(ClientOrderService::mapOrder);
    }

    private static OrderDto mapOrder(ClientOrder clientOrder) {
        clientOrder.setClient(clientOrder.getClient());
        List<ProductOrderDto> products = clientOrder.getOrderProducts()
                .stream()
                .map(ClientOrderService::mapProduct)
                .collect(Collectors.toList());
        return new OrderDto(clientOrder.getId(), clientOrder.getClient(), products);
    }

    private static ProductOrderDto mapProduct(OrderProduct orderProduct) {
        ProductOrderDto productDto = new ProductOrderDto();
        productDto.setQuantity(orderProduct.getQuantity());
        productDto.setId(orderProduct.getId());
        productDto.setDescription(orderProduct.getProduct().getDescription());
        productDto.setPrice(orderProduct.getProduct().getPrice());
        productDto.setBarcode(orderProduct.getProduct().getBarcode());
        return productDto;
    }
}
