package com.hugomalves.apm.demo.ordersservice.service;

import com.hugomalves.apm.demo.ordersservice.dto.ProductDto;
import com.hugomalves.apm.demo.ordersservice.model.Product;
import com.hugomalves.apm.demo.ordersservice.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductService::mapProductDto)
                .collect(Collectors.toList());
    }

    private static ProductDto mapProductDto(Product product) {
        LOGGER.info("Mapping Product to ProductDto");
        return new ProductDto(
                product.getId(),
                product.getBarcode(),
                product.getDescription(),
                product.getPrice());
    }
}
