package kr.or.connect.reservation.dao.sql;

public class PromotionDaoSqls {
	// 프로모션 DAO용 쿼리문
	public static final String SELECT_PROMOTION = "SELECT p2.id, p.id AS productId, fi.save_file_name AS productImageUrl "
												+ "FROM product p, promotion p2, product_image pimage, file_info fi "
												+ "WHERE p.id = p2.product_id AND pimage.type = 'th' AND p.id = pimage.product_id AND pimage.file_id = fi.id ";
}
