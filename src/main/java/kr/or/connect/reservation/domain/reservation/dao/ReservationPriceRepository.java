package kr.or.connect.reservation.domain.reservation.dao;

import kr.or.connect.reservation.domain.reservation.entity.ReservationPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationPriceRepository extends JpaRepository<ReservationPrice, Long> {

    List<ReservationPrice> findAllByReservationId(Long reservationId);

}
