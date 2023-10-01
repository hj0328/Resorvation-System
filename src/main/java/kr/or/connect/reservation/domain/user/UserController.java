package kr.or.connect.reservation.domain.user;

import kr.or.connect.reservation.domain.user.dto.User;
import kr.or.connect.reservation.domain.user.dto.UserRequest;
import kr.or.connect.reservation.domain.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static kr.or.connect.reservation.utils.UtilConstant.USER_EMAIL;
import static kr.or.connect.reservation.utils.UtilConstant.USER_ID;

@Slf4j
@Controller
@RestController
@RequestMapping(path = "/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserResponse join(@Valid @RequestBody UserRequest userRequest) {
        return userService.join(userRequest);
    }

    @PostMapping("/session")
    public UserResponse login(HttpServletRequest request, @Valid @RequestBody UserRequest userRequest) {
        UserResponse userResponse = new UserResponse();
        User user = userService.login(userRequest.getEmail(), userRequest.getPassword());

        HttpSession session = request.getSession();
        session.setAttribute(USER_EMAIL, user.getEmail());
        session.setAttribute(USER_ID, user.getId());
        log.info("login userID={}", user.getId());

        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());
        userResponse.setGrade(user.getGrade());
        return userResponse;
    }

    @DeleteMapping("/session")
    public Map<String, String> logout(HttpServletRequest request) {
        log.info("delete session={}", request.getSession().getId());

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        Map<String, String> map = new HashMap<>();
        map.put(USER_EMAIL, "expired");
        return map;
    }
}
