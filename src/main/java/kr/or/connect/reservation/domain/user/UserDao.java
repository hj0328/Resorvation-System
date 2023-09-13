package kr.or.connect.reservation.domain.user;

import kr.or.connect.reservation.domain.user.dto.UserDto;
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

    public UserDto selectUserByEmail(String email) {
        Map<String, Object> params = new HashMap<>();
        params.put("email", email);
       return template.queryForObject(SELECT_USER_BY_EMAIL, params, UserDaoRowMapper);
    }

    public UserType selectUserTypeByEmail(String email) {
        Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        return template.queryForObject(SELECT_TYPE_BY_EMAIL, params, UserType.class);
    }

    public Integer selectUserTotalReservationCount(String email) {
        Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        return template.queryForObject(SELECT_TOTAL_RESERVATION_COUNT_BY_EMAIL, params, Integer.class);
    }

    public String selectUserPassword(String userEmail) {
        Map<String, Object> params = new HashMap<>();
        params.put("email", userEmail);

        return template.queryForObject(SELECT_PASSWORD_BY_EMAIL, params, String.class);
    }

    public int updateTypeAndTotalReservationCount(String email, UserType userType
            , Integer totalReservationCount) {
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("email", email)
                .addValue("type", userType.toString())
                .addValue("totalReservationCount", totalReservationCount);
        return template.update(UPDATE_TYPE_AND_TOTAL_RESERVATION_COUNT_BY_EMAIL, param);
    }
}
