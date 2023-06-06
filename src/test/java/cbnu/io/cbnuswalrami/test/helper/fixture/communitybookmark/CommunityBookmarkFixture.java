package cbnu.io.cbnuswalrami.test.helper.fixture.communitybookmark;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.web.common.RequestId;
import cbnu.io.cbnuswalrami.business.web.community.presentation.response.ResponseCommunity;
import cbnu.io.cbnuswalrami.business.web.communitybookmark.application.service.SaveCommunityBookmarkService;
import cbnu.io.cbnuswalrami.business.web.util.MemberFindUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommunityBookmarkFixture {

    @Autowired
    private MemberFindUtil memberFindUtil;

    @Autowired
    private SaveCommunityBookmarkService saveCommunityBookmarkService;

    public List<ResponseCommunity> saveCommunitiesToBookmark(List<ResponseCommunity> communities) {
        Member member = memberFindUtil.findMemberByAuthentication();

        List<ResponseCommunity> responseCommunities = new ArrayList<>();

        for (ResponseCommunity community : communities) {
            ResponseCommunity responseCommunity = saveCommunityBookmarkService.saveCommunityBookmark(member, RequestId.from(community.getId()));
            responseCommunities.add(responseCommunity);
        }

        return responseCommunities;
    }

    public ResponseCommunity saveCommunityToBookmark(ResponseCommunity community) {
        Member member = memberFindUtil.findMemberByAuthentication();
        return saveCommunityBookmarkService.saveCommunityBookmark(member, RequestId.from(community.getId()));
    }
}
