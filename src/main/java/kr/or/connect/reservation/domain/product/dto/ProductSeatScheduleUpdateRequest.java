package kr.or.connect.reservation.domain.product.dto;

import kr.or.connect.reservation.domain.product.entity.ProductSeatSchedule;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
public class ProductSeatScheduleUpdateRequest {
    @NotNull
    private Long placeId;
    @NotNull
    private LocalDateTime eventDateTime;
    @NotNull
    private  String seatType;
    @NotNull
    private Integer reservedQuantity;

    private ProductSeatScheduleUpdateRequest(LocalDateTime eventDateTime, Long placeId,
                                             String seatType, Integer reservedQuantity) {
        this.eventDateTime = eventDateTime;
        this.placeId = placeId;
        this.seatType = seatType;
        this.reservedQuantity = reservedQuantity;
    }

    public ProductSeatSchedule toProductSeatSchedule() {
        return ProductSeatSchedule.create(null, null,
                eventDateTime, reservedQuantity, seatType);
    }
}
