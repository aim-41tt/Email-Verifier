package ru.example.Email_Verifier.service.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import ru.example.Email_Verifier.service.template.TemplateService;

@Service
public class EmailService {

	private final JavaMailSender emailSender;
	private final TemplateService templateService;
	private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

	public EmailService(JavaMailSender emailSender, TemplateService templateService) {
		this.emailSender = emailSender;
		this.templateService = templateService;
	}

	@Async
	public void sendPasswordResetEmail(String email, String confirmationLink) {
		logStart("Password Reset Email", email);
		String subject = "PASSWORD RESET";
		String body = templateService.generatePasswordResetTemplate(confirmationLink);
		sendEmail(email, subject, body);
	}

	@Async
	public void sendVerificationEmail(String email, String confirmationLink) {
		logStart("Verification Email", email);
		String subject = "Email Verification";
		String body = templateService.generateVerificationTemplate(confirmationLink);
		sendEmail(email, subject, body);
	}

	@Async
	public void sendActionConfirmationEmail(String email, String confirmationLink) {
		logStart("Action Confirmation Email", email);
		String subject = "Action Confirmation";
		String body = templateService.generateActionConfirmationTemplate(confirmationLink);
		sendEmail(email, subject, body);
	}

	@Async
	public void sendNotification(String email) {
		logStart("Notification Email", email);
		String subject = "Notification";
		String body = templateService.generateNotificationTemplate();
		sendEmail(email, subject, body);
	}

	private void sendEmail(String email, String subject, String body) {
		try {
			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo(email);
			helper.setSubject(subject);
			helper.setText(body, true); // HTML формат

			emailSender.send(message);

			logger.info("Email successfully sent to {} with subject: {}", email, subject);
		} catch (MailException e) {
			handleMailException(e, email, subject);
		} catch (Exception e) {
			handleGeneralException(e, email, subject);
		}
	}

	private void handleMailException(MailException e, String email, String subject) {
		logger.error("MailException occurred while sending email to {} with subject: {}. Details: {}", email, subject,
				e.getMessage(), e);
	}

	private void handleGeneralException(Exception e, String email, String subject) {
		logger.error("Unexpected exception occurred while sending email to {} with subject: {}. Details: {}", email,
				subject, e.getMessage(), e);
	}

	private void logStart(String emailType, String email) {
		logger.info("Starting to send {} to {}", emailType, email);
	}
}
