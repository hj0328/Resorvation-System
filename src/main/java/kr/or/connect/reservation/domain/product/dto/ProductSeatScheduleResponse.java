package kr.or.connect.reservation.domain.product.dto;

import kr.or.connect.reservation.domain.product.entity.Place;
import kr.or.connect.reservation.domain.product.entity.ProductSeatSchedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ProductSeatScheduleResponse {
    private Long productSeatScheduleId;
    private LocalDateTime eventDateTime;
    private Integer reservedQuantity;
    private String seatType;
    private PlaceDto place;

    private ProductSeatScheduleResponse(Long productSeatScheduleId,
                                       LocalDateTime eventDateTime, Integer reservedQuantity,
                                       String seatType, PlaceDto place) {
        this.productSeatScheduleId = productSeatScheduleId;
        this.eventDateTime = eventDateTime;
        this.reservedQuantity = reservedQuantity;
        this.seatType = seatType;
        this.place = place;
    }

    public static ProductSeatScheduleResponse of(ProductSeatSchedule productSeatSchedule, Place place) {
        return new ProductSeatScheduleResponse(
                productSeatSchedule.getId(),
                productSeatSchedule.getEventDateTime(),
                productSeatSchedule.getReservedQuantity(),
                productSeatSchedule.getSeatType().name(),
                PlaceDto.of(place));
    }
}
