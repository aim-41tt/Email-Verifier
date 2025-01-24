package ru.example.Email_Verifier.service.template;

import org.springframework.stereotype.Service;

@Service
public class TemplateService {

    public String generateVerificationTemplate(String confirmationLink) {
        return "<html>" +
                "<body>" +
                "<h1>Email Verification</h1>" +
                "<p>Please click the link below to verify your email:</p>" +
                "<a href='" + confirmationLink + "'>Verify Email</a>" +
                "</body>" +
                "</html>";
    }

    public String generateActionConfirmationTemplate(String confirmationLink) {
        return "<html>" +
                "<body>" +
                "<h1>Action Confirmation</h1>" +
                "<p>Please click the link below to confirm the action:</p>" +
                "<a href='" + confirmationLink + "'>Confirm Action</a>" +
                "</body>" +
                "</html>";
    }

    public String generateNotificationTemplate() {
        return "<html>" +
                "<body>" +
                "<h1>Notification</h1>" +
                "<p>This is a notification email.</p>" +
                "</body>" +
                "</html>";
    }
    public String generatePasswordResetTemplate(String confirmationLink) {
    	return "<html>" +
                "<body>" +
                "<h1>Action Confirmation</h1>" +
                "<p>Please click the link for reset password:</p>" +
                "<a href='" + confirmationLink + "'>Confirm Action</a>" +
                "</body>" +
                "</html>";
    }
}
