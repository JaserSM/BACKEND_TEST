package com.apiproducts.backendtest.service;

import com.apiproducts.backendtest.dto.ProductDetailDTO;
import com.apiproducts.backendtest.exception.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class SimilarProductService {
    private final WebClient webClient;

    public SimilarProductService(@Qualifier("externalApiWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public List<ProductDetailDTO> getSimilarProducts(String productId) {
        try {
            String[] similarIds = webClient.get()
                    .uri("/product/{productId}/similarids", productId)
                    .retrieve()
                    .bodyToMono(String[].class)
                    .block();
            log.info("JM long.info: {}", similarIds);
            if (similarIds == null) return List.of();

            return Arrays.stream(similarIds)
                    .map(this::getProductDetailSafe)
                    .filter(Objects::nonNull)
                    .toList();
        } catch (WebClientResponseException.NotFound e) {
            throw new ProductNotFoundException("Product not found with ID: " + productId);
        }
    }
    private ProductDetailDTO getProductDetailSafe(String id) {
        try {
            return webClient.get()
                    .uri("/product/{productId}", id)
                    .retrieve()
                    .bodyToMono(ProductDetailDTO.class)
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}

