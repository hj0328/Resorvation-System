package kr.or.connect.reservation.domain.reservation.dao;

import kr.or.connect.reservation.domain.reservation.entity.Reservation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByMemberId(Long memberId, PageRequest pageRequest);
}
