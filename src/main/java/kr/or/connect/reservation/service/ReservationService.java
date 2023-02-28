package kr.or.connect.reservation.service;

import java.util.Map;

public interface ReservationService {
	Map<String, Object> getReservations(String reservationEmail);
}
