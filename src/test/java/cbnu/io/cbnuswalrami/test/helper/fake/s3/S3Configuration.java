package cbnu.io.cbnuswalrami.test.helper.fake.s3;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class S3Configuration {

    @Primary
    @Bean
    public S3FakeService s3FakeService() {
        return new S3FakeService();
    }
}
