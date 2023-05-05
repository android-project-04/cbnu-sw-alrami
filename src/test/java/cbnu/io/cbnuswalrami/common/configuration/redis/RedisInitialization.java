package cbnu.io.cbnuswalrami.common.configuration.redis;

import groovy.util.logging.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
@Import({TestRedisConfiguration.class, TestRedisServerConfig.class})
public class RedisInitialization {

    @Autowired
    @Qualifier("redisStringTemplate")
    private RedisTemplate<String, String> redisStringTemplate;

    public void init() {
        System.out.println("[====================redis init====================]");
        initRedisStringTemplate();
    }

    private void initRedisStringTemplate() {
        Set<String> redisKeys = redisStringTemplate.keys("users:id*");

        if (redisKeys == null) {
            redisKeys = new HashSet<>();
        }

        for (String key : redisKeys) {
            redisStringTemplate.delete(key);
        }
    }
}
