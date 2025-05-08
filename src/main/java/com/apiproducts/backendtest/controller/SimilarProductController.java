package com.apiproducts.backendtest.controller;

import com.apiproducts.backendtest.dto.ProductDetailDTO;
import com.apiproducts.backendtest.exception.ProductNotFoundException;
import com.apiproducts.backendtest.service.SimilarProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/product")
public class SimilarProductController {

    private final SimilarProductService service;

    public SimilarProductController(SimilarProductService service) {
        this.service = service;
    }

    @GetMapping("/{productId}/similar")
    public ResponseEntity<List<ProductDetailDTO>> getSimilarProducts(@PathVariable String productId) {
        try {
            List<ProductDetailDTO> result = service.getSimilarProducts(productId);
            log.info("JM long.info: {}",result);
            return ResponseEntity.ok(result);
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
