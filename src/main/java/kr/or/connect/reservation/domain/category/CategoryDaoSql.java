package kr.or.connect.reservation.domain.category;

public class CategoryDaoSql {
	public static final String SELECT_CATEGORY = "SELECT count(*) AS count, c.id, c.name "
											   + "FROM category c, product p, display_info di "
											   + "WHERE c.id = p.category_id AND p.id = di.product_id "
											   + "GROUP BY c.name, c.id ";
}
