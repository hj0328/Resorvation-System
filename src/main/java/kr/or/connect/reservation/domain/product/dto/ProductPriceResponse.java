package kr.or.connect.reservation.domain.product.dto;

import kr.or.connect.reservation.domain.product.entity.ProductPrice;
import kr.or.connect.reservation.domain.product.entity.SeatType;
import lombok.Getter;

@Getter
public class ProductPriceResponse {
    private Integer price;
    private SeatType seatType;

    private ProductPriceResponse(Integer price, SeatType seatType) {
        this.price = price;
        this.seatType = seatType;
    }

    public static ProductPriceResponse of(ProductPrice productPrice) {
        return new ProductPriceResponse(productPrice.getPrice(), productPrice.getSeatType());
    }
}
