package kr.or.connect.reservation.dao;

public class ReservationDaoSqls {
	// 카테고리 DAO용 쿼리문
	public static final String SELECT_CATEGORY = "SELECT count(*) AS count, c.id, c.name "
											   + "FROM category c, product p, display_info di "
											   + "WHERE c.id = p.category_id AND p.id = di.product_id "
											   + "GROUP BY c.name, c.id ";
	
	// 상품 DAO용 쿼리문
	public static final String SELECT_PRODUCTS = "SELECT info.id AS displayInfoId, info.place_name,content AS productContent ,description AS productDescription, product.id AS productId, finfo.save_file_name AS productImageUrl "
												+ "FROM category, product, display_info info , product_image image, file_info finfo "
												+ "WHERE category.id = :categoryId AND category.id = product.category_id  AND product.id = info.product_id "
												+ "AND image.type = 'th' AND product.id = image.product_id AND image.file_id = finfo.id "
												+ "LIMIT 0, 4 ";
	
	// 프로모션 DAO용 쿼리문 
	public static final String SELECT_PROMOTION = "SELECT p2.id, p.id AS productId, fi.save_file_name AS productImageUrl "
												+ "FROM product p, promotion p2, product_image pimage, file_info fi "
												+ "WHERE p.id = p2.product_id AND pimage.type = 'th' AND p.id = pimage.product_id AND pimage.file_id = fi.id ";
}
