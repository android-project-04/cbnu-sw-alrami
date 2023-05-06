package cbnu.io.cbnuswalrami.business.web.redis.service;

import cbnu.io.cbnuswalrami.business.web.redis.RedisSessionService;
import cbnu.io.cbnuswalrami.common.exception.common.CbnuException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

import static cbnu.io.cbnuswalrami.business.core.error.CommonTypeException.REDIS_CONNECTION_FAILURE_EXCEPTION;
import static cbnu.io.cbnuswalrami.business.core.error.CommonTypeException.REDIS_WRONG_TYPE_DATASTRUCTURE_EXCEPTION;

@Slf4j
@Service
public class SessionStoreService implements RedisSessionService {

    private final StringRedisTemplate stringRedisTemplate;
    private final RedisTemplate<String, Long> loginCountRedisTemplate;


    public SessionStoreService(
            StringRedisTemplate stringRedisTemplate,
            RedisTemplate<String, Long> loginCountRedisTemplate
    ) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.loginCountRedisTemplate = loginCountRedisTemplate;
    }


    @Override
    @Transactional
    public String save(Long memberId) {
        log.info("member session save start");
        try {
            String session = UUID.randomUUID().toString();
            stringRedisTemplate.execute(new SessionCallback<Object>() {
                @Override
                public <K, V> Object execute(@NonNull RedisOperations<K, V> operations) throws DataAccessException {
                    operations.multi();

                    stringRedisTemplate.opsForValue().set(session, String.valueOf(memberId), Duration.ofDays(14));

                    @SuppressWarnings("unchecked")
                    List<Object> transactionCommit = operations.exec();
                    return transactionCommit;
                }
            });
            return session;
        } catch (RedisSystemException e) {
            throw CbnuException.of(REDIS_WRONG_TYPE_DATASTRUCTURE_EXCEPTION);
        } catch (DataAccessException e) {
            throw CbnuException.of(REDIS_CONNECTION_FAILURE_EXCEPTION);
        }
    }


    @Override
    public Long getMemberId(String sessionId) {
        try {
            String memberId = stringRedisTemplate.opsForValue().get(sessionId);
            if (memberId != null) {
                return Long.parseLong(memberId);
            }
            return null;
        } catch (RedisSystemException e) {
            throw CbnuException.of(REDIS_WRONG_TYPE_DATASTRUCTURE_EXCEPTION);
        }
    }

    @Override
    public Long getLoginTryCount(Long memberId) {
        try {
            Long loginTryCount = loginCountRedisTemplate.opsForValue().increment(getLoginCountKey(memberId));
            loginCountRedisTemplate.expire(getLoginCountKey(memberId), Duration.ofMinutes(30));
            return loginTryCount;
        } catch (DataAccessException e) {
            throw CbnuException.of(REDIS_CONNECTION_FAILURE_EXCEPTION);
        }
    }

    @Override
    @Transactional
    public void deleteSession(Long memberId, String session) {
        try {
            stringRedisTemplate.execute(new SessionCallback<Object>() {
                @Override
                public <K, V> Object execute(RedisOperations<K, V> operations) throws DataAccessException {
                    operations.multi();

                    stringRedisTemplate.delete(session);
                    @SuppressWarnings("unchecked")
                    List<Object> transactionCommit = operations.exec();
                    return transactionCommit;
                }
            });
        } catch (RedisSystemException e) {
            throw CbnuException.of(REDIS_WRONG_TYPE_DATASTRUCTURE_EXCEPTION);
        }

    }


    private String getSessionsKey(Long memberId) {
        return String.format("member:id:%s:sessions", memberId);
    }

    private String getLoginCountKey(Long memberId) {
        return String.format("member:id:%s:login:count", memberId);
    }
}
