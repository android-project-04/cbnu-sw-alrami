package cbnu.io.cbnuswalrami.business.web.member.presentation.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ResponseMyPage {

    private String nickname;

    private Integer studentNumber;

    List<ResponseMyPageCommunity> responseMyPageCommunities;

    public ResponseMyPage(String nickname, Integer studentNumber, List<ResponseMyPageCommunity> responseMyPageCommunities) {
        this.nickname = nickname;
        this.studentNumber = studentNumber;
        this.responseMyPageCommunities = responseMyPageCommunities;
    }
}
