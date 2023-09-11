package kr.or.connect.reservation.domain.reserve;


import kr.or.connect.reservation.domain.reserve.ReservationDao;
import kr.or.connect.reservation.domain.reserve.ReservationServiceImpl;
import kr.or.connect.reservation.reserve.*;
import kr.or.connect.reservation.domain.display.DisplayInfo;
import kr.or.connect.reservation.domain.reserve.dto.ReservationInfoDto;
import kr.or.connect.reservation.domain.reserve.dto.ReservationPriceDto;
import kr.or.connect.reservation.domain.reserve.dto.ReservationRequestDto;
import kr.or.connect.reservation.domain.reserve.dto.ReservationResponseDto;
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

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ReservationServiceImplTest {

    @Mock
    private ReservationDao myReservationDao;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @DisplayName("예약 조회 기능 테스트")
    @Test
    void getReservationsTest() {
        // given
        List<ReservationInfoDto> list = new ArrayList<>();

        ReservationInfoDto reservationInfo = new ReservationInfoDto();
        reservationInfo.setReservationInfoId(1);
        reservationInfo.setDisplayInfoId(1);
        reservationInfo.setTotalPrice(1000);

        Mockito.when(myReservationDao.selectTotalPriceById(1))
                .thenReturn(1000);

        DisplayInfo displayInfo = new DisplayInfo();
        displayInfo.setDisplayInfoId(1);
        displayInfo.setDisplayInfoId(1);
        Mockito.when(myReservationDao.selectDisplayInfoById(1, 1))
                .thenReturn(displayInfo);

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
        ReservationResponseDto reservationResponse = new ReservationResponseDto();
        reservationResponse.setPrices(getReservationPriceDtoList());
        Mockito.when(myReservationDao.selectReservationInfoPriceDtoList(0))
                .thenReturn(reservationResponse.getPrices());

        Mockito.when(myReservationDao.selectReservationResponseDto(0))
                .thenReturn(reservationResponse);


        // when
        ReservationRequestDto newReservationRequest = getNewReservationRequest();
        ReservationResponseDto response = reservationService.createReservations(newReservationRequest);

        // then
        assertThat(response.getPrices().size()).isEqualTo(3);
    }

    @DisplayName("예약 취소 테스트")
    @Test
    void cancelReservationsTest() {
        // given
        ReservationResponseDto reservationResponse = new ReservationResponseDto();
        reservationResponse.setCancelYn(true);
        Mockito.when(myReservationDao.selectReservationResponseDto(0))
                .thenReturn(reservationResponse);

        // when
        ReservationResponseDto response = reservationService.cancelReservation(0);

        // then
        assertThat(response.isCancelYn()).isEqualTo(true);
    }

    private List<ReservationPriceDto> getReservationPriceDtoList() {
        List<ReservationPriceDto> prices = new ArrayList<>();
        ReservationPriceDto priceDto1 = new ReservationPriceDto();
        priceDto1.setCount(1);
        prices.add(priceDto1);

        ReservationPriceDto priceDto2 = new ReservationPriceDto();
        priceDto1.setCount(2);
        prices.add(priceDto2);

        ReservationPriceDto priceDto3 = new ReservationPriceDto();
        priceDto1.setCount(3);
        prices.add(priceDto3);
        return prices;
    }

    private ReservationRequestDto getNewReservationRequest() {
        ReservationRequestDto reservationRequest = new ReservationRequestDto();
        List<ReservationPriceDto> prices = new ArrayList<>();
        ReservationPriceDto priceDto1 = new ReservationPriceDto();
        priceDto1.setCount(1);
        prices.add(priceDto1);

        ReservationPriceDto priceDto2 = new ReservationPriceDto();
        priceDto1.setCount(2);
        prices.add(priceDto2);

        ReservationPriceDto priceDto3 = new ReservationPriceDto();
        priceDto1.setCount(3);
        prices.add(priceDto3);
        reservationRequest.setPrices(prices);

        return reservationRequest;
    }
}