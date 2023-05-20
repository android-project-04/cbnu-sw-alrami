package cbnu.io.cbnuswalrami.business.web.s3.application.service;

import cbnu.io.cbnuswalrami.business.web.s3.application.S3Command;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3Service implements S3Command {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

//    @Value("${cloud.aws.s3.dir}")
//    private String dir;
    private final AmazonS3 amazonS3;


    @Override
    public String uploadFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();

        if (file == null) {
            log.info("file이 null입니다.");
            throw new IllegalArgumentException("file의 값이 null입니다.");
        }

        // 파일 형식 구하기
        String ext = fileName.split("\\.")[1];
        String contentType = "";

        //content type을 지정해서 올려주지 않으면 자동으로 "application/octet-stream"으로 고정이 되서 링크 클릭시 웹에서 열리는게 아니라 자동 다운이 시작됨.
        contentType = getExt(ext);

        String extendedFilename = UUID.randomUUID().toString();
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(contentType);

            amazonS3.putObject(new PutObjectRequest(bucket, extendedFilename, file.getInputStream(), metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (AmazonServiceException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("AWS 예외입니다.");
        } catch (SdkClientException e) {
            throw new IllegalArgumentException("S3 Sdk 예외입니다.");
        } catch (IOException e) {
            log.error("[AWS IO EX] : {}" ,e.getMessage());
            throw new RuntimeException("AWS IO 예외입니다.");
        }

        //object 정보 가져오기
        ListObjectsV2Result listObjectsV2Result = amazonS3.listObjectsV2(bucket);
//        List<S3ObjectSummary> objectSummaries = listObjectsV2Result.getObjectSummaries();

        String url = amazonS3.getUrl(bucket, extendedFilename).toString();

        return url;
    }

    private String getExt(String ext) {
        String contentType = "";
        switch (ext) {
            case "jpeg":
                contentType = "image/jpeg";
                break;
            case "png":
                contentType = "image/png";
                break;
            case "txt":
                contentType = "text/plain";
                break;
            case "csv":
                contentType = "text/csv";
                break;
        }
        return contentType;
    }
}
