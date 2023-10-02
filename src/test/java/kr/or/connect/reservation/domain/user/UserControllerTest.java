package kr.or.connect.reservation.domain.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.or.connect.reservation.config.exception.ExceptionAdvice;
import kr.or.connect.reservation.domain.user.dto.User;
import kr.or.connect.reservation.domain.user.dto.UserGrade;
import kr.or.connect.reservation.domain.user.dto.UserRequest;
import kr.or.connect.reservation.domain.user.dto.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static kr.or.connect.reservation.utils.UtilConstant.USER_EMAIL;
import static kr.or.connect.reservation.utils.UtilConstant.USER_ID;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    private UserResponse response;
    private User user;
    private UserRequest request;

    @BeforeEach
    void initMockMvc() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .setControllerAdvice(new ExceptionAdvice())
                .build();
    }

    @BeforeEach
    void initDto() {
        request = new UserRequest();
        request.setName("lee");
        request.setEmail("test@gmail.com");
        request.setPassword("test");

        response = new UserResponse();
        response.setGrade(UserGrade.BASIC);
        response.setEmail("tset@gmail.com");
        response.setName("lee");

        user = new User();
        user.setName("lee");
        user.setEmail("test@gmail.com");
        user.setPassword("test");
        user.setId(1);
    }

    @Test
    @DisplayName("회원가입 컨트롤러")
    void joinTest() throws Exception {
        Mockito.when(userService.join(any()))
                .thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").exists())
                .andExpect(jsonPath("email").exists())
                .andExpect(jsonPath("grade").exists());
    }

    @Test
    @DisplayName("로그인 컨트롤러")
    void loginTest() throws Exception {
        Mockito.when(userService.login("test@gmail.com", "test"))
                .thenReturn(user);

        // 로그인 세션 확인!
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(request().sessionAttribute(USER_ID, notNullValue()))
                .andExpect(request().sessionAttribute(USER_EMAIL, notNullValue()))
                .andExpect(jsonPath("email").exists());
    }

    @Test
    @DisplayName("로그아웃 컨트롤러")
    void logoutTest() throws Exception {

        MockHttpSession session = new MockHttpSession();
        session.setAttribute(USER_ID, "session");

        // 로그아웃 세션없음 확인!
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .session(session)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(request().sessionAttribute(USER_ID, nullValue()))
                .andExpect(request().sessionAttribute(USER_EMAIL, nullValue()));
    }
}