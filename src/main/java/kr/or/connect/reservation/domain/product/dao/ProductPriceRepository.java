package kr.or.connect.reservation.domain.product.dao;

import kr.or.connect.reservation.domain.product.entity.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductPriceRepository extends JpaRepository<ProductPrice, Long> {
}
