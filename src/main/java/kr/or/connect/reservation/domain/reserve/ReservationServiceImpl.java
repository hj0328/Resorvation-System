package kr.or.connect.reservation.domain.reserve;

import kr.or.connect.reservation.config.exception.CustomException;
import kr.or.connect.reservation.config.exception.CustomExceptionStatus;
import kr.or.connect.reservation.domain.reserve.dto.ReservationInfo;
import kr.or.connect.reservation.domain.reserve.dto.ReservationPrice;
import kr.or.connect.reservation.domain.reserve.dto.ReservationRequest;
import kr.or.connect.reservation.domain.reserve.dto.ReservationResponse;
import kr.or.connect.reservation.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static kr.or.connect.reservation.utils.UtilConstant.RESERVATIONS;
import static kr.or.connect.reservation.utils.UtilConstant.SIZE;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

	private final UserService userService;
	private final ReservationDao reservationDao;

	@Override
	public Map<String, Object> getReservations(Integer userId) {
		List<ReservationInfo> reservationInfoList = reservationDao.findReservationInfoByUserId(userId);
		List<ReservationResponse> responseList = new ArrayList<>();

		for (ReservationInfo info : reservationInfoList) {
			ReservationResponse response = convertToResponse(info);

			Integer reservationInfoId = info.getReservationInfoId();
			Integer displayInfoId = info.getDisplayInfoId();
			response.setDisplayInfo(reservationDao.findDisplayInfoById(reservationInfoId, displayInfoId)
					.orElseThrow(() -> new CustomException(CustomExceptionStatus.RESERVATION_NOT_FOUND)));

			response.setTotalPrice(reservationDao.findTotalPriceById(reservationInfoId)
					.orElseThrow(() -> new CustomException(CustomExceptionStatus.RESERVATION_NOT_FOUND)));

			responseList.add(response);
		}

		HashMap<String, Object> reservationMap = new HashMap<>();
		reservationMap.put(RESERVATIONS, responseList);
		reservationMap.put(SIZE, responseList.size());
		return reservationMap;
	}

	private ReservationResponse convertToResponse(ReservationInfo info) {
		ReservationResponse response = new ReservationResponse();
		response.setCancelYn(info.getCancelYn());
		response.setReservationDate(info.getReservationDate());
		response.setReservationName(info.getReservationName());
		response.setReservationEmail(info.getReservationEmail());
		response.setReservationTelephone(info.getReservationTelephone());
		response.setProductId(info.getProductId());
		response.setModifyDate(info.getModifyDate());
		return response;
	}

	@Transactional
	@Override
	public ReservationResponse createReservations(ReservationRequest request, Integer userId) {
		ReservationInfo reservationInfo = convertToReservationInfo(request, userId);

		Integer reservationInfoId = reservationDao.saveReservationInfo(reservationInfo);
		List<ReservationPrice> prices = request.getPrices();
		reservationDao.saveReservationInfoPrice(prices, reservationInfoId);

		// 사용자 Type 예약 수에 따라 등급 업데이트
		updateUserType(request, userId);

		// 응답 데이터 생성 후 리턴
		return getReservationResponse(reservationInfoId);
	}

	private ReservationInfo convertToReservationInfo(ReservationRequest request, Integer userId) {
		ReservationInfo reservationInfo = new ReservationInfo();
		reservationInfo.setDisplayInfoId(request.getDisplayInfoId());
		reservationInfo.setProductId(request.getProductId());
		reservationInfo.setReservationEmail(request.getReservationEmail());
		reservationInfo.setReservationName(request.getReservationName());
		reservationInfo.setReservationTelephone(request.getReservationTelephone());
		reservationInfo.setReservationDate(request.getReservationYearMonthDay());
		reservationInfo.setUserId(userId);
		return reservationInfo;
	}

	private ReservationResponse getReservationResponse(Integer reservationInfoId) {
		ReservationResponse reservationResponse = reservationDao.findReservationResponseById(reservationInfoId)
				.orElseThrow(() -> new CustomException(CustomExceptionStatus.RESERVATION_NOT_FOUND));

		List<ReservationPrice> prices = reservationDao
				.findReservationInfoPriceListById(reservationInfoId);
		reservationResponse.setPrices(prices);
		return reservationResponse;
	}

	private void updateUserType(ReservationRequest reservationRequest, Integer userId) {
		AtomicInteger reservationCount = new AtomicInteger();
		List<ReservationPrice> newReservationList = reservationRequest.getPrices();
		newReservationList.listIterator()
				.forEachRemaining(reservationPrice -> reservationCount.addAndGet(reservationPrice.getCount()));

		userService.updateUserGrade(userId, reservationCount.get());
	}

	@Transactional
	@Override
	public ReservationResponse setReservationCancel(int reservationInfoId) {
		reservationDao.updateReservationCancel(reservationInfoId);
		ReservationResponse response = getReservationResponse(reservationInfoId);
		return response;
	}
}
