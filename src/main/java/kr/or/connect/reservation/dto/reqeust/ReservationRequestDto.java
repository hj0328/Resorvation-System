package kr.or.connect.reservation.dto.reqeust;

import java.util.List;

import kr.or.connect.reservation.dto.ReservationPriceDto;

public class ReservationRequestDto {
	private int displayInfoId;
	private List<ReservationPriceDto> prices;
	private int productId;
	private String reservationEmail;
	private String reservationName;
	private String reservationTelephone;
	private String reservationYearMonthDay;
	
	public int getDisplayInfoId() {
		return displayInfoId;
	}
	
	public void setDisplayInfoId(int displayInfoId) {
		this.displayInfoId = displayInfoId;
	}

	public List<ReservationPriceDto> getPrices() {
		return prices;
	}
	
	public void setPrices(List<ReservationPriceDto> prices) {
		this.prices = prices;
	}
	
	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getReservationEmail() {
		return reservationEmail;
	}

	public void setReservationEmail(String reservationEmail) {
		this.reservationEmail = reservationEmail;
	}

	public String getReservationName() {
		return reservationName;
	}

	public void setReservationName(String reservationName) {
		this.reservationName = reservationName;
	}

	public String getReservationTelephone() {
		return reservationTelephone;
	}

	public void setReservationTelephone(String reservationTelephone) {
		this.reservationTelephone = reservationTelephone;
	}

	public String getReservationYearMonthDay() {
		return this.reservationYearMonthDay;
	}

	public void setReservationYearMonthDay(String reservationYearMonthDay) {
		this.reservationYearMonthDay = reservationYearMonthDay;
	}

	@Override
	public String toString() {
		return "ReservationPostDto [displayInfoId=" + displayInfoId + ", prices=" + prices + ", productId=" + productId
				+ ", reservationEmail=" + reservationEmail + ", reservationName=" + reservationName
				+ ", reservationTelephone=" + reservationTelephone + ", reservationYearMonthDay="
				+ reservationYearMonthDay;
	}
}
