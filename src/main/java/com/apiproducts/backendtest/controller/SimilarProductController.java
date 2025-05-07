package com.apiproducts.backendtest.controller;

import com.apiproducts.backendtest.dto.ProductDetailDTO;
import com.apiproducts.backendtest.service.SimilarProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

    @Autowired
    private SimilarProductService service;

    @GetMapping(value = "/{productId}/similar", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<ProductDetailDTO> getSimilarProducts(@PathVariable String productId) {
        return service.getSimilarProducts(productId);
    }
}
