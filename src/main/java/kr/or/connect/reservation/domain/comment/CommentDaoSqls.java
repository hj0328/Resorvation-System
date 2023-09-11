package kr.or.connect.reservation.domain.comment;

public class CommentDaoSqls {
	public static final String SELECT_COMMENT_BY_DISPLAY_ID = "SELECT ruc.comment, ruc.id AS commentId, ruc.create_date, ruc.modify_date, p.id AS product_id "
			+ ", ri.reservation_date, ri.reservation_email, ri.id AS reservationInfoId, ri.reservation_name, ri.reservation_tel AS reservationTelephone, ruc.score "
			+ "FROM display_info di, product p, reservation_info ri, reservation_user_comment ruc "
			+ "WHERE di.product_id = p.id AND p.id = ruc.product_id AND ri.id = ruc.reservation_info_id "
			+ "AND di.id = :displayInfoId ";

	public static final String SELECT_COMMENT_IMAGE_BY_COMMENT_ID = "SELECT fi.content_type, fi.create_date, fi.delete_flag, fi.id AS fileId, fi.file_name, fi.save_file_name, ruci.id AS iamgeId, fi.modify_date, ruci.reservation_info_id, ruci.reservation_user_comment_id "
			+ "FROM file_info fi, reservation_user_comment_image ruci "
			+ "WHERE ruci.id = :commentId AND fi.id = ruci.file_id ";
}
