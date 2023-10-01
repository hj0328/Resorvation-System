package kr.or.connect.reservation.domain.reserve;

import kr.or.connect.reservation.config.exception.CustomException;
import kr.or.connect.reservation.config.exception.CustomExceptionStatus;
import kr.or.connect.reservation.domain.display.DisplayInfo;
import kr.or.connect.reservation.domain.reserve.dto.ReservationInfoDto;
import kr.or.connect.reservation.domain.reserve.dto.ReservationPriceDto;
import kr.or.connect.reservation.domain.reserve.dto.ReservationRequestDto;
import kr.or.connect.reservation.domain.reserve.dto.ReservationResponseDto;
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
		List<ReservationInfoDto> reservationInfoList = reservationDao.selectReservationInfoByEmail(reservationEmail);

		for (ReservationInfoDto reservationInfo : reservationInfoList) {
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
	public ReservationResponseDto createReservations(ReservationRequestDto reservationRequest) {
		long reservationInfoId = reservationDao.insertReservationInfo(reservationRequest);
		reservationDao.insertReservationInfoPrice(reservationRequest, reservationInfoId);

		// 사용자 Type 예약 수에 따라 업데이트
		updateUserType(reservationRequest);

		// 응답 데이터 생성 후 리턴
		return getReservationResponse(reservationInfoId);
	}

	private ReservationResponseDto getReservationResponse(long reservationInfoId) {
		ReservationResponseDto reservationResponseDto = reservationDao.selectReservationResponseDto(reservationInfoId)
				.orElseThrow(() -> new CustomException(CustomExceptionStatus.RESERVATION_NOT_FOUND));

		List<ReservationPriceDto> prices = reservationDao
				.selectReservationInfoPriceDtoList(reservationInfoId);
		reservationResponseDto.setPrices(prices);
		return reservationResponseDto;
	}

	private void updateUserType(ReservationRequestDto reservationRequest) {
		AtomicInteger reservationCount = new AtomicInteger();
		List<ReservationPriceDto> newReservationList = reservationRequest.getPrices();
		newReservationList.listIterator()
				.forEachRemaining(reservationPriceDto -> reservationCount.addAndGet(reservationPriceDto.getCount()));

		userService.updateUserGrade(reservationRequest.getId(), reservationCount.get());
	}

	@Transactional
	@Override
	public ReservationResponseDto cancelReservation(int reservationInfoId) {
		reservationDao.cancelReservation(reservationInfoId);

		// 응답 데이터 생성 후 리턴
		return getReservationResponse(reservationInfoId);
	}
}
