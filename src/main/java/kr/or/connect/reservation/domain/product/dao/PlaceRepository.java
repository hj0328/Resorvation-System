package kr.or.connect.reservation.domain.product.dao;

import kr.or.connect.reservation.domain.product.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {
}
