package kr.or.connect.reservation.domain.reservation;

import kr.or.connect.reservation.utils.UtilConstant;

/*
 *  '나의 예매내역 확인' 페이지 관련 sql문
 */
public class ReservationSql {
	public static final String SELECT_RESERVATION_INFO_BY_EMAIL = "SELECT ri.id AS 'reservationInfoId', product_id 'productId', display_info_id, "
			+ "reservation_name, reservation_tel 'reservationTelephone', reservation_email "
			+ ", IF(cancel_flag = 0, 'false', 'true') AS 'cancelYn' "
			+ ", reservation_date, ri.create_date, ri.modify_date "
			+ "FROM reservation_info ri JOIN user_info ifo ON ri.user_id = ifo.id "
			+ "WHERE ifo.id = :userId ";

	public static final String SELECT_TOTAL_PRICE_BY_ID = "SELECT SUM(pp.price) 'totalPrice' "
			+ "FROM reservation_info ri, reservation_info_price rip, product_price pp "
			+ "WHERE ri.id  = :reservationInfoId "
			+ "AND ri.id = rip.reservation_info_id AND rip.product_price_id = pp.id ";

	public static final String SELECT_RESERVATION_INFO_BY_ID = "SELECT id 'reservationInfoId', product_id, display_info_id, reservation_name, "
			+ "reservation_tel 'reservationTelephone', reservation_email , reservation_date , cancel_flag 'cancelYn', create_date, modify_date "
			+ "FROM reservation_info ri WHERE ri.id  = :reservationInfoId; ";

	public static final String SELECT_RESERVATION_INFO_PRICE_BY_ID = "SELECT id 'reservationInfoPriceId', reservation_info_id , product_price_id, count "
			+ "FROM reservation_info_price rip "
			+ "WHERE rip.reservation_info_id = :reservationInfoId ; ";

	public static final String UPDATE_RESERVATION_CANCEL = "UPDATE reservation_info SET cancel_flag = "
			+ UtilConstant.CANCEL_FLAG_Y + " WHERE id = :reservationInfoId ";

	public static final String SELECT_PRODUCT_ID_BY_ID = "SELECT product_id FROM reservation_info WHERE id = :reservationInfoId ";
}
