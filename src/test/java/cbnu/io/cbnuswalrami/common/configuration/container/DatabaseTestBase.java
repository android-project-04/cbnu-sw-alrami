package cbnu.io.cbnuswalrami.common.configuration.container;

import cbnu.io.cbnuswalrami.business.core.domon.member.entity.Member;
import cbnu.io.cbnuswalrami.common.configuration.annotation.IntegrationTest;
import cbnu.io.cbnuswalrami.common.configuration.rdb.DatabaseCleanup;
import cbnu.io.cbnuswalrami.common.configuration.redis.RedisInitialization;
import cbnu.io.cbnuswalrami.common.configuration.security.TokenProvider;
import cbnu.io.cbnuswalrami.test.helper.fixture.member.MemberFixture;
import cbnu.io.cbnuswalrami.test.helper.fixture.member.SignupFixture;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@Slf4j
@ContextConfiguration(initializers = DatabaseTestBase.DataSourceInitializer.class)
@IntegrationTest
public abstract class DatabaseTestBase {

    public static class DataSourceInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Container
        private static final MySQLContainer database = new MySQLContainer("mysql:latest");

        private static final String REDIS_DOCKER_IMAGE = "redis:5.0.3-alpine";

        GenericContainer<?> REDIS_CONTAINER =
                new GenericContainer<>(DockerImageName.parse(REDIS_DOCKER_IMAGE))
                        .withExposedPorts(6379)
                        .withReuse(true);

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            database.start();
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    applicationContext,
                    "spring.datasource.url=" + database.getJdbcUrl(),
                    "spring.datasource.username=" + database.getUsername(),
                    "spring.datasource.password=" + database.getPassword()
            );
            database.withInitScript("init.sql");

            REDIS_CONTAINER.start();
            System.setProperty("spring.redis.host", REDIS_CONTAINER.getHost());
            System.setProperty("spring.redis.port", REDIS_CONTAINER.getMappedPort(6379).toString());
        }
    }

    @Autowired
    private DatabaseCleanup databaseCleanup;

    @Autowired
    private RedisInitialization redisInitialization;

    @Autowired
    private SignupFixture signupFixture;

    @Autowired
    private TokenProvider tokenProvider;

    @BeforeEach
    void cleanDB() {
        log.info("========db clean start========");
        databaseCleanup.execute();
        redisInitialization.init();

        log.info("========Security Context Setting ========");
        MemberFixture.createMember();
        Member member = signupFixture.signupMember(MemberFixture.createMember());
        Authentication authentication = tokenProvider.authenticate(member.getId());
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);
    }
}
