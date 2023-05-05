package cbnu.io.cbnuswalrami.common.configuration.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Configuration
@Profile("test")
public class TestRedisServerConfig {

    private final RedisServer redisServer;

    public TestRedisServerConfig(@Value("${spring.redis.port}") int redisPort ) {
        this.redisServer = RedisServer.builder()
                .port(redisPort)
                .setting("maxmemory 10M")
                .build();
    }

    @PostConstruct
    public void startRedis() {
        this.redisServer.start();
    }

    @PreDestroy
    public void stopRedis() {
        this.redisServer.stop();
    }
}
