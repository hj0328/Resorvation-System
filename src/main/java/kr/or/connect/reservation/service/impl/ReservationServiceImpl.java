package kr.or.connect.reservation.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
		int maxId = myReservationDao.selectReservationInfo();
		myReservationDao.insertReservationInfoPrice(reservationRequestDto, maxId);

		// [PJT-5] 예약취소는 실제 DB 에 적용된 값이 아닌, Random으로 생성된 예약 객체를 반환한다.
		return this.getRandomValueReservationResponseDto();
	}

	@Override
	public ReservationResponseDto cancelReservation(int reservationInfoId) {
		myReservationDao.cancelReservation(reservationInfoId);

		// [PJT-5] 예약취소는 실제 DB 에 적용된 값이 아닌, Random으로 생성된 예약 객체를 반환한다.
		return this.getRandomValueReservationResponseDto();
	}

	private ReservationResponseDto getRandomValueReservationResponseDto() {
		ReservationResponseDto reservationResponse = new ReservationResponseDto();
		reservationResponse.setCancelYn(false);
		reservationResponse.setCreateDate(LocalDateTime.now().toString());
		reservationResponse.setDisplayInfoId((int) Math.random() * 10);

		ReservationPriceDto reservationPriceDto = new ReservationPriceDto();
		reservationPriceDto.setCount(1);
		reservationPriceDto.setProductPriceId(0);
		reservationPriceDto.setReservationInfoId(0);
		reservationPriceDto.setReservationInfoPriceId(0);
		List<ReservationPriceDto> reservationPriceDtoList = new ArrayList<>();
		reservationPriceDtoList.add(reservationPriceDto);
		reservationResponse.setPrices(reservationPriceDtoList);

		reservationResponse.setModifyDate(LocalDateTime.now().toString());
		reservationResponse.setProductId((int) Math.random() * 10);
		reservationResponse.setReservationDate(LocalDateTime.now().toString());
		reservationResponse.setReservationEmail("crong@naver.com");
		reservationResponse.setReservationInfoId((int) Math.random() * 10);
		reservationResponse.setReservationName("crong");
		reservationResponse.setReservationTelephone("010-0101-0101");
		return reservationResponse;
	}

}
