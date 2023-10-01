package kr.or.connect.reservation.config.authentication;

import kr.or.connect.reservation.utils.UtilConstant;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute(UtilConstant.USER_ID) == null) {
            response.sendRedirect("/");
            return false;
        }

        return true;
    }
}
