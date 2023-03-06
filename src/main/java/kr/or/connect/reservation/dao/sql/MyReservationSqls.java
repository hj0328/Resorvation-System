package kr.or.connect.reservation.dao.sql;

/*
 *  '나의 예매내역 확인' 페이지 관련 sql문
 */
public class MyReservationSqls {
	public static final String SELECT_RESERVATION_INFO_BY_EMAIL = "SELECT id AS 'reservationInfoId', product_id 'productId', display_info_id 'displayInfoId', "
			+ "reservation_name 'reservationName', reservation_tel 'reservationTelephone', reservation_email 'reservationEmail' "
			+ ", IF(cancel_flag = 0, 'false', 'true') AS 'cancelYn' "
			+ ", reservation_date 'reservationDate', create_date 'createDate', modify_date 'modifyDate' "
			+ "FROM reservation_info ri " + "WHERE ri.reservation_email  = :reservationEmail ";

	public static final String SELECT_DISPLAY_INFO_BY_EMAIL = "SELECT c.id 'categoryId', c.name 'categoryName', di.create_date 'createDate', di.id 'displayInfoId', di.email, di.homepage, "
			+ "di.modify_date 'modifyDate', di.opening_hours 'openingHours', di.place_lot 'placeLot', "
			+ "di.place_name 'placeName', di.place_street 'placeStreet', "
			+ "p.content 'productContent', p.description 'productDescription', p.event 'productEvent', p.id 'productId', di.tel 'telephone' "
			+ "FROM reservation_info ri, display_info di, product p, category c "
			+ "WHERE ri.reservation_email = :reservationEmail AND ri.display_info_id = di.id AND ri.product_id = p.id AND di.product_id = p.id AND p.category_id = c.id ";

	public static final String SELECT_TOTAL_PRICE_BY_EMAIL = "SELECT SUM(pp.price) 'totalPrice' "
			+ "FROM reservation_info ri, reservation_info_price rip, product_price pp "
			+ "WHERE ri.reservation_email  = :reservationEmail "
			+ "AND ri.id = rip.reservation_info_id AND rip.product_price_id = pp.id ";

	public static final String INSERT_RESERVATION_INFO = "INSERT INTO reservation_info "
			+ "VALUES( ((SELECT max(ri.id) FROM reservation_info ri) +1), "
			+ ":productId, :displayInfoId, :reservationName, :reservationTelephone, :reservationEmail, :reservationTime, 0, now(), now()) ";

	public static final String INSERT_RESERVATION_INFO_PRICE = "INSERT INTO reservation_info_price "
			+ "VALUES (((SELECT max(rip.id) FROM reservation_info_price rip) +1), :count, :productPriceId, :reservationInfoId) ";

	public static final String UPDATE_RESERVATION_CANCEL = "UPDATE reservation_info SET cancel_flag = 1 WHERE id = :reservationInfoId ";
}
