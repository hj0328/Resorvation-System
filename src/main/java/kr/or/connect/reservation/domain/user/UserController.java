package kr.or.connect.reservation.domain.user;

import kr.or.connect.reservation.domain.user.dto.UserDto;
import kr.or.connect.reservation.domain.user.dto.UserRequestDto;
import kr.or.connect.reservation.domain.user.dto.UserResponseDto;
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
@RequestMapping(path = "/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/login")
    public UserResponseDto login(HttpServletRequest request, @Validated UserRequestDto userRequestDto) {

        UserResponseDto userResponse = new UserResponseDto();
        UserDto user = userService.login(userRequestDto.getEmail(), userRequestDto.getPassword());

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

    @PostMapping("/register")
    public UserResponseDto register(@Validated UserRequestDto userRequestDto,
                                    BindingResult bindingResult) {
        log.info("POST /register UserRequestDto={}", userRequestDto);
        return userService.register(userRequestDto);
    }
}
