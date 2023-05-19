package kr.or.connect.reservation.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.or.connect.reservation.dto.reqeust.ReservationRequestDto;
import kr.or.connect.reservation.dto.response.ReservationResponseDto;
import kr.or.connect.reservation.service.ReservationService;

@RestController
@RequestMapping(path = "/api")
public class ReservationController {

	private final ReservationService reservationService;

	public ReservationController(ReservationService reservationService) {
		this.reservationService = reservationService;
	}

	@GetMapping("/reservations")
	public Map<String, Object> getMyReservations(@RequestParam String reservationEmail, HttpSession session) throws ServletException, IOException {
		Map<String, Object> reservations = reservationService.getReservations(reservationEmail);
		Integer size = (Integer) reservations.get("size");
		if (size > 0) {
			session.setAttribute("reservationEmail", reservationEmail);
		}

		return reservations;
	}

	/*
	 * [PJT-5] 예약취소는 실제 DB 에 적용된 값이 아닌, Random으로 생성된 예약 객체를 반환한다.
	 */
	@PostMapping("/reservations")
	public ReservationResponseDto setReservation(@RequestBody ReservationRequestDto reservationRequestDto) {
		return reservationService.createReservations(reservationRequestDto);
	}

	/*
	 * [PJT-5] 예약취소는 실제 DB 에 적용된 값이 아닌, Random으로 생성된 예약 객체를 반환한다.
	 */
	@PutMapping("/reservations/{reservationId}")
	public ReservationResponseDto deleteReservation(@PathVariable int reservationId) {
		return reservationService.cancelReservation(reservationId);
	}
}
