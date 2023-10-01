package kr.or.connect.reservation.domain.user.dao;

import kr.or.connect.reservation.domain.user.dto.User;
import kr.or.connect.reservation.domain.user.dto.UserGrade;
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

import static kr.or.connect.reservation.domain.user.UserSql.*;

@Repository
public class UserDaoJdbcTemplate implements UserDao {
    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;
    private RowMapper<User> UserDaoRowMapper = BeanPropertyRowMapper.newInstance(User.class);

    public UserDaoJdbcTemplate(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("user_info")
                .usingGeneratedKeyColumns("user_id");
    }

    @Override
    public int saveUser(User user) {
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(user);
        Number pk = jdbcInsert.executeAndReturnKey(param);

        return pk.intValue();
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        try {
            User user = template.queryForObject(SELECT_USER_BY_EMAIL, params, UserDaoRowMapper);
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserGrade> findUserTypeById(Integer id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        try {
            UserGrade userGrade = template.queryForObject(SELECT_GRADE_BY_ID, params, UserGrade.class);
            return Optional.of(userGrade);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Integer> findUserTotalReservationCountById(Integer id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        try {
            Integer totalReservationCount = template.queryForObject(SELECT_TOTAL_RESERVATION_COUNT_BY_ID, params, Integer.class);
            return Optional.of(totalReservationCount);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public int updateTypeAndTotalReservationCountById(Integer id, UserGrade userGrade
            , Integer totalReservationCount) {
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("grade", userGrade.toString())
                .addValue("totalReservationCount", totalReservationCount);
        return template.update(UPDATE_TYPE_AND_TOTAL_RESERVATION_COUNT_BY_ID, param);
    }
}
