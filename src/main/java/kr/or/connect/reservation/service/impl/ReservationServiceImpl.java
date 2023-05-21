package kr.or.connect.reservation.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.connect.reservation.dao.MyReservationDao;
import kr.or.connect.reservation.dto.DisplayInfoDto;
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
			DisplayInfoDto displayInfo = myReservationDao.selectDisplayInfoByEmail(reservationEmail);
			reservationInfo.setDisplayInfoDto(displayInfo);

			Integer totalPriceByEmail = myReservationDao.selectTotalPriceByEmail(reservationEmail);
			reservationInfo.setTotalPrice(totalPriceByEmail);
		}

		HashMap<String, Object> reservationMap = new HashMap<>();
		reservationMap.put("reservations", reservationInfos);
		reservationMap.put("size", reservationInfos.size());

		return reservationMap;
	}

	@Transactional
	@Override
	public ReservationResponseDto createReservations(ReservationRequestDto reservationRequestDto) {
		myReservationDao.insertReservationInfo(reservationRequestDto);
		int reservationInfoId = myReservationDao.selectReservationInfoMaxId();
		myReservationDao.insertReservationInfoPrice(reservationRequestDto, reservationInfoId);

		// insert 값을 리턴하도록 수정
		ReservationResponseDto reservationResponseDto = myReservationDao
				.selectReservationResponseDto(reservationInfoId);

		int reservationInfoPriceMaxId = myReservationDao.selectReservationInfoPriceMaxId();
		List<ReservationPriceDto> prices = myReservationDao
				.selectReservationInfoPriceDtoList(reservationInfoPriceMaxId);
		reservationResponseDto.setPrices(prices);

		return reservationResponseDto;
	}

	@Override
	public ReservationResponseDto cancelReservation(int reservationInfoId) {
		myReservationDao.cancelReservation(reservationInfoId);

		// cancel 값을 리턴하도록 수정
		ReservationResponseDto reservationResponseDto = myReservationDao
				.selectReservationResponseDto(reservationInfoId);

		int reservationInfoPriceMaxId = myReservationDao.selectReservationInfoPriceMaxId();
		List<ReservationPriceDto> prices = myReservationDao
				.selectReservationInfoPriceDtoList(reservationInfoPriceMaxId);
		reservationResponseDto.setPrices(prices);
		return reservationResponseDto;
	}
}
