package com.ashu.aws.s3;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.WritableResource;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;

import lombok.extern.slf4j.Slf4j;

//@Component
@Slf4j
public class S3CLR implements CommandLineRunner {

	@Autowired
	private ResourceLoader resourceLoader;

	@Autowired
	private ResourcePatternResolver resourcePatternResolver;

	@Autowired
	private AmazonS3 s3Client;

	public void downloadS3Object(String s3Url) throws IOException {
		Resource resource = resourceLoader.getResource(s3Url);
		File downloadedS3Object = new File(resource.getFilename());
		log.info("================== file download successful =======================");
		try (InputStream inputStream = resource.getInputStream()) {
			Files.copy(inputStream, downloadedS3Object.toPath(), StandardCopyOption.REPLACE_EXISTING);
		}
		Files.lines(downloadedS3Object.toPath()).forEach(log::info);
		log.info("================++ file read successful ++====================");
	}

	public void uploadFileToS3(File file, String s3Url) throws IOException {
		WritableResource resource = (WritableResource) resourceLoader.getResource(s3Url);
		try (OutputStream outputStream = resource.getOutputStream()) {
			Files.copy(file.toPath(), outputStream);
		}
	}

	public void downloadMultipleS3Objects(String s3Url) throws IOException {
		// s3Url = "s3://my-s3-bucket/**/a*.txt ";
		Resource[] allFileMatchingPattern = resourcePatternResolver.getResources(s3Url);
	}

	public void withTransferManager(String bucket, File file) {
		TransferManager transferManager = TransferManagerBuilder.standard().withS3Client(s3Client).build();
		log.info("================== file upload with Transfermanager started =======================");
		transferManager.upload(bucket, file.getName(), file);
		log.info("================== file upload with Transfermanager successful =======================");
	}

	@Override
	public void run(String... args) throws Exception {
		withTransferManager("elasticbeanstalk-ap-south-1-089591637283", new File("C:\\temp\\s3-upload-file.txt"));
		Thread.sleep(2000);
		downloadS3Object("s3://elasticbeanstalk-ap-south-1-089591637283/s3-upload-file.txt");
	}

}
