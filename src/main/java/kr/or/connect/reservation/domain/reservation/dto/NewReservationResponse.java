package kr.or.connect.reservation.domain.reservation.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class NewReservationResponse {
    private final Long ReservationId;
    private final List<Long> ReservationPriceIds;

    private NewReservationResponse(Long reservationId, List<Long> reservationPriceIds) {
        ReservationId = reservationId;
        ReservationPriceIds = reservationPriceIds;
    }

    public static NewReservationResponse of(Long reservationId, List<Long> reservationPriceIds) {
        return new NewReservationResponse(reservationId, reservationPriceIds);
    }
}
