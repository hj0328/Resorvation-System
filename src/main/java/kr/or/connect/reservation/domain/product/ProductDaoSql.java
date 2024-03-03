package kr.or.connect.reservation.domain.product;

import static kr.or.connect.reservation.utils.UtilConstant.PRODUCT_PAGE_SIZE;

// 상품 DAO용 쿼리문
public class ProductDaoSql {
	public static final String SELECT_ALL_PRODUCTS = "SELECT info.id AS displayInfoId, info.place_name,content AS productContent ,description AS productDescription, product.id AS productId, finfo.save_file_name AS productImageUrl "
			+ "FROM category, product, display_info info , product_image image, file_info finfo "
			+ "WHERE category.id = product.category_id  AND product.id = info.product_id "
			+ "AND image.type = 'th' AND product.id = image.product_id AND image.file_id = finfo.id "
			+ "LIMIT :start, " + PRODUCT_PAGE_SIZE;

	public static final String SELECT_PRODUCTS = "SELECT info.id AS displayInfoId, info.place_name,content AS productContent ,description AS productDescription, product.id AS productId, finfo.save_file_name AS productImageUrl "
			+ "FROM category, product, display_info info , product_image image, file_info finfo "
			+ "WHERE category.id = :categoryId AND category.id = product.category_id  AND product.id = info.product_id "
			+ "AND image.type = 'th' AND product.id = image.product_id AND image.file_id = finfo.id "
			+ "LIMIT :start, " + PRODUCT_PAGE_SIZE;

	public static final String SELECT_PRODUCTS_COUNT_BY_ID = "SELECT count(*) "
			+ "FROM category, product, display_info info , product_image image, file_info finfo "
			+ "WHERE category.id = :categoryId AND category.id = product.category_id  AND product.id = info.product_id "
			+ "AND image.type = 'th' AND product.id = image.product_id AND image.file_id = finfo.id ";

	public static final String SELECT_COMMENT_AVERAGE_SCORE_BY_DISPLAY_ID = "SELECT IFNULL(avg(ruc.score), 0.0) AS averageScore "
			+ "FROM display_info di, product p, reservation_user_comment ruc, reservation_info ri "
			+ "WHERE di.id = :displayInfoId AND di.product_id = p.id AND p.id = ruc.product_id AND ri.id = ruc.reservation_info_id ";

	public static final String SELECT_PRODUCT_PRICE_BY_DISPLAY_ID = "SELECT pp.create_date, pp.discount_rate, pp.modify_date, pp.price, pp.price_type_name, pp.product_id, pp.id AS productPriceId "
			+ "FROM display_info di, product p, product_price pp "
			+ "WHERE di.id = :displayInfoId AND di.product_id = p.id AND p.id = pp.product_id ";

}
