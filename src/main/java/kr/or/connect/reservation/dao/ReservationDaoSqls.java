package kr.or.connect.reservation.dao;

public class ReservationDaoSqls {
	// 카테고리 DAO용 쿼리문
	public static final String SELECT_CATEGORY = "SELECT count(*) AS count, c.id, c.name "
											   + "FROM category c, product p, display_info di "
											   + "WHERE c.id = p.category_id AND p.id = di.product_id "
											   + "GROUP BY c.name, c.id ";

	// 상품 DAO용 쿼리문
	public static final String SELECT_ALL_PRODUCTS = "SELECT info.id AS displayInfoId, info.place_name,content AS productContent ,description AS productDescription, product.id AS productId, finfo.save_file_name AS productImageUrl "
											   + "FROM category, product, display_info info , product_image image, file_info finfo "
											   + "WHERE category.id = product.category_id  AND product.id = info.product_id "
											   + "AND image.type = 'th' AND product.id = image.product_id AND image.file_id = finfo.id "
											   + "LIMIT :start, 4 ";


	public static final String SELECT_PRODUCTS = "SELECT info.id AS displayInfoId, info.place_name,content AS productContent ,description AS productDescription, product.id AS productId, finfo.save_file_name AS productImageUrl "
												+ "FROM category, product, display_info info , product_image image, file_info finfo "
												+ "WHERE category.id = :categoryId AND category.id = product.category_id  AND product.id = info.product_id "
												+ "AND image.type = 'th' AND product.id = image.product_id AND image.file_id = finfo.id "
												+ "LIMIT :start, 4 ";

	public static final String SELECT_PRODUCTS_COUNT_BY_ID = "SELECT count(*) "
												+ "FROM category, product, display_info info , product_image image, file_info finfo "
												+ "WHERE category.id = :categoryId AND category.id = product.category_id  AND product.id = info.product_id "
												+ "AND image.type = 'th' AND product.id = image.product_id AND image.file_id = finfo.id ";

	public static final String SELECT_COMMENT_BY_DISPLAY_ID = "SELECT ruc.comment, ruc.id AS commentId, ruc.create_date, ruc.modify_date, p.id AS product_id "
												+ ", ri.reservation_date, ri.reservation_email, ri.id AS reservationInfoId, ri.reservation_name, ri.reservation_tel AS reservationTelephone, ruc.score "
												+ "FROM display_info di, product p, reservation_info ri, reservation_user_comment ruc "
												+ "WHERE di.product_id = p.id AND p.id = ruc.product_id AND ri.id = ruc.reservation_info_id "
												+ "AND di.id = :displayInfoId ";

	public static final String SELECT_COMMENT_AVERAGE_SCORE_BY_DISPLAY_ID = "SELECT avg(ruc.score) AS averageScore "
												+ "FROM display_info di, product p, reservation_user_comment ruc, reservation_info ri "
												+ "WHERE di.id = :displayInfoId AND di.product_id = p.id AND p.id = ruc.product_id AND ri.id = ruc.reservation_info_id ";

	public static final String SELECT_COMMENT_IMAGE_BY_COMMENT_ID = "SELECT fi.content_type, fi.create_date, fi.delete_flag, fi.id AS fileId, fi.file_name, fi.save_file_name, ruci.id AS iamgeId, fi.modify_date, ruci.reservation_info_id, ruci.reservation_user_comment_id "
												+ "FROM file_info fi, reservation_user_comment_image ruci "
												+ "WHERE ruci.id = :commentId AND fi.id = ruci.file_id ";

	public static final String SELECT_DISPLAYINFO_BY_DISPLAY_ID = "SELECT c.id AS categoryId, c.name AS categoryName, di.create_date, di.id AS displayInfoId, di.email, di.homepage, di.modify_date "
													+ ", di.opening_hours, di.place_lot, di.place_street, di.place_name, p.content AS productContent, p.description AS productDescription, p.event AS productEvent, p.id AS productId, di.tel AS telephone "
												+ "FROM display_info di, product p, category c "
												+ "WHERE di.id = :displayInfoId AND di.product_id = p.id AND p.category_id = c.id ";

	public static final String SELECT_PRODUCT_IMAGE_BY_DISPLAY_ID = "SELECT p.id AS productId, pi2.id AS productImageId, pi2.`type` , fi.id AS fileInfoId, fi.file_name, fi.save_file_name, fi.content_type, fi.delete_flag, fi.create_date, fi.modify_date "
												+ "FROM product p, display_info di, product_image pi2, file_info fi "
												+ "WHERE di.id = :displayInfoId  AND p.id = di.product_id AND p.id = pi2.product_id AND pi2.file_id = fi.id AND pi2.`type` IN ('ma', 'et') ";

	public static final String SELECT_PRODUCT_PRICE_BY_DISPLAY_ID = "SELECT pp.create_date, pp.discount_rate, pp.modify_date, pp.price, pp.price_type_name, pp.product_id, pp.id AS productPriceId "
												+ "FROM display_info di, product p, product_price pp "
												+ "WHERE di.id = :displayInfoId AND di.product_id = p.id AND p.id = pp.product_id ";

	public static final String SELECT_DISPLAY_INFO_IMG_BY_DISPLAY_ID = "SELECT fi.content_type, fi.create_date, fi.delete_flag, dii.display_info_id, dii.id AS displayInfoImageId, dii.file_id, fi.file_name, fi.modify_date, fi.save_file_name "
												+ "FROM display_info di, display_info_image dii, file_info fi "
												+ "WHERE di.id = :displayInfoId  AND di.id = dii.display_info_id AND dii.file_id = fi.id ";

	// 프로모션 DAO용 쿼리문
	public static final String SELECT_PROMOTION = "SELECT p2.id, p.id AS productId, fi.save_file_name AS productImageUrl "
												+ "FROM product p, promotion p2, product_image pimage, file_info fi "
												+ "WHERE p.id = p2.product_id AND pimage.type = 'th' AND p.id = pimage.product_id AND pimage.file_id = fi.id ";
}
