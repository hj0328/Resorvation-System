package kr.or.connect.reservation.domain.product.dto;

import kr.or.connect.reservation.domain.product.entity.Product;
import kr.or.connect.reservation.domain.product.entity.ProductPrice;
import kr.or.connect.reservation.domain.product.entity.SeatType;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.PositiveOrZero;

@Getter
public class ProductPriceRequest {
    @PositiveOrZero
    private Integer price;
    private SeatType seatType;

    @Builder
    public ProductPriceRequest(Integer price, SeatType seatType) {
        this.price = price;
        this.seatType = seatType;
    }

    public ProductPrice toProductPrice(Product product) {
        return ProductPrice.create(product, this.price, this.seatType);
    }
}
