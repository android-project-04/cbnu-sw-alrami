package cbnu.io.cbnuswalrami.common.configuration.redis;

import groovy.util.logging.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
@Import({TestRedisConfiguration.class})
@ActiveProfiles("test")
public class RedisInitialization {

    @Autowired
    @Qualifier("testRedisStringTemplate")
    private RedisTemplate<String, String> redisStringTemplate;

    @Autowired
    @Qualifier("testSessionRedisTemplate")
    private StringRedisTemplate stringRedisTemplate;

    public void init() {
        initRedisStringTemplate();
    }

    private void initRedisStringTemplate() {
        Set<String> redisKeys = redisStringTemplate.keys("*");
        Set<String> stringRedisKeys = stringRedisTemplate.keys("*");

        if (redisKeys == null) {
            redisKeys = new HashSet<>();
        }

        if (stringRedisKeys == null) {
            stringRedisKeys = new HashSet<>();
        }

        for (String key : redisKeys) {
            redisStringTemplate.delete(key);
        }

        for (String key : stringRedisKeys) {
            stringRedisTemplate.delete(key);
        }
    }
}
