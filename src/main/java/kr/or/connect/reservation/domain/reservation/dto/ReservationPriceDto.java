package kr.or.connect.reservation.domain.reservation.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReservationPriceDto {
	private Long productSeatScheduleId;

	private Integer price;
	private Integer quantity;
	private String seatType;

	@Builder
	public ReservationPriceDto(Long productSeatScheduleId, Integer price, Integer quantity, String seatType) {
		this.productSeatScheduleId = productSeatScheduleId;
		this.price = price;
		this.quantity = quantity;
		this.seatType = seatType;
	}
}
