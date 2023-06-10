package cbnu.io.cbnuswalrami.business.web.community.application.service;

import cbnu.io.cbnuswalrami.business.core.domon.post.community.entity.Community;
import cbnu.io.cbnuswalrami.business.core.domon.post.community.infrastructure.command.CommunityJpaRepository;
import cbnu.io.cbnuswalrami.business.web.common.RequestId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteCommunityByAdminService {

    private final CommunityJpaRepository communityJpaRepository;

    @Transactional
    public String deleteCommunityById(RequestId deleteId) {
        Community community = communityJpaRepository.findById(deleteId.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 커뮤니티가 존재하지 않습니다."));
        String message = community.getTitle() + "을 삭제하였습니다.";
        community.changeToDeleteTrue();
        return message;
    }
}
