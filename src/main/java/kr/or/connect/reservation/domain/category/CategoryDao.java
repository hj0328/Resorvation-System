package kr.or.connect.reservation.domain.category;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryDao {
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<CategoryItemDto> rowMapper = BeanPropertyRowMapper.newInstance(CategoryItemDto.class);

	public CategoryDao(DataSource dataSource) {
		jdbc = new NamedParameterJdbcTemplate(dataSource);
	}

	public List<CategoryItemDto> selectAll() {
		return jdbc.query(CategoryDaoSql.SELECT_CATEGORY, rowMapper);
	}
}
