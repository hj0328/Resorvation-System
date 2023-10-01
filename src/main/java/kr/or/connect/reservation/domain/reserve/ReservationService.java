package kr.or.connect.reservation.domain.reserve;

import kr.or.connect.reservation.domain.reserve.dto.ReservationRequest;
import kr.or.connect.reservation.domain.reserve.dto.ReservationResponse;

import java.util.Map;

public interface ReservationService {
	Map<String, Object> getReservations(String reservationEmail);
	ReservationResponse createReservations(ReservationRequest reservationRequest);
	ReservationResponse cancelReservation(int reservationInfoId);
}
