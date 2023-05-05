package cbnu.io.cbnuswalrami.test.helper.fake.s3;

import cbnu.io.cbnuswalrami.business.web.s3.application.S3Command;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

public class S3FakeService implements S3Command {
    @Override
    public String uploadFile(MultipartFile file) {
        return "aws.ac.kr";
    }
}
