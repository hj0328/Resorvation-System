package kr.or.connect.reservation.domain.reserve;

import kr.or.connect.reservation.domain.display.DisplayInfo;
import kr.or.connect.reservation.domain.reserve.dto.ReservationInfo;
import kr.or.connect.reservation.domain.reserve.dto.ReservationPrice;
import kr.or.connect.reservation.domain.reserve.dto.ReservationResponse;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static kr.or.connect.reservation.domain.reserve.ReservationSql.*;
import static kr.or.connect.reservation.utils.UtilConstant.*;

@Repository
public class ReservationDao {
	private final NamedParameterJdbcTemplate jdbc;
	private final SimpleJdbcInsert jdbcInsertInfo;
	private final SimpleJdbcInsert jdbcInsertInfoPrice;

	private RowMapper<DisplayInfo> displayInfoRowMapper = BeanPropertyRowMapper.newInstance(DisplayInfo.class);
	private RowMapper<ReservationInfo> reservationInfoRowMapper = BeanPropertyRowMapper
			.newInstance(ReservationInfo.class);
	private RowMapper<ReservationResponse> reservationResponseRowMapper = BeanPropertyRowMapper
			.newInstance(ReservationResponse.class);
	private RowMapper<ReservationPrice> reservationPriceRowMapper = BeanPropertyRowMapper
			.newInstance(ReservationPrice.class);

	public ReservationDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.jdbcInsertInfo = new SimpleJdbcInsert(dataSource)
				.withTableName("reservation_info")
				.usingGeneratedKeyColumns("id");
		this.jdbcInsertInfoPrice = new SimpleJdbcInsert(dataSource)
				.withTableName("reservation_info_price")
				.usingGeneratedKeyColumns("id");
	}

	public List<ReservationInfo> findReservationInfoByUserId(Integer userId) {
		Map<String, Object> params = new HashMap<>();
		params.put(USER_ID, userId);
		List<ReservationInfo> reservationInfo = jdbc.query(SELECT_RESERVATION_INFO_BY_EMAIL,
				params, reservationInfoRowMapper);

		return reservationInfo;
	}

	public Optional<DisplayInfo> findDisplayInfoById(Integer reservationInfoId, Integer displayInfoId) {
		Map<String, Integer> params = new HashMap<>();
		params.put(DISPLAY_INFO_ID, displayInfoId);
		params.put(RESERVATION_INFO_ID, reservationInfoId);

		DisplayInfo displayInfo = jdbc.queryForObject(SELECT_DISPLAY_INFO_BY_ID, params, displayInfoRowMapper);
		return Optional.of(displayInfo);
	}

	public Optional<Integer> findTotalPriceById(Integer reservationInfoId) {
		Map<String, Integer> params = new HashMap<>();
		params.put(RESERVATION_INFO_ID, reservationInfoId);

		Integer totalPrice = jdbc.queryForObject(SELECT_TOTAL_PRICE_BY_ID, params, Integer.class);
		return Optional.of(totalPrice);
	}

	public Integer saveReservationInfo(ReservationInfo info) {
		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(info);
		Number key = jdbcInsertInfo.executeAndReturnKey(params);
		return key.intValue();
	}

	public void saveReservationInfoPrice(List<ReservationPrice> prices, long reservationInfoId) {
		for (ReservationPrice price : prices) {
			Map<String, Object> params = new HashMap<>();
			params.put("count", price.getCount());
			params.put("productPriceId", price.getProductPriceId());
			params.put("reservationInfoId", reservationInfoId);

			BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(price);
			jdbcInsertInfoPrice.executeAndReturnKey(param);
		}
	}

	public Optional<ReservationResponse> findReservationResponseById(Integer reservationInfoId) {
		Map<String, Integer> params = new HashMap<>();
		params.put(RESERVATION_INFO_ID, reservationInfoId);

		ReservationResponse reservationResponse = jdbc.queryForObject(SELECT_RESERVATION_INFO_BY_ID, params,
				reservationResponseRowMapper);
		return Optional.of(reservationResponse);
	}

	public List<ReservationPrice> findReservationInfoPriceListById(Integer reservationInfoId) {
		Map<String, Object> params = new HashMap<>();
		params.put(RESERVATION_INFO_ID, reservationInfoId);
		return jdbc.query(SELECT_RESERVATION_INFO_PRICE_BY_ID, params, reservationPriceRowMapper);
	}

	public void updateReservationCancel(int reservationInfoId) {
		SqlParameterSource params = new MapSqlParameterSource()
				.addValue(RESERVATION_INFO_ID, reservationInfoId);

		jdbc.update(UPDATE_RESERVATION_CANCEL, params);
	}

	public Optional<Integer> findProductIdById(Integer reservationInfoId) {
		Map<String, Object> params = new HashMap<>();
		params.put(RESERVATION_INFO_ID, reservationInfoId);
		Integer productId = jdbc.queryForObject(SELECT_PRODUCT_ID_BY_ID, params, Integer.class);
		return Optional.of(productId);
	}
}
