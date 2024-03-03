package kr.or.connect.reservation.domain.member.dao;

import kr.or.connect.reservation.domain.member.dto.MemberDto;
import kr.or.connect.reservation.domain.member.entity.Member;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static kr.or.connect.reservation.domain.member.MemberSql.SELECT_TOTAL_RESERVATION_COUNT_BY_ID;
import static kr.or.connect.reservation.domain.member.MemberSql.SELECT_USER_BY_EMAIL;

@Repository
public class MemberDao {
    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;
    private final RowMapper<MemberDto> MemberDaoRowMapper = BeanPropertyRowMapper.newInstance(MemberDto.class);

    public MemberDao(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("user_info")
                .usingGeneratedKeyColumns("user_id");
    }

    public int saveMember(Member member) {
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(member);
        Number pk = jdbcInsert.executeAndReturnKey(param);

        return pk.intValue();
    }

    public Optional<MemberDto> findMemberByEmail(String email) {
        Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        try {
            MemberDto member = template.queryForObject(SELECT_USER_BY_EMAIL, params, MemberDaoRowMapper);
            return Optional.of(member);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Integer> findMemberTotalReservationCountById(Integer id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        try {
            Integer totalReservationCount = template.queryForObject(SELECT_TOTAL_RESERVATION_COUNT_BY_ID, params, Integer.class);
            return Optional.of(totalReservationCount);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

}
