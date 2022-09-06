package kr.or.connect.reservation.dao;

import static kr.or.connect.reservation.dao.ReservationDaoSqls.SELECT_CATEGORY;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.connect.reservation.dto.CategoryItemDto;

@Repository
public class CategoryDao {
	private NamedParameterJdbcTemplate jdbc;
    private RowMapper<CategoryItemDto> rowMapper = BeanPropertyRowMapper.newInstance(CategoryItemDto.class);
    	
	public CategoryDao(DataSource dataSource) {
		jdbc = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public List<CategoryItemDto> selectAll() {
		return jdbc.query(SELECT_CATEGORY, rowMapper);
	}
}
