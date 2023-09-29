package kr.or.connect.reservation.domain.user;

import kr.or.connect.reservation.domain.user.dto.User;
import kr.or.connect.reservation.domain.user.dto.UserRequest;
import kr.or.connect.reservation.domain.user.dto.UserResponse;
import kr.or.connect.reservation.utils.UtilConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RestController
@RequestMapping(path = "/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/login")
    public UserResponse login(HttpServletRequest request, @Validated UserRequest userRequestDto) {
        UserResponse userResponse = new UserResponse();
        User user = userService.login(userRequestDto.getEmail(), userRequestDto.getPassword());

        HttpSession session = request.getSession();
        session.setAttribute(UtilConstant.LOGIN_ID, user.getEmail());

        userResponse.setUserId(user.getUserId());
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());
        userResponse.setType(user.getType());

        return userResponse;
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "expired";
    }

    @PostMapping("/join")
    public UserResponse join(@Validated UserRequest userRequestDto,
                                 BindingResult bindingResult) {
        log.info("POST /join UserRequestDto={}", userRequestDto);
        return userService.join(userRequestDto);
    }
}
