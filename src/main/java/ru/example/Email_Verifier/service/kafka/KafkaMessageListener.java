package ru.example.Email_Verifier.service.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import ru.example.Email_Verifier.DTO.request.EmailMessageDTO;
import ru.example.Email_Verifier.service.email.EmailService;

@Service
public class KafkaMessageListener {

	private static final Logger logger = LoggerFactory.getLogger(KafkaMessageListener.class);
	private final EmailService emailService;

	public KafkaMessageListener(EmailService emailService) {
		this.emailService = emailService;
	}

	@KafkaListener(topics = "${kafka.topic.name}")
	public void onMessage(@Payload EmailMessageDTO message, ConsumerRecord<String, String> record,Acknowledgment acknowledgmen) {
		logger.info("Received message from topic: {}, partition: {}, offset: {}", record.topic(), record.partition(),
				record.offset());

		try {
			switch (message.getMessageType()) {
			case PASSWORD_RESET:
				emailService.sendPasswordResetEmail(message.getEmail(), message.getLink());
				break;
			case VERIFICATION:
				emailService.sendVerificationEmail(message.getEmail(), message.getLink());
				break;
			case WELCOME:
				emailService.sendNotification(message.getEmail());
				break;
			default:
				logger.warn("Unknown message type: {}", message.getMessageType());
				break;
			}
			acknowledgmen.acknowledge();
		} catch (Exception e) {
			handleError(e, record);
		}
		
	}

	private void handleError(Exception e, ConsumerRecord<String, String> record) {
		logger.error("Error processing record from topic: {}, partition: {}, offset: {}. Details: {}", record.topic(),
				record.partition(), record.offset(), e.getMessage(), e);
	}
}
