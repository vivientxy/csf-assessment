package ibf2023.csf.backend.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import jakarta.json.Json;

// You can add addtional methods and annotations to this controller. 
// You cannot remove any existing annotations or methods from UploadController
@Controller
@RequestMapping(path="/api")
public class UploadController {

	// TODO Task 5.2
	// You may change the method signature by adding additional parameters and annotations.
	// You cannot remove any any existing annotations and parameters from postUpload()
	@PostMapping(path="/image/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> postUpload(@RequestPart MultipartFile picture, @RequestPart String content) {

		System.out.println(">>> picture: " + picture);
		System.out.println(">>> content: " + content);

		String[] contentParts = content.split("\\|");
		String title = contentParts[0];
		String comments = contentParts[1];
		String date = contentParts[2];
		System.out.println(">>> title: " + title);
		System.out.println(">>> comments: " + comments);
		System.out.println(">>> date: " + date);

		return ResponseEntity.ok(
			Json.createObjectBuilder().build().toString()
		);
	}
}
