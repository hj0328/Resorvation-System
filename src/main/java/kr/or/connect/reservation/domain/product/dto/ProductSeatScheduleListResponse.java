package kr.or.connect.reservation.domain.product.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ProductSeatScheduleListResponse {
    private Long productId;
    private List<ProductSeatScheduleResponse> productSeatScheduleList;

    @Builder
    public ProductSeatScheduleListResponse(Long productId, List<ProductSeatScheduleResponse> productSeatScheduleList) {
        this.productId = productId;
        this.productSeatScheduleList = productSeatScheduleList;
    }
}
