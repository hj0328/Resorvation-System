package kr.or.connect.reservation.domain.reservation.dto;

import kr.or.connect.reservation.domain.reservation.entity.ReservationStatus;
import lombok.Getter;

@Getter
public class ReservationWatchedResponse {
    private Long memberId;
    private Long reservationId;
    private ReservationStatus reservationStatus;

    private ReservationWatchedResponse(Long memberId, Long reservationId, ReservationStatus reservationStatus) {
        this.memberId = memberId;
        this.reservationId = reservationId;
        this.reservationStatus = reservationStatus;
    }

    public static ReservationWatchedResponse of(Long memberId, Long reservationId, ReservationStatus reservationStatus) {
        return new ReservationWatchedResponse(memberId, reservationId, reservationStatus);
    }
}
