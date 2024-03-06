package kr.or.connect.reservation.domain.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.or.connect.reservation.config.exception.ExceptionAdvice;
import kr.or.connect.reservation.domain.member.dto.MemberRequest;
import kr.or.connect.reservation.domain.member.dto.MemberResponse;
import kr.or.connect.reservation.domain.member.entity.Member;
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

import static kr.or.connect.reservation.utils.UtilConstant.*;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class MemberControllerTest {

    @Mock
    private MemberService userService;

    @InjectMocks
    private MemberController userController;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MemberResponse response;
    private Member member;
    private MemberRequest request;

    @BeforeEach
    void initMockMvc() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .setControllerAdvice(new ExceptionAdvice())
                .build();
    }

    @BeforeEach
    void initDto() {
        request = MemberRequest.createMemberRequest("lee", "test@gmail.com", "test");
        member = Member.create("test@gmail.com", "lee", "test");
        response = MemberResponse.of(member);
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
                .andExpect(jsonPath("email").exists());
    }

    @Test
    @DisplayName("로그인 컨트롤러")
    void loginTest() throws Exception {
        Mockito.when(userService.login("test@gmail.com", "test"))
                .thenReturn(response);
        // 로그인 세션 확인!
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(request().sessionAttribute(MEMBER_ID, notNullValue()))
                .andExpect(request().sessionAttribute(USER_EMAIL, notNullValue()))
                .andExpect(jsonPath("email").exists());
    }

    @Test
    @DisplayName("로그아웃 컨트롤러")
    void logoutTest() throws Exception {

        MockHttpSession session = new MockHttpSession();
        session.setAttribute(MEMBER_ID, "session");

        // 로그아웃 세션없음 확인!
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .session(session)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(request().sessionAttribute(MEMBER_ID, nullValue()))
                .andExpect(request().sessionAttribute(USER_EMAIL, nullValue()));
    }
}