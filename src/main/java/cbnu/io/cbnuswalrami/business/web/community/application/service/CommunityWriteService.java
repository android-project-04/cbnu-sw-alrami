package cbnu.io.cbnuswalrami.business.web.community.application.service;

import cbnu.io.cbnuswalrami.business.core.domon.post.community.entity.Community;
import cbnu.io.cbnuswalrami.business.core.domon.post.community_count.entity.CommunityCount;
import cbnu.io.cbnuswalrami.business.core.domon.post.community_count.infrastructure.CommunityCountJpaRepository;
import cbnu.io.cbnuswalrami.business.core.domon.post.values.CommunityType;
import cbnu.io.cbnuswalrami.business.core.domon.post.values.Description;
import cbnu.io.cbnuswalrami.business.core.domon.post.values.Title;
import cbnu.io.cbnuswalrami.business.core.domon.post.community.infrastructure.command.CommunityJpaRepository;
import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.web.community.presentation.request.RequestCommunity;
import cbnu.io.cbnuswalrami.business.web.community.presentation.response.ResponseCommunity;
import cbnu.io.cbnuswalrami.business.web.s3.application.S3Command;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CommunityWriteService {

    private final S3Command s3Command;
    private final CommunityJpaRepository communityJpaRepository;
    private final CommunityCountJpaRepository communityCountJpaRepository;

    @Transactional
    public ResponseCommunity writeCommunity(
            RequestCommunity requestCommunity,
            MultipartFile file,
            Member member,
            CommunityType communityType
    ) {
        String url = file == null ? null : s3Command.uploadFile(file);
        Community community = new Community(
                Title.from(requestCommunity.getTitle()),
                Description.from(requestCommunity.getDescription()),
                url,
                member,
                communityType
        );

        return getResponseCommunity(community);
    }

    private ResponseCommunity getResponseCommunity(Community community) {
        Community savedCommunity = communityJpaRepository.save(community);
        CommunityCount communityCount = communityCountJpaRepository.save(new CommunityCount(savedCommunity));
        ResponseCommunity responseCommunity = new ResponseCommunity(
                savedCommunity.getId(),
                community.getTitle(),
                savedCommunity.getDescription(),
                savedCommunity.getUrl(),
                savedCommunity.getCreatedAt(),
                communityCount.getCount()
        );
        return responseCommunity;
    }

}
