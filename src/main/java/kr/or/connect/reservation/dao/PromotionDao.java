package kr.or.connect.reservation.dao;

import static kr.or.connect.reservation.dao.ReservationDaoSqls.SELECT_PROMOTION;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.connect.reservation.dto.PromotionItem;

@Repository
public class PromotionDao {
	private NamedParameterJdbcTemplate jdbc;
    private RowMapper<PromotionItem> rowMapper = BeanPropertyRowMapper.newInstance(PromotionItem.class);

    public PromotionDao(DataSource dataSource) {
    	jdbc = new NamedParameterJdbcTemplate(dataSource);
    }
    
    public List<PromotionItem> selectPromotions() {
    	return jdbc.query(SELECT_PROMOTION, rowMapper);
    }
}
