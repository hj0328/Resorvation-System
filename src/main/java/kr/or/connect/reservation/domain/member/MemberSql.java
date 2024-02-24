package kr.or.connect.reservation.domain.member;

public class MemberSql {
    public static final String SELECT_USER_BY_EMAIL = "SELECT id , email, name, password, grade FROM user_info WHERE email= :email";
    public static final String SELECT_TOTAL_RESERVATION_COUNT_BY_ID = "SELECT total_reservation_count FROM user_info WHERE id = :id";
}
