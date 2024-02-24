package kr.or.connect.reservation.domain.reservation;

import kr.or.connect.reservation.domain.reservation.dto.ReservationRequest;
import kr.or.connect.reservation.domain.reservation.dto.ReservationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping(path = "/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

	private final ReservationService reservationService;

	/**
	 * 예약정보조회
	 * @param memberId
	 */
	@GetMapping("/{memberId}")
	public Map<String, Object> getReservations(@PathVariable Integer memberId) {
		return reservationService.getReservations(memberId);
	}

	/**
	 * {memberId} 예약하기
	 */
	@PostMapping("/{memberId}")
	public ReservationResponse createReservation(
			@RequestBody ReservationRequest reservationRequest,
			@PathVariable Integer memberId) {
		return reservationService.createReservations(reservationRequest, memberId);
	}

	/**
	 * 예약취소
	 * 예약취소 시, 예약 리소스는 바로 삭제되지 취소 표시만 남긴 후 향후 삭제한다.
	 * @param reservationId
	 */
	@PutMapping("/{reservationId}")
	public ReservationResponse setReservationCancel(@PathVariable Integer reservationId) {
		return reservationService.setReservationCancel(reservationId);
	}
}
