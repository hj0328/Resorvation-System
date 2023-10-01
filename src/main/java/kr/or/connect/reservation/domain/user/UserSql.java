package kr.or.connect.reservation.domain.user;

public class UserSql {
    public static final String SELECT_USER_BY_EMAIL = "SELECT id , email, name, password, grade FROM user_info WHERE email= :email";
    public static final String SELECT_TOTAL_RESERVATION_COUNT_BY_ID = "SELECT total_reservation_count FROM user_info WHERE id = :id";
    public static final String SELECT_GRADE_BY_ID = "SELECT grade FROM user_info WHERE id= :id";

    public static final String UPDATE_TYPE_AND_TOTAL_RESERVATION_COUNT_BY_ID = "UPDATE user_info SET total_reservation_count=:totalReservationCount, grade=:grade WHERE id= :id";
}
