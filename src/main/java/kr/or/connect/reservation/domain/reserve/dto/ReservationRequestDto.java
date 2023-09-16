package kr.or.connect.reservation.domain.reserve.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@ToString
public class ReservationRequestDto {
	@NotBlank
	private int displayInfoId;
	@NotBlank
	private int UserId;
	private List<ReservationPriceDto> prices;

	@NotBlank
	private int productId;

	@NotBlank @Email
	private String reservationEmail;
	private String reservationName;
	private String reservationTelephone;
	private String reservationYearMonthDay;
}
