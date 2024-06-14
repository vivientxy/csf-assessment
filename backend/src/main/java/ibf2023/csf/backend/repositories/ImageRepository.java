package ibf2023.csf.backend.repositories;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Repository
public class ImageRepository {

	@Autowired
    AmazonS3 s3;

	public static final String S3_BUCKET = "tfip-csf-viv";

	// Task 4.1
	// You may change the method signature by adding parameters and/or the return type
	// You may throw any exception 
	public String save(MultipartFile uploadFile) throws IOException {
		Map<String, String> userData = new HashMap<>();
        // userData.put("name", "viv");
        userData.put("filename", uploadFile.getOriginalFilename());

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(uploadFile.getContentType());
        metadata.setContentLength(uploadFile.getSize());
        metadata.setUserMetadata(userData);

        String id = UUID.randomUUID().toString().substring(0, 8);
        PutObjectRequest putRequest = new PutObjectRequest(S3_BUCKET, id, uploadFile.getInputStream(), metadata);
        putRequest.withCannedAcl(CannedAccessControlList.PublicRead);

        s3.putObject(putRequest);

        return s3.getUrl(S3_BUCKET, id).toString();
	}
}

