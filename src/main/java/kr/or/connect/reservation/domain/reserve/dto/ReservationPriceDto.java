package kr.or.connect.reservation.domain.reserve.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class ReservationPriceDto {
	private int count;
	private int productPriceId;
	private int reservationInfoId;
	private int reservationInfoPriceId;
}
