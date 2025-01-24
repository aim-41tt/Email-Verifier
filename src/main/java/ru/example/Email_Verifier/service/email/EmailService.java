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

	/**
	 * @param emailSender
	 * @param templateService
	 */
	public EmailService(JavaMailSender emailSender, TemplateService templateService) {
		this.emailSender = emailSender;
		this.templateService = templateService;
	}

	@Async
	public void sendPasswordResetEmail(String email, String confirmationLink) {
		String subject = "PASSWORD RESET";
		String body = templateService.generatePasswordResetTemplate(confirmationLink);
		sendEmail(email, subject, body);
	}

	@Async
	public void sendVerificationEmail(String email, String confirmationLink) {
		String subject = "Email Verification";
		String body = templateService.generateVerificationTemplate(confirmationLink);
		sendEmail(email, subject, body);
	}

	@Async
	public void sendActionConfirmationEmail(String email, String confirmationLink) {
		String subject = "Action Confirmation";
		String body = templateService.generateActionConfirmationTemplate(confirmationLink);
		sendEmail(email, subject, body);
	}

	@Async
	public void sendNotification(String email) {
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
			helper.setText(body, true); // это HTML-содержимое

			emailSender.send(message);
		} catch (MailException e) {
			handleError(e, email);
		} catch (Exception e) {
			handleError(e, email);
		}
	}

	private void handleError(Exception e, String email) {
		StringBuilder sb = new StringBuilder().append("Error send email: ").append(email);

		logger.error(sb.toString());

		e.getStackTrace();
	}

}
