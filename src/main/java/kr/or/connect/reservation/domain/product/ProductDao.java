package kr.or.connect.reservation.domain.product;

import kr.or.connect.reservation.domain.product.dto.ProductItemDto;
import kr.or.connect.reservation.domain.product.dto.ProductPriceDto;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static kr.or.connect.reservation.domain.product.ProductDaoSql.*;

@Repository
public class ProductDao {
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<ProductItemDto> rowMapper = BeanPropertyRowMapper.newInstance(ProductItemDto.class);
	private RowMapper<ProductPriceDto> proudcPriceRowMapper = BeanPropertyRowMapper.newInstance(ProductPriceDto.class);

	public ProductDao(DataSource dataSource) {
		jdbc = new NamedParameterJdbcTemplate(dataSource);
	}

	public List<ProductItemDto> selectProducts(Integer categoryId, Integer start) {
		Map<String, Integer> params = new HashMap<>();
		params.put("categoryId", categoryId);
		params.put("start", start);

		return jdbc.query(SELECT_PRODUCTS, params, rowMapper);
	}

	public List<ProductItemDto> selectAllProducts(Integer start) {
		Map<String, Integer> params = new HashMap<>();
		params.put("start", start);

		return jdbc.query(SELECT_ALL_PRODUCTS, params, rowMapper);
	}

	public Optional<Integer> selectProductCountById(int categoryId) {
		Map<String, Integer> params = new HashMap<>();
		String sql = SELECT_PRODUCTS_COUNT_BY_ID;

		if (categoryId == 0) {
			sql = SELECT_PRODUCTS_COUNT_BY_ID.replace("category.id = :categoryId AND", "");
		} else {
			params.put("categoryId", categoryId);
		}
		Integer productCnt = jdbc.queryForObject(sql, params, Integer.class);
		return Optional.of(productCnt);
	}

	public Optional<Double> selectAverageScore(int displayInfoId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("displayInfoId", displayInfoId);

		Double averageScore = jdbc.queryForObject(SELECT_COMMENT_AVERAGE_SCORE_BY_DISPLAY_ID, params, Double.class);
		return Optional.of(averageScore);
	}

	public List<ProductPriceDto> selectProductPrice(int displayInfoId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("displayInfoId", displayInfoId);
		return jdbc.query(SELECT_PRODUCT_PRICE_BY_DISPLAY_ID, params, proudcPriceRowMapper);
	}
}
