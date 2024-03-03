package kr.or.connect.reservation.domain.product.dto;

import kr.or.connect.reservation.domain.product.entity.ProductSeatSchedule;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
public class ProductSeatScheduleRequest {
    @NotNull
    private Long placeId;
    @NotNull
    private LocalDateTime eventDateTime;
    @NotNull
    private String seatType;

    @Builder
    public ProductSeatScheduleRequest(LocalDateTime eventDateTime, Long placeId, String seatType) {
        this.placeId = placeId;
        this.eventDateTime = eventDateTime;
        this.seatType = seatType;
    }

    public ProductSeatSchedule toProductSeatSchedule() {
        return ProductSeatSchedule.create(null, null,
                eventDateTime, 0, seatType);
    }
}
