package cbnu.io.cbnuswalrami.business.web.communitybookmark.application.service;

import cbnu.io.cbnuswalrami.business.core.domon.communitybookmark.entity.CommunityBookmark;
import cbnu.io.cbnuswalrami.business.core.domon.communitybookmark.infrastructure.command.CommunityBookmarkJpaRepository;
import cbnu.io.cbnuswalrami.business.core.domon.communitybookmark.infrastructure.query.CommunityBookmarkQuery;
import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.web.common.RequestId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteCommunityBookmarkService {

    private final CommunityBookmarkQuery communityBookmarkQuery;

    private final CommunityBookmarkJpaRepository communityBookmarkJpaRepository;

    @Transactional
    public String deleteCommunityBookmarkById(
            RequestId deleteId,
            Member member
    ) {
        CommunityBookmark communityBookmark = communityBookmarkQuery.findCommunityBookmarkByBookmarkId(deleteId.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 북마크가 존재하지 않습니다."));

        validateMember(member, communityBookmark);

        communityBookmarkJpaRepository.delete(communityBookmark);
        return deleteId.getId() + "번 북마크가 잘 삭제되었습니다.";
    }

    private static void validateMember(Member member, CommunityBookmark communityBookmark) {
        if (!communityBookmark.getMember().getId().equals(member.getId())) {
            throw new IllegalArgumentException("해당 유저의 북마크가 아닙니다.");
        }
    }
}
