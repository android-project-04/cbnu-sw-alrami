package cbnu.io.cbnuswalrami.business.web.communitybookmark.application.service;

import cbnu.io.cbnuswalrami.business.core.domon.common.deleted.Deleted;
import cbnu.io.cbnuswalrami.business.core.domon.communitybookmark.entity.CommunityBookmark;
import cbnu.io.cbnuswalrami.business.core.domon.communitybookmark.infrastructure.command.CommunityBookmarkJpaRepository;
import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.core.domon.post.community.entity.Community;
import cbnu.io.cbnuswalrami.business.core.domon.post.community.infrastructure.command.CommunityJpaRepository;
import cbnu.io.cbnuswalrami.business.core.domon.post.community_count.entity.CommunityCount;
import cbnu.io.cbnuswalrami.business.core.domon.post.community_count.infrastructure.CommunityCountJpaRepository;
import cbnu.io.cbnuswalrami.business.web.common.RequestId;
import cbnu.io.cbnuswalrami.business.web.community.presentation.response.ResponseCommunity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SaveCommunityBookmarkService {

    private final CommunityJpaRepository communityJpaRepository;
    private final CommunityBookmarkJpaRepository communityBookmarkJpaRepository;

    private final CommunityCountJpaRepository communityCountJpaRepository;

    @Transactional
    public ResponseCommunity saveCommunityBookmark(
            Member member,
            RequestId communityId
    ) {
        Community community = communityJpaRepository.findById(communityId.getId())
                .filter(x -> x.getMember().getId().equals(member.getId()))
                .filter(x -> x.getIsDeleted().equals(Deleted.FALSE))
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 커뮤니티 게시글이 존재하지 않거나 게시글 작성자가 아닙니다."));

        CommunityCount communityCount = communityCountJpaRepository.findByCommunity(community)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물의 조회수가 없습니다."));

        CommunityBookmark communityBookmark = new CommunityBookmark(member, community);
        communityBookmarkJpaRepository.save(communityBookmark);

        ResponseCommunity responseCommunity = new ResponseCommunity(
                community.getId(),
                community.getTitle(),
                community.getDescription(),
                community.getUrl(),
                community.getCreatedAt(),
                communityCount.getCount()
        );
        return responseCommunity;
    }
}
