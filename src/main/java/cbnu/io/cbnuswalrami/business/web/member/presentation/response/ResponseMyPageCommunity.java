package cbnu.io.cbnuswalrami.business.web.member.presentation.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseMyPageCommunity {

    private Long communityId;

    private String title;

    @QueryProjection
    public ResponseMyPageCommunity(Long communityId, String title) {
        this.communityId = communityId;
        this.title = title;
    }
}
