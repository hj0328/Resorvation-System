package kr.or.connect.reservation.domain.reservation;

import kr.or.connect.reservation.domain.product.dao.ProductSeatScheduleRepository;
import kr.or.connect.reservation.domain.product.entity.ProductSeatSchedule;
import kr.or.connect.reservation.domain.product.entity.SeatType;
import kr.or.connect.reservation.domain.reservation.dto.NewReservationRequest;
import kr.or.connect.reservation.domain.reservation.dto.ReservationPriceDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
@Sql(scripts = {"classpath:data/data.sql"})
class ReservationServiceBootTest {

    @Autowired
    ProductSeatScheduleRepository productSeatScheduleRepository;

    @Autowired
    ReservationService reservationService;

    @Test
    public void createReservation () throws Exception {
        // given
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);

        List<NewReservationRequest> list = new ArrayList<>();
        for (int i = 1; i <= threadCount; i++) {
            ReservationPriceDto price = ReservationPriceDto.builder()
                    .productSeatScheduleId(1L)
                    .price(10)
                    .quantity(1)
                    .seatType(SeatType.S.name())
                    .placeId(31)
                    .build();
            List<ReservationPriceDto> list1 = new ArrayList<>();
            list1.add(price);

            NewReservationRequest reservationRequest = NewReservationRequest.builder()
                    .memberId(1L)
                    .productId(1L)
                    .reservationPriceDtos(list1)
                    .build();

            list.add(reservationRequest);
        }

        CountDownLatch latch = new CountDownLatch(threadCount);

        // when
        for (int i = 1; i <= threadCount; i++) {
            executorService.submit(() -> {
                try {
                    NewReservationRequest req = list.remove(0);
                    reservationService.createReservation(req);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();


        // then
        ProductSeatSchedule ret = productSeatScheduleRepository.findById(1L).get();
        Assertions.assertThat(ret.getReservedQuantity()).isEqualTo(100);
    }
}