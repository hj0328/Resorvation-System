package kr.or.connect.reservation.domain.reserve;

import java.util.Map;

import kr.or.connect.reservation.domain.reserve.dto.ReservationRequestDto;
import kr.or.connect.reservation.domain.reserve.dto.ReservationResponseDto;

public interface ReservationService {
	Map<String, Object> getReservations(String reservationEmail);
	ReservationResponseDto createReservations(ReservationRequestDto reservationRequestDto);
	ReservationResponseDto cancelReservation(int reservationInfoId);
}
