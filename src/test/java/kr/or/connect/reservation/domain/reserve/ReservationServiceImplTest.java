package kr.or.connect.reservation.domain.reserve;


import kr.or.connect.reservation.domain.display.DisplayInfo;
import kr.or.connect.reservation.domain.reserve.dto.ReservationInfo;
import kr.or.connect.reservation.domain.reserve.dto.ReservationPrice;
import kr.or.connect.reservation.domain.reserve.dto.ReservationRequest;
import kr.or.connect.reservation.domain.reserve.dto.ReservationResponse;
import kr.or.connect.reservation.domain.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ReservationServiceImplTest {

    @Mock
    private ReservationDao myReservationDao;

    @Mock
    private UserService userService;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @DisplayName("예약 조회 기능 테스트")
    @Test
    void getReservationsTest() {
        // given
        List<ReservationInfo> list = new ArrayList<>();

        ReservationInfo reservationInfo = new ReservationInfo();
        reservationInfo.setReservationInfoId(1);
        reservationInfo.setDisplayInfoId(1);
        reservationInfo.setTotalPrice(1000);

        Mockito.when(myReservationDao.selectTotalPriceById(1))
                .thenReturn(Optional.of(Integer.valueOf(1000)));

        DisplayInfo displayInfo = new DisplayInfo();
        displayInfo.setDisplayInfoId(1);
        displayInfo.setDisplayInfoId(1);
        Mockito.when(myReservationDao.selectDisplayInfoById(1, 1))
                .thenReturn(Optional.of(displayInfo));

        reservationInfo.setDisplayInfo(displayInfo);
        list.add(reservationInfo);
        Mockito.when(myReservationDao.selectReservationInfoByEmail("test@gmail.com"))
                .thenReturn(list);

        // when
        Map<String, Object> reservations = reservationService.getReservations("test@gmail.com");

        // then
        Object reservations1 = reservations.get("reservations");
        assertThat(reservations1).isEqualTo(list);

        Object size = reservations.get("size");
        assertThat(size).isEqualTo(1);
    }

    @DisplayName("예약하기 테스트")
    @Test
    void createReservationsTest() {
        // given
        ReservationResponse reservationResponse = new ReservationResponse();
        reservationResponse.setPrices(getReservationPriceDtoList());
        Mockito.when(myReservationDao.selectReservationInfoPriceDtoList(0))
                .thenReturn(reservationResponse.getPrices());

        Mockito.when(myReservationDao.selectReservationResponse(0))
                .thenReturn(Optional.of(reservationResponse));


        // when
        ReservationRequest newReservationRequest = getNewReservationRequest();
        ReservationResponse response = reservationService.createReservations(newReservationRequest);

        // then
        assertThat(response.getPrices().size()).isEqualTo(3);
    }

    @DisplayName("예약 취소 테스트")
    @Test
    void cancelReservationsTest() {
        // given
        ReservationResponse reservationResponse = new ReservationResponse();
        reservationResponse.setCancelYn(true);
        Mockito.when(myReservationDao.selectReservationResponse(0))
                .thenReturn(Optional.of(reservationResponse));

        // when
        ReservationResponse response = reservationService.cancelReservation(0);

        // then
        assertThat(response.isCancelYn()).isEqualTo(true);
    }

    private List<ReservationPrice> getReservationPriceDtoList() {
        List<ReservationPrice> prices = new ArrayList<>();
        ReservationPrice priceDto1 = new ReservationPrice();
        priceDto1.setCount(1);
        prices.add(priceDto1);

        ReservationPrice priceDto2 = new ReservationPrice();
        priceDto2.setCount(2);
        prices.add(priceDto2);

        ReservationPrice priceDto3 = new ReservationPrice();
        priceDto1.setCount(3);
        prices.add(priceDto3);
        return prices;
    }

    private ReservationRequest getNewReservationRequest() {
        ReservationRequest reservationRequest = new ReservationRequest();
        reservationRequest.setPrices(new ArrayList<>());
        reservationRequest.setId(0);

        List<ReservationPrice> prices = new ArrayList<>();
        ReservationPrice priceDto1 = new ReservationPrice();
        priceDto1.setCount(1);
        prices.add(priceDto1);

        ReservationPrice priceDto2 = new ReservationPrice();
        priceDto1.setCount(2);
        prices.add(priceDto2);

        ReservationPrice priceDto3 = new ReservationPrice();
        priceDto3.setCount(3);
        prices.add(priceDto3);
        reservationRequest.setPrices(prices);

        return reservationRequest;
    }
}