package kr.or.connect.reservation.domain.reservation.dto;

import kr.or.connect.reservation.domain.product.entity.SeatType;
import kr.or.connect.reservation.domain.reservation.entity.ReservationPrice;
import lombok.Getter;

@Getter
public class MyReservationPrice {
    private Long reservationPriceId;
    private Integer reservedQuantity;
    private Integer reservedPrice;
    private SeatType reservedSeatType;

    public MyReservationPrice(Long reservationPriceId,
                              Integer reservedQuantity,
                              Integer reservedPrice,
                              SeatType reservedSeatType) {

        this.reservationPriceId = reservationPriceId;
        this.reservedQuantity = reservedQuantity;
        this.reservedPrice = reservedPrice;
        this.reservedSeatType = reservedSeatType;
    }

    public static MyReservationPrice of(ReservationPrice v) {
        return new MyReservationPrice(v.getId(),
                v.getReservedQuantity(),
                v.getReservedPrice(),
                v.getReservedSeatType());
    }

}
