package ibf2023.csf.backend.services;

import java.util.Date;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ibf2023.csf.backend.exceptions.SizeException;
import ibf2023.csf.backend.models.UploadPicture;
import ibf2023.csf.backend.repositories.ImageRepository;
import ibf2023.csf.backend.repositories.PictureRepository;

@Service
public class PictureService {

	@Autowired
	ImageRepository s3Repo;

	@Autowired
	PictureRepository mongoRepo;

	@Value("${pic.monthly.threshold.in.mb}")
	String sizeThresholdInMB;

	// Task 5.1
	// You may change the method signature by adding parameters and/or the return type
	// You may throw any exception 
	@SuppressWarnings("deprecation")
	public UploadPicture save(MultipartFile picture, String content) throws Exception {
		// check size against Mongo size first
		Long thresholdInBytes = Long.valueOf(sizeThresholdInMB) * 100000;
		Long currentMongoSize = mongoRepo.getCurrentSize();
		Long uploadSize = picture.getSize();
		if ((uploadSize + currentMongoSize) > thresholdInBytes) {
			throw new SizeException("The upload has exceeded your monthly upload quota of " + sizeThresholdInMB + "MB");
		}

		// save to S3
		String url = s3Repo.save(picture);

		// save to Mongo
		String[] contentParts = content.split(Pattern.quote("|"));
		String title = contentParts[0];
		String comments = contentParts[1];
		String date = contentParts[2];

		UploadPicture mongoPic = new UploadPicture();
		mongoPic.setDate(new Date(date));
		mongoPic.setTitle(title);
		mongoPic.setComments(comments);
		mongoPic.setSize(uploadSize);
		mongoPic.setUrl(url);

		return mongoRepo.save(mongoPic);
	}
}
