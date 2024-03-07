package kr.or.connect.reservation.domain.reservation.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ReservationResponse {
	private Boolean cancelYn;
	private Integer displayInfoId;
	private List<ReservationPriceDto> prices;
	private Integer productId;
	private Integer totalPrice;
	private String reservationDate;
	private String reservationEmail;
	private Integer reservationInfoId;
	private String reservationTelephone;
	private String reservationName;
	private LocalDateTime modifyDate;
}
