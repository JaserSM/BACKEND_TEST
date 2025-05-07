package com.apiproducts.backendtest.service;

import com.apiproducts.backendtest.dto.ProductDetailDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class SimilarProductService {

    @Autowired
    private WebClient externalApiClient;

    public Flux<ProductDetailDTO> getSimilarProducts(String productId) {
        return externalApiClient.get()
                .uri("/product/{productId}/similarids", productId)
                .retrieve()
                .bodyToFlux(String.class)
                .flatMap(this::getProductDetail)
                .onErrorResume(e -> Flux.empty());
    }

    private Mono<ProductDetailDTO> getProductDetail(String id) {
        return externalApiClient.get()
                .uri("/product/{id}", id)
                .retrieve()
                .bodyToMono(ProductDetailDTO.class)
                .onErrorResume(e -> Mono.empty());
    }
}

