package kr.or.connect.reservation.domain.product.dao;

import kr.or.connect.reservation.domain.product.dao.dto.PopularProductDto;
import kr.or.connect.reservation.domain.product.entity.ProductSeatSchedule;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface ProductSeatScheduleRepository extends JpaRepository<ProductSeatSchedule, Long> {
    List<ProductSeatSchedule> findAllByProductId(Long productId);

    /*
     * 예매 시 동시성 이슈 방지
     */
    @Override
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<ProductSeatSchedule> findById(Long id);

    @Query("select p.id as productId, p.title as title, p.description as description, " +
                "p.runningTime as runningTime, p.releaseDate as releaseDate," +
                " sum(pss.reservedQuantity) as totalReservedCount " +
            "from ProductSeatSchedule pss left join pss.product p " +
            "where pss.reservedQuantity is not null " +
            "group by p.id " +
            "order by totalReservedCount desc")
    List<PopularProductDto> findPopularProductByReservation(PageRequest pageRequest);

    @Query(value = "select p.product_id as productId, p.title as title, p.description as description, p.running_Time as runningTime, p.release_date as releaseDate, sub.totalReservedCount " +
            "from product p, " +
            " (select pss.product_id, sum(pss.reserved_quantity) as totalReservedCount  " +
            " from product_seat_schedule pss " +
            " group by pss.product_id) as sub " +
            "where p.product_id = sub.product_id " +
            "order by totalReservedCount desc ", nativeQuery = true)
    List<PopularProductDto> findAllPopularProductByReservation();

}
