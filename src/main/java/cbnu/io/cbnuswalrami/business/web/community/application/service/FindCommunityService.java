package cbnu.io.cbnuswalrami.business.web.community.application.service;

import cbnu.io.cbnuswalrami.business.core.domon.post.community.infrastructure.query.CommunityQuery;
import cbnu.io.cbnuswalrami.business.web.common.RequestId;
import cbnu.io.cbnuswalrami.business.web.community.presentation.response.ResponseCommunity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static cbnu.io.cbnuswalrami.business.web.util.RedisKey.*;


@Service
@RequiredArgsConstructor
public class FindCommunityService {

    private final RedisTemplate<String ,String> redisTemplate;

    private final CommunityQuery communityQuery;

    @Transactional(readOnly = true)
    public ResponseCommunity findCommunityById(RequestId communityId, RequestId memberId) {
        ResponseCommunity responseCommunity = communityQuery.findCommunityById(communityId.getId());
        validate(responseCommunity);

        // redis SISMEMBER로 조회 ture 이면 조회수 증가하지 않고 false 이면 조회수 증가하며 Sets에 member:id 추가
        countViews(responseCommunity.getId(), memberId.getId());
        return responseCommunity;
    }

    private static void validate(ResponseCommunity responseCommunity) {
        if (responseCommunity == null) {
            throw new IllegalArgumentException("해당 id값의 커뮨니티 게시글이 존재하지 않습니다.");
        }
    }

    private void countViews(Long communityId, Long memberId) {
        SetOperations<String, String> setOperations = redisTemplate.opsForSet();
        Boolean isExist = setOperations.isMember(COMMUNITY_KEY + communityId, MEMBER_KEY + memberId);
        if (isExist == false) {
            setOperations.add(COMMUNITY_KEY + communityId, MEMBER_KEY + memberId);
            redisTemplate.opsForValue().increment(COMMUNITY_COUNT_KEY + communityId);
        }
    }
}
