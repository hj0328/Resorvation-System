package kr.or.connect.reservation.domain.reservation.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
public class ReservationResponse {
	private Boolean cancelYn;
	private Integer displayInfoId;
	private List<ReservationPrice> prices;
	private Integer productId;
	private Integer totalPrice;
	private String reservationDate;
	private String reservationEmail;
	private Integer reservationInfoId;
	private String reservationTelephone;
	private String reservationName;
	private LocalDateTime modifyDate;
}
