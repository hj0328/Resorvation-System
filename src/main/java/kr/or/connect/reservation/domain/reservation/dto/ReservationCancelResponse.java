package kr.or.connect.reservation.domain.reservation.dto;

import kr.or.connect.reservation.domain.reservation.entity.ReservationStatus;
import lombok.Getter;

@Getter
public class ReservationCancelResponse {
    private Long memberId;
    private Long reservationId;
    private ReservationStatus reservationStatus;

    private ReservationCancelResponse(Long memberId, Long reservationId, ReservationStatus reservationStatus) {
        this.memberId = memberId;
        this.reservationId = reservationId;
        this.reservationStatus = reservationStatus;
    }

    public static ReservationCancelResponse of(Long memberId, Long reservationId, ReservationStatus reservationStatus) {
        return new ReservationCancelResponse(memberId, reservationId, reservationStatus);
    }
}
