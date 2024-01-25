package com.hugomalves.apm.demo.ordersservice;

import com.hugomalves.apm.demo.ordersservice.dto.ProductDTO;
import com.hugomalves.apm.demo.ordersservice.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        LOGGER.info("Requested the list of products");
        List<ProductDTO> products = productService.getAllProducts();
        LOGGER.debug("Returned {} products to the customer", products.size());
        return ResponseEntity.ok(products);
    }
}
