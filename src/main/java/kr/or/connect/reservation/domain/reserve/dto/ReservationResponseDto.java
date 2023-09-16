package kr.or.connect.reservation.domain.reserve.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter @ToString
public class ReservationResponseDto {
	private boolean cancelYn;
	private String createDate;
	private String modifyDate;
	private int displayInfoId;
	private List<ReservationPriceDto> prices;
	private int productId;
	private String reservationEmail;
	private String reservationName;
	private String reservationTelephone;
	private String reservationDate;
	private int reservationInfoId;
}
