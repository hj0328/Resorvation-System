package kr.or.connect.reservation.domain.reserve;

import kr.or.connect.reservation.config.exception.CustomException;
import kr.or.connect.reservation.config.exception.CustomExceptionStatus;
import kr.or.connect.reservation.domain.display.DisplayInfo;
import kr.or.connect.reservation.domain.reserve.dto.ReservationInfo;
import kr.or.connect.reservation.domain.reserve.dto.ReservationPrice;
import kr.or.connect.reservation.domain.reserve.dto.ReservationRequest;
import kr.or.connect.reservation.domain.reserve.dto.ReservationResponse;
import kr.or.connect.reservation.domain.user.UserService;
import kr.or.connect.reservation.utils.UtilConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

	private final UserService userService;
	private final ReservationDao reservationDao;

	@Override
	public Map<String, Object> getReservations(String reservationEmail) {
		List<ReservationInfo> reservationInfoList = reservationDao.selectReservationInfoByEmail(reservationEmail);

		for (ReservationInfo reservationInfo : reservationInfoList) {
			Integer reservationInfoId = reservationInfo.getReservationInfoId();
			Integer displayInfoId = reservationInfo.getDisplayInfoId();

			DisplayInfo displayInfo = reservationDao.selectDisplayInfoById(reservationInfoId, displayInfoId)
					.orElseThrow(() -> new CustomException(CustomExceptionStatus.RESERVATION_NOT_FOUND));
			reservationInfo.setDisplayInfo(displayInfo);

			Integer totalPrice = reservationDao.selectTotalPriceById(reservationInfoId)
					.orElseThrow(() -> new CustomException(CustomExceptionStatus.RESERVATION_NOT_FOUND));

			reservationInfo.setTotalPrice(totalPrice);
		}

		HashMap<String, Object> reservationMap = new HashMap<>();
		reservationMap.put(UtilConstant.RESERVATIONS, reservationInfoList);
		reservationMap.put(UtilConstant.SIZE, reservationInfoList.size());
		return reservationMap;
	}

	@Transactional
	@Override
	public ReservationResponse createReservations(ReservationRequest reservationRequest) {
		long reservationInfoId = reservationDao.insertReservationInfo(reservationRequest);
		reservationDao.insertReservationInfoPrice(reservationRequest, reservationInfoId);

		// 사용자 Type 예약 수에 따라 업데이트
		updateUserType(reservationRequest);

		// 응답 데이터 생성 후 리턴
		return getReservationResponse(reservationInfoId);
	}

	private ReservationResponse getReservationResponse(long reservationInfoId) {
		ReservationResponse reservationResponse = reservationDao.selectReservationResponse(reservationInfoId)
				.orElseThrow(() -> new CustomException(CustomExceptionStatus.RESERVATION_NOT_FOUND));

		List<ReservationPrice> prices = reservationDao
				.selectReservationInfoPriceList(reservationInfoId);
		reservationResponse.setPrices(prices);
		return reservationResponse;
	}

	private void updateUserType(ReservationRequest reservationRequest) {
		AtomicInteger reservationCount = new AtomicInteger();
		List<ReservationPrice> newReservationList = reservationRequest.getPrices();
		newReservationList.listIterator()
				.forEachRemaining(reservationPrice -> reservationCount.addAndGet(reservationPrice.getCount()));

		userService.updateUserGrade(reservationRequest.getId(), reservationCount.get());
	}

	@Transactional
	@Override
	public ReservationResponse cancelReservation(int reservationInfoId) {
		reservationDao.cancelReservation(reservationInfoId);

		// 응답 데이터 생성 후 리턴
		return getReservationResponse(reservationInfoId);
	}
}
