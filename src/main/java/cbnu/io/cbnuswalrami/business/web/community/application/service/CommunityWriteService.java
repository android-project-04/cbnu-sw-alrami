package cbnu.io.cbnuswalrami.business.web.community.application.service;

import cbnu.io.cbnuswalrami.business.core.domon.community.entity.Community;
import cbnu.io.cbnuswalrami.business.core.domon.community.infrastructure.command.CommunityJpaRepository;
import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.business.core.error.ErrorField;
import cbnu.io.cbnuswalrami.business.web.community.presentation.request.RequestCommunity;
import cbnu.io.cbnuswalrami.business.web.community.presentation.response.ResponseCommunity;
import cbnu.io.cbnuswalrami.business.web.s3.application.S3Command;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CommunityWriteService {

    private final S3Command s3Command;
    private final CommunityJpaRepository communityJpaRepository;

    public ResponseCommunity writeCommunity(RequestCommunity requestCommunity, MultipartFile file, Member member) {
        validate(requestCommunity);
        String url = file == null ? null : s3Command.uploadFile(file);
        Community community = new Community(requestCommunity.getTitle(),
                requestCommunity.getDescription(),
                url,
                member
        );
        return getResponseCommunity(community);
    }

    private ResponseCommunity getResponseCommunity(Community community) {
        Community savedCommunity = communityJpaRepository.save(community);
        ResponseCommunity responseCommunity = new ResponseCommunity(
                savedCommunity.getId(),
                savedCommunity.getTitle(),
                savedCommunity.getDescription(),
                savedCommunity.getUrl()
        );
        return responseCommunity;
    }

    private void validate(RequestCommunity requestCommunity) {
        if (requestCommunity.getTitle() == null || requestCommunity.getTitle().isEmpty()) {
            throw new IllegalArgumentException("제목을 입력해주세요.", ErrorField.of("title", requestCommunity.getTitle()));
        }
        if (requestCommunity.getDescription() == null || requestCommunity.getDescription().isEmpty()) {
            throw new IllegalArgumentException("글을 작성해주세요.", ErrorField.of("description", requestCommunity.getDescription()));
        }
    }
}
