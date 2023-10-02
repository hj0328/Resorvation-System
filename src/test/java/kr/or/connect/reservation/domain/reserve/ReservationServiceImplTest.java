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

import static kr.or.connect.reservation.utils.UtilConstant.RESERVATIONS;
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
        List<ReservationInfo> infoList = new ArrayList<>();
        ReservationInfo info = new ReservationInfo();
        info.setUserId(1);
        info.setReservationInfoId(1);
        info.setDisplayInfoId(1);
        info.setProductId(1);
        infoList.add(info);

        Mockito.when(myReservationDao.findReservationInfoByUserId(1))
                .thenReturn(infoList);

        List<ReservationResponse> list = new ArrayList<>();

        ReservationResponse response = new ReservationResponse();
        response.setReservationInfoId(1);
        response.setDisplayInfoId(1);
        response.setProductId(1);

        Mockito.when(myReservationDao.findTotalPriceById(1))
                .thenReturn(Optional.of(Integer.valueOf(1000)));

        DisplayInfo displayInfo = new DisplayInfo();
        Mockito.when(myReservationDao.findDisplayInfoById(1, 1))
                .thenReturn(Optional.of(displayInfo));
        response.setDisplayInfo(displayInfo);

        list.add(response);

        // when
        Map<String, Object> reservationMap = reservationService.getReservations(1);

        // then
        List<ReservationResponse> returnRespList = (List<ReservationResponse>) reservationMap.get(RESERVATIONS);
        assertThat(returnRespList.size()).isEqualTo(list.size());
        assertThat(returnRespList.get(0).getDisplayInfo()).isEqualTo(list.get(0).getDisplayInfo());
        assertThat(returnRespList.get(0).getProductId()).isEqualTo(list.get(0).getProductId());

        Object size = reservationMap.get("size");
        assertThat(size).isEqualTo(1);
    }

    @DisplayName("예약하기 테스트")
    @Test
    void createReservationsTest() {
        // given
        ReservationResponse reservationResponse = new ReservationResponse();
        reservationResponse.setPrices(getReservationPriceDtoList());
        Mockito.when(myReservationDao.findReservationInfoPriceListById(0))
                .thenReturn(reservationResponse.getPrices());

        Mockito.when(myReservationDao.findReservationResponseById(0))
                .thenReturn(Optional.of(reservationResponse));


        // when
        ReservationRequest newReservationRequest = getNewReservationRequest();
        ReservationResponse response = reservationService.createReservations(newReservationRequest, 1);

        // then
        assertThat(response.getPrices().size()).isEqualTo(3);
    }

    @DisplayName("예약 취소 테스트")
    @Test
    void cancelReservationsTest() {
        // given
        ReservationResponse reservationResponse = new ReservationResponse();
        reservationResponse.setCancelYn(true);
        Mockito.when(myReservationDao.findReservationResponseById(0))
                .thenReturn(Optional.of(reservationResponse));

        // when
        ReservationResponse response = reservationService.setReservationCancel(0);

        // then
        assertThat(response.getCancelYn()).isEqualTo(true);
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