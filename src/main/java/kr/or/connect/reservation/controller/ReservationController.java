package kr.or.connect.reservation.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.or.connect.reservation.service.ReservationService;

@RestController
@RequestMapping(path = "/api")
public class ReservationController {

	private final ReservationService reservationService;

	public ReservationController(ReservationService reservationService) {
		this.reservationService = reservationService;
	}

	@GetMapping("/reservations")
	public Map<String, Object> getMyReservations(@RequestParam String reservationEmail) {
		Map<String, Object> reservations = reservationService.getReservations(reservationEmail);
		return reservations;
	}

	/* 
	 *  개인적으로 추가로 넣은 코드입니다.
	 *  기획에서 '예약하기' 이후 디비에 저장되지 않지만, 
	 *  '예약확인'기능을 바로 확인하고자 Post, Redirect, Get 패턴으로 메인화면으로 이동하도록 하였습니다.
	 */
	@PostMapping("/ticketing")
	public void setReservation(HttpServletResponse response) {
		try {
			response.sendRedirect("/reservation");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
