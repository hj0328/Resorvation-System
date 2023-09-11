package kr.or.connect.reservation.domain.promotion;

import static kr.or.connect.reservation.domain.promotion.PromotionDaoSqls.SELECT_PROMOTION;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

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
