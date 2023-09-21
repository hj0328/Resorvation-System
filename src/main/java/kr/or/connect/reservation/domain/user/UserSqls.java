package kr.or.connect.reservation.domain.user;

public class UserSqls {
    public static final String SELECT_USER_BY_EMAIL = "SELECT user_id , email, name, password, type FROM user_info WHERE email= :email";
    public static final String SELECT_TOTAL_RESERVATION_COUNT_BY_ID = "SELECT total_reservation_count FROM user_info WHERE user_id = :userId";
    public static final String SELECT_TYPE_BY_ID = "SELECT type FROM user_info WHERE user_id= :userId";

    public static final String UPDATE_TYPE_AND_TOTAL_RESERVATION_COUNT_BY_ID = "UPDATE user_info SET total_reservation_count=:totalReservationCount, type=:type WHERE user_id= :userId";
}
