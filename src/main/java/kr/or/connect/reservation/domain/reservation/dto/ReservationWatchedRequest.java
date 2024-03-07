package kr.or.connect.reservation.domain.reservation.dto;

import lombok.Getter;

@Getter
public class ReservationWatchedRequest {
    private Long memberId;
    private Long reservationId;
}
