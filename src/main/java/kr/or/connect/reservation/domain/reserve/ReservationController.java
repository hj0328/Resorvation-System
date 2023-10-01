package kr.or.connect.reservation.domain.reserve;

import kr.or.connect.reservation.domain.reserve.dto.ReservationRequest;
import kr.or.connect.reservation.domain.reserve.dto.ReservationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(path = "/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

	private final ReservationService reservationService;

	/**
	 * 예약정보조회
	 * @param reservationEmail
	 */
	@GetMapping
	public Map<String, Object> getReservations(@RequestParam String reservationEmail)
			throws ServletException, IOException {
		log.info("GET /api/reservation, reservationEmail={}", reservationEmail);
		return reservationService.getReservations(reservationEmail);
	}

	/**
	 * 예약하기
	 * @param reservationRequest
	 */
	@PostMapping
	public ReservationResponse setReservation(
			@RequestBody ReservationRequest reservationRequest) {
		return reservationService.createReservations(reservationRequest);
	}

	/**
	 * 예약취소
	 * 예약취소 시, 예약 리소스는 바로 삭제되지 취소 표시만 남긴 후 향후 삭제한다.
	 * @param reservationId
	 */
	@PutMapping("/{reservationId}")
	public ReservationResponse deleteReservation(@PathVariable int reservationId) {
		log.info("PUT /api/reservation, reservationId={}", reservationId);
		return reservationService.cancelReservation(reservationId);
	}

}
