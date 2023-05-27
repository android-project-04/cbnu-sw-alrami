package cbnu.io.cbnuswalrami.test.acceptance.member;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.web.member.application.service.ChangeNicknameService;
import cbnu.io.cbnuswalrami.business.web.member.presentation.ChangeNicknameAPI;
import cbnu.io.cbnuswalrami.business.web.member.presentation.response.MemberDto;
import cbnu.io.cbnuswalrami.business.web.util.MemberFindUtil;
import cbnu.io.cbnuswalrami.common.configuration.container.AcceptanceTestBase;
import cbnu.io.cbnuswalrami.common.configuration.security.TokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.restdocs.request.RequestParametersSnippet;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("유저의 닉네임을 변경하는 API 인수트스트")
class ChangeMemberNicknameAPITest extends AcceptanceTestBase {

    @Autowired
    private TokenProvider tokenProvider;

    private MockMvc mockMvc;

    @Mock
    private MemberFindUtil memberFindUtil;

    @InjectMocks
    private ChangeNicknameAPI changeNicknameAPI;


    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(changeNicknameAPI)
                .apply(
                        documentationConfiguration(restDocumentation)
                                .operationPreprocessors()
                                .withRequestDefaults(prettyPrint())
                                .withResponseDefaults(prettyPrint())
                )
                .build();
    }

    @Mock
    private ChangeNicknameService changeNicknameService;

    @DisplayName("유저의 닉네임을 변경하는 기능을 테스트한다.")
    @Test
    void given_member_when_change_nickname_then_status_code_200() throws Exception {

        String newNickname = "newNickname";
        Member member = memberFindUtil.findMemberByAuthentication();

        given(changeNicknameService.changeNickname(newNickname, member)).willReturn(new MemberDto(
                1L,
                "changeNickname",
                2020110110
        ));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = tokenProvider.createToken(authentication);

        this.mockMvc.perform(put("/api/member/nickname")
                        .param("nickname", newNickname)
                        .header("Authorization", "Bearer " + token)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(document(
                        "put-member-nickname",
                        getRequestParametersSnippet(),
                        getResponseFieldsSnippet()
                ));
    }

    private static RequestParametersSnippet getRequestParametersSnippet() {
        return requestParameters(
                parameterWithName("nickname").description("바꿀 유저의 닉네임")
        );
    }


    private static ResponseFieldsSnippet getResponseFieldsSnippet() {
        return responseFields(
                fieldWithPath("eventTime").type(JsonFieldType.STRING).description("이벤트 시간"),
                fieldWithPath("status").type(JsonFieldType.STRING).description("HTTP 상태값"),
                fieldWithPath("code").type(JsonFieldType.NUMBER).description("HTTP 상태 코드"),
                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("유저의 id값"),
                fieldWithPath("data.nickname").type(JsonFieldType.STRING).description("바뀐 닉네임"),
                fieldWithPath("data.studentNumber").type(JsonFieldType.NUMBER).description("유저의 학번")
        );
    }
}
