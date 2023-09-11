package kr.or.connect.reservation.domain.category;

public class CategoryDaoSqls {
	// 카테고리 DAO용 쿼리문
	public static final String SELECT_CATEGORY = "SELECT count(*) AS count, c.id, c.name "
											   + "FROM category c, product p, display_info di "
											   + "WHERE c.id = p.category_id AND p.id = di.product_id "
											   + "GROUP BY c.name, c.id ";
}
