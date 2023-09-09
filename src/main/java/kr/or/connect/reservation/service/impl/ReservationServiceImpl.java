package kr.or.connect.reservation.service.impl;

import kr.or.connect.reservation.dao.MyReservationDao;
import kr.or.connect.reservation.dto.DisplayInfo;
import kr.or.connect.reservation.dto.ReservationInfoDto;
import kr.or.connect.reservation.dto.ReservationPriceDto;
import kr.or.connect.reservation.dto.reqeust.ReservationRequestDto;
import kr.or.connect.reservation.dto.response.ReservationResponseDto;
import kr.or.connect.reservation.service.ReservationService;
import kr.or.connect.reservation.utils.UtilConstant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReservationServiceImpl implements ReservationService {

	private final MyReservationDao myReservationDao;

	public ReservationServiceImpl(MyReservationDao myReservationDao) {
		this.myReservationDao = myReservationDao;
	}

	@Override
	public Map<String, Object> getReservations(String reservationEmail) {
		List<ReservationInfoDto> reservationInfos = myReservationDao.selectReservationInfoByEmail(reservationEmail);

		for (ReservationInfoDto reservationInfo : reservationInfos) {
			Integer reservationInfoId = reservationInfo.getReservationInfoId();
			Integer displayInfoId = reservationInfo.getDisplayInfoId();

			DisplayInfo displayInfo = myReservationDao.selectDisplayInfoById(reservationInfoId, displayInfoId);
			reservationInfo.setDisplayInfo(displayInfo);

			Integer totalPrice = myReservationDao.selectTotalPriceById(reservationInfoId);
			reservationInfo.setTotalPrice(totalPrice);
		}

		HashMap<String, Object> reservationMap = new HashMap<>();
		reservationMap.put(UtilConstant.RESERVATIONS, reservationInfos);
		reservationMap.put(UtilConstant.SIZE, reservationInfos.size());
		return reservationMap;
	}

	@Transactional
	@Override
	public ReservationResponseDto createReservations(ReservationRequestDto reservationRequestDto) {
		long reservationInfoId = myReservationDao.insertReservationInfo(reservationRequestDto);
		myReservationDao.insertReservationInfoPrice(reservationRequestDto, reservationInfoId);

		// 응답 데이터 생성
		ReservationResponseDto reservationResponseDto = myReservationDao
				.selectReservationResponseDto(reservationInfoId);

		List<ReservationPriceDto> prices = myReservationDao
				.selectReservationInfoPriceDtoList(reservationInfoId);
		reservationResponseDto.setPrices(prices);

		return reservationResponseDto;
	}

	@Transactional
	@Override
	public ReservationResponseDto cancelReservation(int reservationInfoId) {
		myReservationDao.cancelReservation(reservationInfoId);

		ReservationResponseDto reservationResponseDto = myReservationDao
				.selectReservationResponseDto(reservationInfoId);

		List<ReservationPriceDto> prices = myReservationDao
				.selectReservationInfoPriceDtoList(reservationInfoId);
		reservationResponseDto.setPrices(prices);
		return reservationResponseDto;
	}
}
