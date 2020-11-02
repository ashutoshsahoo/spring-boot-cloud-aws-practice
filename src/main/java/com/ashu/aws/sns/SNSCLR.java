package com.ashu.aws.sns;

import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;
import org.springframework.stereotype.Component;

import com.amazonaws.services.sns.AmazonSNS;

import lombok.extern.slf4j.Slf4j;

//@Component
@Slf4j
public class SNSCLR implements CommandLineRunner {

	private final NotificationMessagingTemplate messagingTemplate;

	public SNSCLR(AmazonSNS amazonSns) {
		super();
		messagingTemplate = new NotificationMessagingTemplate(amazonSns);
	}

	public void send(String subject, String message) {
		messagingTemplate.sendNotification("my-topic", message, subject);
	}

	@Override
	public void run(String... args) throws Exception {
		send("Test subject", "Hi can u tell me something ?");
		log.info("======================== Message sent successfully =============================");
	}

}
