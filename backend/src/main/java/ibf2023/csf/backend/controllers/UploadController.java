package ibf2023.csf.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import ibf2023.csf.backend.exceptions.SizeException;
import ibf2023.csf.backend.models.UploadPicture;
import ibf2023.csf.backend.services.PictureService;
import jakarta.json.Json;
import jakarta.json.JsonObject;

// You can add addtional methods and annotations to this controller. 
// You cannot remove any existing annotations or methods from UploadController
@Controller
@RequestMapping(path="/api")
public class UploadController {

	@Autowired
	PictureService svc;

	// Task 5.2
	// You may change the method signature by adding additional parameters and annotations.
	// You cannot remove any any existing annotations and parameters from postUpload()
	@PostMapping(path="/image/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> postUpload(@RequestPart MultipartFile picture, @RequestPart String content) {

		try {
			UploadPicture pic = svc.save(picture, content);
			JsonObject json = Json.createObjectBuilder().add("id", pic.getId().toString()).build();
			return new ResponseEntity<>(json.toString(), HttpStatus.OK);
		} catch (SizeException e) {
			JsonObject json = Json.createObjectBuilder().add("message", e.getMessage()).build();
			return new ResponseEntity<>(json.toString(), HttpStatus.PAYLOAD_TOO_LARGE);
		} catch (Exception e) {
			e.printStackTrace();
			JsonObject json = Json.createObjectBuilder().add("message", e.getMessage()).build();
			return new ResponseEntity<>(json.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
