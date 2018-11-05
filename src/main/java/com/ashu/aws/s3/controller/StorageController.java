package com.ashu.aws.s3.controller;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "/storage")
@Slf4j
public class StorageController {

	@Autowired
	private ResourceLoader resourceLoader;

	@GetMapping
	public String resourceLoadingMethod() throws IOException {
		log.info("IN RESOURCE LOADER");
		Resource resource = resourceLoader
				.getResource("s3://elasticbeanstalk-ap-south-1-089591637283/s3-upload-file.txt");
		InputStream inputStream = resource.getInputStream();
		return inputStream.toString();
	}
}
