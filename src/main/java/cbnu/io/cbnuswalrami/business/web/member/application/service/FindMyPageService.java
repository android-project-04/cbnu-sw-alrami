package cbnu.io.cbnuswalrami.business.web.member.application.service;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.core.domon.post.community.infrastructure.query.CommunityQuery;
import cbnu.io.cbnuswalrami.business.web.member.presentation.response.ResponseMyPage;
import cbnu.io.cbnuswalrami.business.web.member.presentation.response.ResponseMyPageCommunity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindMyPageService {

    private final CommunityQuery communityQuery;

    @Transactional(readOnly = true)
    public ResponseMyPage findMyPage(Member member) {
        List<ResponseMyPageCommunity> responseMyPageCommunities = communityQuery.findMyPageCommunityByMember(member);

        ResponseMyPage responseMyPage = new ResponseMyPage(
                member.getNickname().getNickname(),
                member.getStudentNumber().getStudentNumber(),
                responseMyPageCommunities
        );
        return responseMyPage;
    }
}
