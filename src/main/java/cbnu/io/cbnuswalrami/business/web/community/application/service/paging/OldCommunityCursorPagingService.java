package cbnu.io.cbnuswalrami.business.web.community.application.service.paging;

import cbnu.io.cbnuswalrami.business.core.domon.post.community.infrastructure.query.CommunityQuery;
import cbnu.io.cbnuswalrami.business.web.common.Cursor;
import cbnu.io.cbnuswalrami.business.web.common.CursorResult;
import cbnu.io.cbnuswalrami.business.web.community.presentation.response.ResponseCommunity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OldCommunityCursorPagingService {

    private final CommunityQuery communityQuery;
    public CursorResult findByCursor(Cursor cursor) {
        List<ResponseCommunity> responseCommunities = getResponseCommunities(cursor);
        Long lastIndex = responseCommunities.isEmpty() ? 0 : responseCommunities.get(responseCommunities.size() - 1).getId();
        Boolean hasNext = hasNext(lastIndex);

        return CursorResult.from(responseCommunities, hasNext, lastIndex);
    }

    private Boolean hasNext(Long lastIndex) {
        return communityQuery.existOldCommunityId(lastIndex);
    }

    private List<ResponseCommunity> getResponseCommunities(Cursor cursor) {
        if (cursor.getNext() == null) {
            return communityQuery.findResponseOldCommunities(cursor.getSize());
        }
        return communityQuery.findResponseOldCommunities(cursor.getNext(), cursor.getSize());
    }
}
