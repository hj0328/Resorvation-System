package kr.or.connect.reservation.service;

import java.util.Map;

import kr.or.connect.reservation.dto.reqeust.ReservationRequestDto;
import kr.or.connect.reservation.dto.response.ReservationResponseDto;

public interface ReservationService {
	Map<String, Object> getReservations(String reservationEmail);
	ReservationResponseDto createReservations(ReservationRequestDto reservationRequestDto);
	ReservationResponseDto cancelReservation(int reservationInfoId);
}
