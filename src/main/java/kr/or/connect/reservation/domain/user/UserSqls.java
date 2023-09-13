package kr.or.connect.reservation.domain.user;

public class UserSqls {
    public static final String INSERT_USER_INFO = "INSERT INTO user_info(email, name, password, type) VALUES (:email, :name, :password, :type) ";
    public static final String SELECT_USER_BY_EMAIL = "SELECT email, name, password, type FROM user_info WHERE email= :email";
    public static final String SELECT_PASSWORD_BY_EMAIL = "SELECT password FROM user_info WHERE email= :email";
    public static final String SELECT_TOTAL_RESERVATION_COUNT_BY_EMAIL = "SELECT total_reservation_count FROM user_info WHERE email= :email";
    public static final String SELECT_TYPE_BY_EMAIL = "SELECT type FROM user_info WHERE email= :email";

    public static final String UPDATE_TYPE_AND_TOTAL_RESERVATION_COUNT_BY_EMAIL = "UPDATE user_info SET total_reservation_count=:totalReservationCount, type=:type WHERE email= :email";
}
