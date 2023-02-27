package kr.or.connect.reservation.dao;

import static kr.or.connect.reservation.dao.sql.PromotionDaoSqls.SELECT_PROMOTION;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.connect.reservation.dto.PromotionItemDto;

@Repository
public class PromotionDao {
	private NamedParameterJdbcTemplate jdbc;
    private RowMapper<PromotionItemDto> rowMapper = BeanPropertyRowMapper.newInstance(PromotionItemDto.class);

    public PromotionDao(DataSource dataSource) {
    	jdbc = new NamedParameterJdbcTemplate(dataSource);
    }
    
	public List<PromotionItemDto> selectPromotions() {
		return jdbc.query(SELECT_PROMOTION, rowMapper);
    }
}
