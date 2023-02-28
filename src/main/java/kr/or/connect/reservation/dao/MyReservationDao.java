package kr.or.connect.reservation.dao;

import static kr.or.connect.reservation.dao.sql.MyReservationSqls.SELECT_DISPLAY_INFO_BY_EMAIL;
import static kr.or.connect.reservation.dao.sql.MyReservationSqls.SELECT_RESERVATION_INFO_BY_EMAIL;
import static kr.or.connect.reservation.dao.sql.MyReservationSqls.SELECT_TOTAL_PRICE_BY_EMAIL;

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
}
