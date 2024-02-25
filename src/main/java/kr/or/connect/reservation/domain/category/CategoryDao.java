package kr.or.connect.reservation.domain.category;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class CategoryDao {
	private final NamedParameterJdbcTemplate jdbc;
	private final RowMapper<CategoryItemDto> rowMapper = BeanPropertyRowMapper.newInstance(CategoryItemDto.class);

	public CategoryDao(DataSource dataSource) {
		jdbc = new NamedParameterJdbcTemplate(dataSource);
	}

	public List<CategoryItemDto> selectAll() {
		return jdbc.query(CategoryDaoSql.SELECT_CATEGORY, rowMapper);
	}
}
