package kr.or.connect.reservation.domain.product.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ProductListResponse {
    private Long totalProductCount;
    private List<ProductResponse> products;


    @Builder
    public ProductListResponse(Long totalProductCount, List<ProductResponse> products) {
        this.totalProductCount = totalProductCount;
        this.products = products;
    }
}
