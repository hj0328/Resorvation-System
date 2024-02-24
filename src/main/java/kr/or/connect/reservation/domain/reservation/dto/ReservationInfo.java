package kr.or.connect.reservation.domain.reservation.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class ReservationInfo {
	private Boolean cancelYn;
	private Integer displayInfoId;
	private Integer userId;
	private Integer productId;
	private String reservationDate;
	private String reservationEmail;
	private Integer reservationInfoId;
	private String reservationName;
	private String reservationTelephone;
	private LocalDateTime modifyDate;
}
