package kr.or.connect.reservation.domain.reserve;

import kr.or.connect.reservation.domain.reserve.dto.ReservationRequestDto;
import kr.or.connect.reservation.domain.reserve.dto.ReservationResponseDto;

import java.util.Map;

public interface ReservationService {
	Map<String, Object> getReservations(String reservationEmail);
	ReservationResponseDto createReservations(ReservationRequestDto reservationRequestDto);
	ReservationResponseDto cancelReservation(int reservationInfoId);
}
