package cbnu.io.cbnuswalrami.business.web.communitybookmark.application.service.paging;

import cbnu.io.cbnuswalrami.business.core.domon.communitybookmark.infrastructure.query.CommunityBookmarkQuery;
import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.web.common.Cursor;
import cbnu.io.cbnuswalrami.business.web.common.CursorResult;
import cbnu.io.cbnuswalrami.business.web.community.presentation.response.ResponseCommunity;
import cbnu.io.cbnuswalrami.business.web.notificationbookmark.presentation.response.ResponseCommunityBookmark;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityBookmarkCursorPagingService {

    private final CommunityBookmarkQuery communityBookmarkQuery;

    @Transactional
    public CursorResult findCommunityBookmarkByCursor(Cursor cursor, Member member) {
        List<ResponseCommunityBookmark> responseCommunities = getResponseCommunities(
                cursor.getNext(),
                cursor.getSize(),
                member
        );
        Long lastIndex = responseCommunities.isEmpty() ? 0 : responseCommunities.get(responseCommunities.size() - 1).getId();
        Boolean hasNext = hasNext(lastIndex);
        return CursorResult.from(responseCommunities, hasNext, lastIndex);
    }

    public Boolean hasNext(Long lastIndex) {
        if (lastIndex <= 1) {
            return false;
        }
        return communityBookmarkQuery.hasNext(lastIndex);
    }

    public List<ResponseCommunityBookmark> getResponseCommunities(
            Long next,
            Long size,
            Member member
    ) {
        if (next == null) {
            return communityBookmarkQuery.findResponseCommunitiesByCursor(size, member);
        }
        return communityBookmarkQuery.findResponseCommunitiesByCursor(next, size, member);
    }
}
