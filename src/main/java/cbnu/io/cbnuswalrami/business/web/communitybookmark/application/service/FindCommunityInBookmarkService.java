package cbnu.io.cbnuswalrami.business.web.communitybookmark.application.service;

import cbnu.io.cbnuswalrami.business.core.domon.communitybookmark.infrastructure.query.CommunityBookmarkQuery;
import cbnu.io.cbnuswalrami.business.web.common.RequestId;
import cbnu.io.cbnuswalrami.business.web.community.presentation.response.ResponseCommunity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FindCommunityInBookmarkService {

    private final CommunityBookmarkQuery communityBookmarkQuery;

    @Transactional(readOnly = true)
    public ResponseCommunity findCommunityByBookmarkId(RequestId bookmarkId) {
        ResponseCommunity responseCommunity = communityBookmarkQuery.findResponseCommunityByBookmarkId(bookmarkId.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 북마크의 게시물을 찾을 수 없습니다."));

        return responseCommunity;
    }
}
