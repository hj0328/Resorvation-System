package kr.or.connect.reservation.dao;

import static kr.or.connect.reservation.dao.ReservationDaoSqls.*;

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

	/*
	 * categoryId 인 상품에 대해 조회
	 */
	public List<ProductItem> selectProducts(Integer categoryId, Integer start) {
		String sql = new String(SELECT_PRODUCTS);

		Map<String, Integer> params = new HashMap<>();
		params.put("categoryId", categoryId);
		params.put("start", start);

		List<ProductItem> productList = jdbc.query(sql, params, rowMapper);
		return productList;
	}

	/*
	 * 전체 상품에 대해 조회 (categoryId = 0)
	 */
	public List<ProductItem> selectAllProducts(Integer start) {
		String sql = new String(SELECT_ALL_PRODUCTS);

		Map<String, Integer> params = new HashMap<>();
		params.put("start", start);

		List<ProductItem> productList = jdbc.query(sql, params, rowMapper);
		return productList;
	}


	public int selectProductCntById(int categoryId) {
		String sql = new String(SELECT_PRODUCTS_CNT_BY_ID);
		Map<String, Integer> params = new HashMap<>();

		if(categoryId == 0) {
			sql = sql.replace("category.id = :categoryId AND", "");
		} else {
			params.put("categoryId", categoryId);
		}

		return jdbc.queryForObject(sql, params, Integer.class);
	}
}
