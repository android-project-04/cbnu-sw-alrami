package cbnu.io.cbnuswalrami.business.web.s3.application;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface S3Command {

    public String uploadFile(MultipartFile file);
}
