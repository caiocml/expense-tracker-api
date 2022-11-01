package br.com.caiocesar.expense.tracker.api.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileSaverService {

    public static final String BUCKET_NAME = "caiocesar-bucket-teste";
    private static final String AWS_STANDAR_URL = "https://s3.amazonaws.com";

    @Autowired
    private AmazonS3 amazonS3;

    public String save(MultipartFile multipartFile) throws IOException {

        String fileName = multipartFile.getOriginalFilename();
        PutObjectResult putObjectResult = amazonS3.putObject(new PutObjectRequest(BUCKET_NAME,
                fileName,
                multipartFile.getInputStream(), null)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        return String.format("%s/%s/%s", AWS_STANDAR_URL, BUCKET_NAME, fileName);
    }

}
