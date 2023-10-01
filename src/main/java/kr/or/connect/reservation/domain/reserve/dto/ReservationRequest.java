package kr.or.connect.reservation.domain.reserve.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter @Setter
public class ReservationRequest {
	@NotBlank
	private int displayInfoId;
	private List<ReservationPrice> prices;

	@NotBlank
	private int productId;

	@NotBlank @Email
	private String reservationEmail;
	private String reservationName;
	private String reservationTelephone;
	private String reservationYearMonthDay;
}
