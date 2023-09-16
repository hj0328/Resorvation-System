package kr.or.connect.reservation.domain.reserve;

import kr.or.connect.reservation.domain.display.DisplayInfo;
import kr.or.connect.reservation.domain.reserve.dto.ReservationInfoDto;
import kr.or.connect.reservation.domain.reserve.dto.ReservationPriceDto;
import kr.or.connect.reservation.domain.reserve.dto.ReservationRequestDto;
import kr.or.connect.reservation.domain.reserve.dto.ReservationResponseDto;
import kr.or.connect.reservation.utils.UtilConstant;
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

@Repository
public class ReservationDao {
	private final NamedParameterJdbcTemplate jdbc;
	private final SimpleJdbcInsert jdbcInsertInfo;
	private final SimpleJdbcInsert jdbcInsertInfoPrice;

	private RowMapper<DisplayInfo> displayInfoRowMapper = BeanPropertyRowMapper.newInstance(DisplayInfo.class);
	private RowMapper<ReservationInfoDto> reservationInfoRowMapper = BeanPropertyRowMapper
			.newInstance(ReservationInfoDto.class);
	private RowMapper<ReservationResponseDto> reservationResponseRowMapper = BeanPropertyRowMapper
			.newInstance(ReservationResponseDto.class);
	private RowMapper<ReservationPriceDto> reservationPriceDtoRowMapper = BeanPropertyRowMapper
			.newInstance(ReservationPriceDto.class);

	public ReservationDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.jdbcInsertInfo = new SimpleJdbcInsert(dataSource)
				.withTableName("reservation_info")
				.usingGeneratedKeyColumns("id");
		this.jdbcInsertInfoPrice = new SimpleJdbcInsert(dataSource)
				.withTableName("reservation_info_price")
				.usingGeneratedKeyColumns("id");
	}

	public List<ReservationInfoDto> selectReservationInfoByEmail(String reservationEmail) {
		Map<String, String> params = new HashMap<>();
		params.put(UtilConstant.RESERVATION_EMAIL, reservationEmail);
		List<ReservationInfoDto> reservationInfo = jdbc.query(ReservationSql.SELECT_RESERVATION_INFO_BY_EMAIL, params,
				reservationInfoRowMapper);

		return reservationInfo;
	}

	public Optional<DisplayInfo> selectDisplayInfoById(Integer reservationInfoId, Integer displayInfoId) {
		Map<String, Integer> params = new HashMap<>();
		params.put(UtilConstant.DISPLAY_INFO_ID, displayInfoId);
		params.put(UtilConstant.RESERVATION_INFO_ID, reservationInfoId);

		DisplayInfo displayInfoDto = jdbc.queryForObject(ReservationSql.SELECT_DISPLAY_INFO_BY_ID, params, displayInfoRowMapper);
		return Optional.of(displayInfoDto);
	}

	public Optional<Integer> selectTotalPriceById(Integer reservationInfoId) {
		Map<String, Integer> params = new HashMap<>();
		params.put(UtilConstant.RESERVATION_INFO_ID, reservationInfoId);

		Integer totalPrice = jdbc.queryForObject(ReservationSql.SELECT_TOTAL_PRICE_BY_ID, params, Integer.class);
		return Optional.of(totalPrice);
	}

	public long insertReservationInfo(ReservationRequestDto reservationRequestDto) {
		BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(reservationRequestDto);
		Number key = jdbcInsertInfo.executeAndReturnKey(param);
		return key.longValue();
	}

	public void insertReservationInfoPrice(ReservationRequestDto reservationRequestDto, long reservationInfoId) {
		List<ReservationPriceDto> prices = reservationRequestDto.getPrices();
		for (ReservationPriceDto price : prices) {
			Map<String, Object> params = new HashMap<>();
			params.put("count", price.getCount());
			params.put("productPriceId", price.getProductPriceId());
			params.put("reservationInfoId", reservationInfoId);

			BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(price);
			jdbcInsertInfoPrice.executeAndReturnKey(param);
		}
	}

	public Optional<ReservationResponseDto> selectReservationResponseDto(long reservationInfoId) {
		Map<String, Long> params = new HashMap<>();
		params.put(UtilConstant.RESERVATION_INFO_ID, reservationInfoId);

		ReservationResponseDto reservationResponseDto = jdbc.queryForObject(ReservationSql.SELECT_RESERVATION_INFO_BY_ID, params,
				reservationResponseRowMapper);
		return Optional.of(reservationResponseDto);
	}

	public List<ReservationPriceDto> selectReservationInfoPriceDtoList(long reservationInfoId) {
		Map<String, Long> params = new HashMap<>();
		params.put(UtilConstant.RESERVATION_INFO_ID, reservationInfoId);

		return jdbc.query(ReservationSql.SELECT_RESERVATION_INFO_PRICE_BY_ID, params, reservationPriceDtoRowMapper);
	}

	public void cancelReservation(int reservationInfoId) {
		SqlParameterSource params = new MapSqlParameterSource()
				.addValue(UtilConstant.RESERVATION_INFO_ID, reservationInfoId);

		jdbc.update(ReservationSql.UPDATE_RESERVATION_CANCEL, params);
	}
}
