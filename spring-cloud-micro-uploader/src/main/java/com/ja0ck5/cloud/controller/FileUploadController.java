package com.ja0ck5.cloud.controller;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * Created by Jack on 2017/10/18.
 */
@RestController
@RequestMapping("/upload")
public class FileUploadController {

	@PostMapping("/file")
	public String handleFileUploade(@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request) {
		try {
			byte[] bytes = file.getBytes();
			File fileSaved = new File(file.getOriginalFilename());
			FileCopyUtils.copy(bytes, fileSaved);
			return fileSaved.getAbsolutePath();
		} catch (IOException e) {
			// ignore
		}
		return null;
	}

}
