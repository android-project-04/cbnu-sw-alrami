package cbnu.io.cbnuswalrami.business.web.redis;

public interface RedisSessionService {

    String save(Long memberId);

    Long getMemberId(String sessionId);

    Long getLoginTryCount(Long memberId);

    void deleteSession(Long memberId, String session);

}
