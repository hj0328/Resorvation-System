package kr.or.connect.reservation.domain.reserve.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ReservationRequestDto {
	private int displayInfoId;
	private List<ReservationPriceDto> prices;
	private int productId;
	private String reservationEmail;
	private String reservationName;
	private String reservationTelephone;
	private String reservationYearMonthDay;
}
