package kr.or.connect.reservation.dao;

import static kr.or.connect.reservation.dao.ReservationDaoSqls.SELECT_PRODUCTS;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.connect.reservation.dto.ProductItem;

@Repository
public class ProductDao {
	private NamedParameterJdbcTemplate jdbc;
    private RowMapper<ProductItem> rowMapper = BeanPropertyRowMapper.newInstance(ProductItem.class);

    public ProductDao(DataSource dataSource) {
		jdbc = new NamedParameterJdbcTemplate(dataSource);
	}
    
    public List<ProductItem> selectProducts(Integer categoryId, Integer start) {
    	String sql = new String(SELECT_PRODUCTS);
		Map<String, Integer> params = new HashMap<>();

		if(categoryId == null || categoryId == 0) {
			sql = sql.replace("category.id = :categoryId AND", "");
		} else {
			params.put("categoryId", categoryId);
		}

		if(start != 0) {
			sql = sql.replace("LIMIT 0", "LIMIT :start");
			params.put("start", start);
		}

		List<ProductItem> productList = jdbc.query(sql, params, rowMapper);
    	return productList;
    }
}
