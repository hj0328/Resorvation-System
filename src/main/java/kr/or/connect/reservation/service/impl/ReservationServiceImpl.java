package kr.or.connect.reservation.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.or.connect.reservation.utils.UtilConstant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.connect.reservation.dao.MyReservationDao;
import kr.or.connect.reservation.dto.DisplayInfo;
import kr.or.connect.reservation.dto.ReservationInfoDto;
import kr.or.connect.reservation.dto.ReservationPriceDto;
import kr.or.connect.reservation.dto.reqeust.ReservationRequestDto;
import kr.or.connect.reservation.dto.response.ReservationResponseDto;
import kr.or.connect.reservation.service.ReservationService;

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

			Integer totalPriceByEmail = myReservationDao.selectTotalPriceById(reservationInfoId);
			reservationInfo.setTotalPrice(totalPriceByEmail);
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

		// insert 값을 리턴하도록 수정
		ReservationResponseDto reservationResponseDto = myReservationDao
				.selectReservationResponseDto(reservationInfoId);

		List<ReservationPriceDto> prices = myReservationDao
				.selectReservationInfoPriceDtoList(reservationInfoId);
		reservationResponseDto.setPrices(prices);

		return reservationResponseDto;
	}

	@Override
	public ReservationResponseDto cancelReservation(int reservationInfoId) {
		myReservationDao.cancelReservation(reservationInfoId);

		// cancel 값을 리턴하도록 수정
		ReservationResponseDto reservationResponseDto = myReservationDao
				.selectReservationResponseDto(reservationInfoId);

		List<ReservationPriceDto> prices = myReservationDao
				.selectReservationInfoPriceDtoList(reservationInfoId);
		reservationResponseDto.setPrices(prices);
		return reservationResponseDto;
	}
}
