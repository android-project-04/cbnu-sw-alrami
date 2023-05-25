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
public class CommunityCursorPagingService {

    private final CommunityQuery communityQuery;

    @Transactional(readOnly = true)
    public CursorResult findByCursor(Cursor cursor) {
        List<ResponseCommunity> responseCommunities = getResponseCommunities(cursor);
        Long lastIndex = responseCommunities.isEmpty() ? 0 : responseCommunities.get(responseCommunities.size() - 1).getId();
        Boolean hasNext  = hasNext(lastIndex, COMMUNITY);
        return CursorResult.from(responseCommunities, hasNext, lastIndex);
    }

    private Boolean hasNext(Long lastIndex, CommunityType communityType) {
        return communityQuery.existByCommunityId(lastIndex, communityType);
    }

    private List<ResponseCommunity> getResponseCommunities(Cursor cursor) {
        if (cursor.getNext() == null) {
            return communityQuery.findResponseCommunities(cursor.getSize());
        }
        return communityQuery.findResponseCommunities(cursor.getNext(), cursor.getSize());
    }

    @Transactional(readOnly = true)
    public CursorResult findEmploymentCommunity(Cursor cursor) {
        List<ResponseCommunity> responseCommunities = getResponseCommunityByEmploymentCommunityCursor(cursor);
        Long lastIndex = responseCommunities.get(responseCommunities.size() - 1).getId();
        Boolean hasNext = hasNext(lastIndex, EMPLOYMENT);
        return CursorResult.from(responseCommunities, hasNext, lastIndex);
    }

    private List<ResponseCommunity> getResponseCommunityByEmploymentCommunityCursor(Cursor cursor) {
        if (cursor.getNext() == null) {
            return communityQuery.findResponseEmploymentCommunities(cursor.getSize());
        }
        return communityQuery.findResponseEmploymentCommunities(cursor.getNext(), cursor.getSize());
    }
}
