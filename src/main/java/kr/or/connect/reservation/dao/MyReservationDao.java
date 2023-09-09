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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import kr.or.connect.reservation.utils.UtilConstant;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
		params.put(UtilConstant.RESERVATION_EMAIL, reservationEmail);
		List<ReservationInfoDto> reservationInfos = jdbc.query(SELECT_RESERVATION_INFO_BY_EMAIL, params,
				reservationInfoRowMapper);

		return reservationInfos;
	}

	public DisplayInfo selectDisplayInfoById(Integer reservationInfoId, Integer displayInfoId) {
		Map<String, Integer> params = new HashMap<>();
		params.put(UtilConstant.DISPLAY_INFO_ID, displayInfoId);
		params.put(UtilConstant.RESERVATION_INFO_ID, reservationInfoId);

		DisplayInfo displayInfoDto = jdbc.queryForObject(SELECT_DISPLAY_INFO_BY_ID, params, displayInfoRowMapper);
		return displayInfoDto;
	}

	public Integer selectTotalPriceById(Integer reservationInfoId) {
		Map<String, Integer> params = new HashMap<>();
		params.put(UtilConstant.RESERVATION_INFO_ID, reservationInfoId);

		Integer totalPrice = jdbc.queryForObject(SELECT_TOTAL_PRICE_BY_ID, params, Integer.class);
		return totalPrice;
	}

	public long insertReservationInfo(ReservationRequestDto reservationRequestDto) {
		KeyHolder keyHolder = new GeneratedKeyHolder();

		SqlParameterSource params = new MapSqlParameterSource()
				.addValue("productId", reservationRequestDto.getProductId())
				.addValue("displayInfoId", reservationRequestDto.getDisplayInfoId())
				.addValue("reservationName", reservationRequestDto.getReservationName())
				.addValue("reservationTelephone", reservationRequestDto.getReservationTelephone())
				.addValue("reservationEmail", reservationRequestDto.getReservationEmail())
				.addValue("reservationTime", reservationRequestDto.getReservationYearMonthDay());

		jdbc.update(INSERT_RESERVATION_INFO, params, keyHolder);

		return (long) keyHolder.getKey();
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

	public void insertReservationInfoPrice(ReservationRequestDto reservationRequestDto, long reservationInfoId) {
		List<ReservationPriceDto> prices = reservationRequestDto.getPrices();
		for (ReservationPriceDto price : prices) {
			Map<String, Object> params = new HashMap<>();
			params.put("count", price.getCount());
			params.put("productPriceId", price.getProductPriceId());
			params.put("reservationInfoId", reservationInfoId);

			jdbc.update(INSERT_RESERVATION_INFO_PRICE, params);
		}
	}

	public ReservationResponseDto selectReservationResponseDto(long reservationInfoId) {
		Map<String, Long> params = new HashMap<>();
		params.put("reservationInfoId", reservationInfoId);

		ReservationResponseDto reservationResponseDto = jdbc.queryForObject(SELECT_RESERVATION_INFO_BY_ID, params,
				reservationResponseRowMapper);
		return reservationResponseDto;
	}

	public List<ReservationPriceDto> selectReservationInfoPriceDtoList(long reservationInfoId) {
		Map<String, Long> params = new HashMap<>();
		params.put("reservationInfoId", reservationInfoId);

		return jdbc.query(SELECT_RESERVATION_INFO_PRICE_BY_ID, params, reservationPriceDtoRowMapper);
	}

	public void cancelReservation(int reservationInfoId) {
		SqlParameterSource params = new MapSqlParameterSource()
				.addValue("reservationInfoId", reservationInfoId);

		jdbc.update(UPDATE_RESERVATION_CANCEL, params);
	}
}
