package kr.or.connect.reservation.domain.reservation.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class NewReservationRequest {
    private Long memberId;
    private Long productId;
    private LocalDateTime reservedDate;
    private List<ReservationPriceDto> reservationPriceDtos;

    @Builder
    public NewReservationRequest(Long memberId, Long productId, LocalDateTime reservedDate, List<ReservationPriceDto> reservationPriceDtos) {
        this.memberId = memberId;
        this.productId = productId;
        this.reservedDate = reservedDate;
        this.reservationPriceDtos = reservationPriceDtos;
    }
}
