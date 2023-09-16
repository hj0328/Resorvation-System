package kr.or.connect.reservation.domain.user;

import kr.or.connect.reservation.domain.user.dto.UserDto;
import org.springframework.dao.EmptyResultDataAccessException;
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
import java.util.Map;
import java.util.Optional;

import static kr.or.connect.reservation.domain.user.UserSqls.*;

@Repository
public class UserDao {
    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;
    private RowMapper<UserDto> UserDaoRowMapper = BeanPropertyRowMapper.newInstance(UserDto.class);

    public UserDao(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("user_info")
                .usingGeneratedKeyColumns("user_id");
    }

    public int insertUser(UserDto userDto) {
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(userDto);
        Number key = jdbcInsert.executeAndReturnKey(param);

        return key.intValue();
    }

    public Optional<UserDto> selectUserByEmail(String email) {
        Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        try {
            UserDto userDto = template.queryForObject(SELECT_USER_BY_EMAIL, params, UserDaoRowMapper);
            return Optional.of(userDto);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<UserType> selectUserTypeById(Integer userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        try {
            UserType userType = template.queryForObject(SELECT_TYPE_BY_ID, params, UserType.class);
            return Optional.of(userType);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Integer> selectUserTotalReservationCountById(Integer userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("uerId", userId);
        try {
            Integer totalReservationCount = template.queryForObject(SELECT_TOTAL_RESERVATION_COUNT_BY_ID, params, Integer.class);
            return Optional.of(totalReservationCount);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public int updateTypeAndTotalReservationCountById(Integer userId, UserType userType
            , Integer totalReservationCount) {
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("userId", userId)
                .addValue("type", userType.toString())
                .addValue("totalReservationCount", totalReservationCount);
        return template.update(UPDATE_TYPE_AND_TOTAL_RESERVATION_COUNT_BY_ID, param);
    }
}
