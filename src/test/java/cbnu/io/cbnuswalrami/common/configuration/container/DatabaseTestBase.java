package cbnu.io.cbnuswalrami.common.configuration.container;

import cbnu.io.cbnuswalrami.common.configuration.annotation.IntegrationTest;
import cbnu.io.cbnuswalrami.common.configuration.rdb.DatabaseCleanup;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@IntegrationTest
public abstract class DatabaseTestBase {

    public static class DataSourceInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Container
        private static final MySQLContainer database = new MySQLContainer("mysql:latest");

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            database.start();
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    applicationContext,
                    "spring.datasource.url=" + database.getJdbcUrl(),
                    "spring.datasource.username=" + database.getUsername(),
                    "spring.datasource.password=" + database.getPassword()
            );
            database.withInitScript("/db/init.sql");
        }
    }

    @Autowired
    private DatabaseCleanup databaseCleanup;

    @BeforeEach
    void cleanDB() {
        databaseCleanup.execute();
    }

}
