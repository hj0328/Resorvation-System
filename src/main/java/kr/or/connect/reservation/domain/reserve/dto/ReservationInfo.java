package kr.or.connect.reservation.domain.reserve.dto;

import kr.or.connect.reservation.domain.display.DisplayInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class ReservationInfo {
	private Boolean cancelYn;
	private String createDate;
	private Integer displayInfoId;
	private String modifyDate;
	private Integer productId;
	private String reservationDate;
	private String reservationEmail;
	private Integer reservationInfoId;
	private String reservationName;
	private String reservationTelephone;
	private Integer totalPrice;

	private DisplayInfo displayInfo;
}
