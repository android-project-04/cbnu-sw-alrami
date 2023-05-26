package cbnu.io.cbnuswalrami.business.web.community.application.service.paging;

import cbnu.io.cbnuswalrami.business.core.domon.post.community.infrastructure.query.CommunityQuery;
import cbnu.io.cbnuswalrami.business.core.domon.post.values.CommunityType;
import cbnu.io.cbnuswalrami.business.web.common.Cursor;
import cbnu.io.cbnuswalrami.business.web.common.CursorResult;
import cbnu.io.cbnuswalrami.business.web.community.presentation.response.ResponseCommunity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static cbnu.io.cbnuswalrami.business.core.domon.post.values.CommunityType.*;

@Service
@RequiredArgsConstructor
public class OldCommunityCursorPagingService {

    private final CommunityQuery communityQuery;

    @Transactional(readOnly = true)
    public CursorResult findByCursor(Cursor cursor) {
        List<ResponseCommunity> responseCommunities = getResponseCommunities(cursor);
        Long lastIndex = responseCommunities.isEmpty() ? 0 : responseCommunities.get(responseCommunities.size() - 1).getId();
        Boolean hasNext = hasNext(lastIndex, COMMUNITY);

        return CursorResult.from(responseCommunities, hasNext, lastIndex);
    }

    private Boolean hasNext(Long lastIndex, CommunityType communityType) {
        if (lastIndex <= 1 || lastIndex == null) return false;
        return communityQuery.existOldCommunityId(lastIndex, communityType);
    }

    private List<ResponseCommunity> getResponseCommunities(Cursor cursor) {
        if (cursor.getNext() == null) {
            return communityQuery.findResponseOldCommunities(cursor.getSize());
        }
        return communityQuery.findResponseOldCommunities(cursor.getNext(), cursor.getSize());
    }

    @Transactional(readOnly = true)
    public CursorResult findEmploymentCommunities(Cursor cursor) {
        List<ResponseCommunity> responseCommunities = getResponseEmploymentCommunities(cursor);
        Long lastIndex = responseCommunities.isEmpty() ? 0 : responseCommunities.get(responseCommunities.size() - 1).getId();
        Boolean hasNext = hasNext(lastIndex, EMPLOYMENT);

        return CursorResult.from(responseCommunities, hasNext, lastIndex);
    }

    private List<ResponseCommunity> getResponseEmploymentCommunities(Cursor cursor) {
        if (cursor.getNext() == null) {
            return communityQuery.findResponseOldEmploymentCommunities(cursor.getSize());
        }
        return communityQuery.findResponseOldEmploymentCommunities(cursor.getNext(), cursor.getSize());
    }
}
