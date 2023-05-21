package kr.or.connect.reservation.dao;

import static kr.or.connect.reservation.dao.sql.MyReservationSqls.INSERT_RESERVATION_INFO;
import static kr.or.connect.reservation.dao.sql.MyReservationSqls.INSERT_RESERVATION_INFO_PRICE;
import static kr.or.connect.reservation.dao.sql.MyReservationSqls.SELECT_DISPLAY_INFO_BY_EMAIL;
import static kr.or.connect.reservation.dao.sql.MyReservationSqls.SELECT_RESERVATION_INFO_BY_EMAIL;
import static kr.or.connect.reservation.dao.sql.MyReservationSqls.SELECT_RESERVATION_INFO_MAX_ID;
import static kr.or.connect.reservation.dao.sql.MyReservationSqls.SELECT_TOTAL_PRICE_BY_EMAIL;
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

import kr.or.connect.reservation.dto.DisplayInfoDto;
import kr.or.connect.reservation.dto.ReservationInfoDto;
import kr.or.connect.reservation.dto.ReservationPriceDto;
import kr.or.connect.reservation.dto.reqeust.ReservationRequestDto;

@Repository
public class MyReservationDao {
	private NamedParameterJdbcTemplate jdbc;

	private RowMapper<DisplayInfoDto> displayInfoRowMapper = BeanPropertyRowMapper.newInstance(DisplayInfoDto.class);
	private RowMapper<ReservationInfoDto> reservationInfoRowMapper = BeanPropertyRowMapper
			.newInstance(ReservationInfoDto.class);

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

	public DisplayInfoDto selectDisplayInfoByEmail(String reservationEmail) {
		Map<String, String> params = new HashMap<>();
		params.put("reservationEmail", reservationEmail);

		DisplayInfoDto displayInfoDto = jdbc.queryForObject(SELECT_DISPLAY_INFO_BY_EMAIL, params, displayInfoRowMapper);
		return displayInfoDto;
	}

	public Integer selectTotalPriceByEmail(String reservationEmail) {
		Map<String, String> params = new HashMap<>();
		params.put("reservationEmail", reservationEmail);

		Integer totalPrice = jdbc.queryForObject(SELECT_TOTAL_PRICE_BY_EMAIL, params, Integer.class);
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

	public int selectReservationInfo() {
		Integer maxId = jdbc.queryForObject(SELECT_RESERVATION_INFO_MAX_ID, new HashMap<>(), Integer.class);
		return maxId;
	}

	public void insertReservationInfoPrice(ReservationRequestDto reservationRequestDto, int maxId) {
		List<ReservationPriceDto> prices = reservationRequestDto.getPrices();
		for (ReservationPriceDto price : prices) {
			Map<String, Object> params = new HashMap<>();
			params.put("count", price.getCount());
			params.put("productPriceId", price.getProductPriceId());
			params.put("reservationInfoId", maxId);

			jdbc.update(INSERT_RESERVATION_INFO_PRICE, params);
		}
	}

	public void cancelReservation(int reservationInfoId) {
		Map<String, Object> params = new HashMap<>();
		params.put("reservationInfoId", reservationInfoId);

		jdbc.update(UPDATE_RESERVATION_CANCEL, params);
	}
}
