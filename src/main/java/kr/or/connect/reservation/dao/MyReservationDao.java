package kr.or.connect.reservation.dao;

import static kr.or.connect.reservation.dao.sql.MyReservationSqls.INSERT_RESERVATION_INFO;
import static kr.or.connect.reservation.dao.sql.MyReservationSqls.INSERT_RESERVATION_INFO_PRICE;
import static kr.or.connect.reservation.dao.sql.MyReservationSqls.SELECT_DISPLAY_INFO_BY_ID;
import static kr.or.connect.reservation.dao.sql.MyReservationSqls.SELECT_RESERVATION_INFO_BY_EMAIL;
import static kr.or.connect.reservation.dao.sql.MyReservationSqls.SELECT_RESERVATION_INFO_BY_ID;
import static kr.or.connect.reservation.dao.sql.MyReservationSqls.SELECT_RESERVATION_INFO_MAX_ID;
import static kr.or.connect.reservation.dao.sql.MyReservationSqls.SELECT_RESERVATION_INFO_PRICE_BY_ID;
import static kr.or.connect.reservation.dao.sql.MyReservationSqls.SELECT_TOTAL_PRICE_BY_ID;
import static kr.or.connect.reservation.dao.sql.MyReservationSqls.UPDATE_RESERVATION_CANCEL;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.connect.reservation.dto.DisplayInfo;
import kr.or.connect.reservation.dto.ReservationInfoDto;
import kr.or.connect.reservation.dto.ReservationPriceDto;
import kr.or.connect.reservation.dto.reqeust.ReservationRequestDto;
import kr.or.connect.reservation.dto.response.ReservationResponseDto;

@Repository
public class MyReservationDao {
	private NamedParameterJdbcTemplate jdbc;

	private RowMapper<DisplayInfo> displayInfoRowMapper = BeanPropertyRowMapper.newInstance(DisplayInfo.class);
	private RowMapper<ReservationInfoDto> reservationInfoRowMapper = BeanPropertyRowMapper
			.newInstance(ReservationInfoDto.class);
	private RowMapper<ReservationResponseDto> reservationResponseRowMapper = BeanPropertyRowMapper
			.newInstance(ReservationResponseDto.class);
	private RowMapper<ReservationPriceDto> reservationPriceDtoRowMapper = BeanPropertyRowMapper
			.newInstance(ReservationPriceDto.class);

	public MyReservationDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}

	public List<ReservationInfoDto> selectReservationInfoByEmail(String reservationEmail) {
		Map<String, String> params = new HashMap<>();
		params.put("reservationEmail", reservationEmail);
		List<ReservationInfoDto> reservationInfos = jdbc.query(SELECT_RESERVATION_INFO_BY_EMAIL, params,
				reservationInfoRowMapper);

		return reservationInfos;
	}

	public DisplayInfo selectDisplayInfoById(Integer reservationInfoId, Integer displayInfoId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("displayInfoId", displayInfoId);
		params.put("reservationInfoId", reservationInfoId);

		DisplayInfo displayInfoDto = jdbc.queryForObject(SELECT_DISPLAY_INFO_BY_ID, params, displayInfoRowMapper);
		return displayInfoDto;
	}

	public Integer selectTotalPriceById(Integer reservationInfoId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("reservationInfoId", reservationInfoId);

		Integer totalPrice = jdbc.queryForObject(SELECT_TOTAL_PRICE_BY_ID, params, Integer.class);
		return totalPrice;
	}

	public void insertReservationInfo(ReservationRequestDto reservationRequestDto) {
		Map<String, Object> params = new HashMap<>();
		params.put("productId", reservationRequestDto.getProductId());
		params.put("displayInfoId", reservationRequestDto.getDisplayInfoId());
		params.put("reservationName", reservationRequestDto.getReservationName());
		params.put("reservationTelephone", reservationRequestDto.getReservationTelephone());
		params.put("reservationEmail", reservationRequestDto.getReservationEmail());

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
		LocalDateTime dateTime = LocalDate.parse(reservationRequestDto.getReservationYearMonthDay(), formatter)
				.atStartOfDay();
		params.put("reservationTime", dateTime);

		jdbc.update(INSERT_RESERVATION_INFO, params);
	}

	public int selectReservationInfoMaxId() {
		Integer reservationInfoId = jdbc.queryForObject(SELECT_RESERVATION_INFO_MAX_ID, new HashMap<>(), Integer.class);
		return reservationInfoId;
	}

	public int selectReservationInfoPriceMaxId() {
		Integer reservationInfoPriceId = jdbc.queryForObject(SELECT_RESERVATION_INFO_MAX_ID, new HashMap<>(),
				Integer.class);
		return reservationInfoPriceId;
	}

	public void insertReservationInfoPrice(ReservationRequestDto reservationRequestDto, int reservationInfoId) {
		List<ReservationPriceDto> prices = reservationRequestDto.getPrices();
		for (ReservationPriceDto price : prices) {
			Map<String, Object> params = new HashMap<>();
			params.put("count", price.getCount());
			params.put("productPriceId", price.getProductPriceId());
			params.put("reservationInfoId", reservationInfoId);

			jdbc.update(INSERT_RESERVATION_INFO_PRICE, params);
		}
	}

	public ReservationResponseDto selectReservationResponseDto(int reservationInfoId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("reservationInfoId", reservationInfoId);

		ReservationResponseDto reservationResponseDto = jdbc.queryForObject(SELECT_RESERVATION_INFO_BY_ID, params,
				reservationResponseRowMapper);
		return reservationResponseDto;
	}

	public List<ReservationPriceDto> selectReservationInfoPriceDtoList(int reservationInfoPriceId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("reservationInfoPriceId", reservationInfoPriceId);

		return jdbc.query(SELECT_RESERVATION_INFO_PRICE_BY_ID, params, reservationPriceDtoRowMapper);
	}

	public void cancelReservation(int reservationInfoId) {
		Map<String, Object> params = new HashMap<>();
		params.put("reservationInfoId", reservationInfoId);

		jdbc.update(UPDATE_RESERVATION_CANCEL, params);
	}
}
