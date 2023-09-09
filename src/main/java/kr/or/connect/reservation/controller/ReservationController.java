package kr.or.connect.reservation.controller;

import kr.or.connect.reservation.dto.reqeust.ReservationRequestDto;
import kr.or.connect.reservation.dto.response.ReservationResponseDto;
import kr.or.connect.reservation.service.ReservationService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/reservations")
public class ReservationController {

	private final ReservationService reservationService;

	public ReservationController(ReservationService reservationService) {
		this.reservationService = reservationService;
	}

	/**
	 * 예약정보조회
	 * @param reservationEmail
	 * @param session
	 */
	@GetMapping
	public Map<String, Object> getMyReservations(@RequestParam String reservationEmail)
			throws ServletException, IOException {
		return reservationService.getReservations(reservationEmail);
	}

	/**
	 * 예약하기
	 * @param reservationRequestDto
	 */
	@PostMapping
	public ReservationResponseDto setReservation(@RequestBody ReservationRequestDto reservationRequestDto) {
		return reservationService.createReservations(reservationRequestDto);
	}

	/**
	 * 예약취소
	 * 예약취소 시, 예약 리소스는 바로 삭제되지 취소 표시만 남긴 후 향후 삭제한다.
	 * @param reservationId
	 */
	@PutMapping("/{reservationId}")
	public ReservationResponseDto deleteReservation(@PathVariable int reservationId) {
		return reservationService.cancelReservation(reservationId);
	}
}
