package org.example;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${email.sender}")
    private String senderEmail;

    @Value("${email.company}")
    private String companyEmail;
    @Value("${feedback_url}")
    private String feadbackurl;

    public String sendInitialEmail(UserInput userInput) {
        String feedbackId = UUID.randomUUID().toString();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(senderEmail);
        message.setTo(userInput.getEmail());
        message.setSubject("We Value Your Feedback!");


        String htmlContent = """
             <!DOCTYPE html>
                                <html lang="en">
                                <head>
                                  <meta charset="UTF-8">
                                  <title>Customer Feedback</title>
                                </head>
                                <body style="margin:0;padding:20px;font-family:Arial,sans-serif;background-color:#f5f5f5;line-height:1.6;">
                                  <div style="max-width:600px;margin:0 auto;background:#fff;border-radius:12px;overflow:hidden;border:1px solid #ddd;">
                                   \s
                                    <!-- Header -->
                                    <div style="background-color:#667eea;color:white;padding:30px 20px;text-align:center;">
                                      <h1 style="margin:0;font-size:28px;font-weight:600;">Customer Feedback</h1>
                                      <p style="margin:10px 0 0 0;font-size:16px;opacity:0.9;">We appreciate your business and value your opinion</p>
                                    </div>
                
                                    <!-- Body -->
                                    <div style="padding:40px 30px;">
                                      <p style="font-size:18px;color:#333;margin-bottom:10px;font-weight:500;">Dear Mr/Mrs %s,</p>
                                      <p style="font-size:16px;color:#555;">Thank you for your recent purchase from our store. We hope you're satisfied with your experience.</p>
                
                                      <h3 style="margin-top:30px;margin-bottom:20px;font-size:20px;color:#333;font-weight:600;">How did we do?</h3>
                
                                      <!-- Rating Buttons -->
                                      <a href="%s?rating=5&firstName=%s&lastName=%s&email=%s&feedbackId=%s"
                                         style="display:block;padding:12px 16px;border:2px solid #e1e5e9;border-radius:8px;background:#fff;color:#333;text-decoration:none;margin-bottom:12px;">
                                        <span style="font-size:24px;color:#10b981;">★★★★★</span>
                                        <span style="font-size:16px;font-weight:500;margin-left:15px;">Excellent</span>
                                      </a>
                
                                      <a href="%s?rating=4&firstName=%s&lastName=%s&email=%s&feedbackId=%s"
                                         style="display:block;padding:12px 16px;border:2px solid #e1e5e9;border-radius:8px;background:#fff;color:#333;text-decoration:none;margin-bottom:12px;">
                                        <span style="font-size:24px;color:#10b981;">★★★★☆</span>
                                        <span style="font-size:16px;font-weight:500;margin-left:15px;">Very Good</span>
                                      </a>
                
                                      <a href="%s?rating=3&firstName=%s&lastName=%s&email=%s&feedbackId=%s"
                                         style="display:block;padding:12px 16px;border:2px solid #e1e5e9;border-radius:8px;background:#fff;color:#333;text-decoration:none;margin-bottom:12px;">
                                        <span style="font-size:24px;color:#fbbf24;">★★★☆☆</span>
                                        <span style="font-size:16px;font-weight:500;margin-left:15px;">Good</span>
                                      </a>
                
                                      <a href="%s?rating=2&firstName=%s&lastName=%s&email=%s&feedbackId=%s"
                                         style="display:block;padding:12px 16px;border:2px solid #e1e5e9;border-radius:8px;background:#fff;color:#333;text-decoration:none;margin-bottom:12px;">
                                        <span style="font-size:24px;color:#f97316;">★★☆☆☆</span>
                                        <span style="font-size:16px;font-weight:500;margin-left:15px;">Fair</span>
                                      </a>
                
                                      <a href="%s?rating=1&firstName=%s&lastName=%s&email=%s&feedbackId=%s"
                                         style="display:block;padding:12px 16px;border:2px solid #e1e5e9;border-radius:8px;background:#fff;color:#333;text-decoration:none;margin-bottom:12px;">
                                        <span style="font-size:24px;color:#ef4444;">★☆☆☆☆</span>
                                        <span style="font-size:16px;font-weight:500;margin-left:15px;">Poor</span>
                                      </a>
                
                                   </div>
                
                                    <!-- Footer -->
                                    <div style="background:#f8f9fa;padding:20px 30px;text-align:center;border-top:1px solid #e1e5e9;">
                                      <p style="margin:0;color:#666;font-size:14px;">
                                        Your feedback helps us improve our products and services. Thank you for your time!
                                      </p>
                                    </div>
                
                                  </div>
                                </body>
                                </html>
                
""".formatted(userInput.getFirstName(),
                feadbackurl,userInput.getFirstName(),userInput.getLastName(),userInput.getEmail(),feedbackId,
                feadbackurl, userInput.getFirstName(),userInput.getLastName(),userInput.getEmail(),feedbackId,
                feadbackurl,userInput.getFirstName(),userInput.getLastName(),userInput.getEmail(),feedbackId,
                feadbackurl,userInput.getFirstName(),userInput.getLastName(),userInput.getEmail(),feedbackId,
                feadbackurl,userInput.getFirstName(),userInput.getLastName(),userInput.getEmail(),feedbackId
                );
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(htmlContent, true);
            helper.setTo(userInput.getEmail());
            helper.setSubject("We Value Your Feedback!");
            helper.setFrom(senderEmail);
            mailSender.send(mimeMessage);
            return feedbackId;
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email: " + e.getMessage());
        }
    }

    public void sendFeedbackToCompany(FeedbackData feedbackData) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(senderEmail);
        message.setTo(companyEmail);
        message.setSubject("New Feedback Submission - " + feedbackData.getFirstName());
        message.setText("""
            New Feedback Received:
            First Name: %s
            Last Name: %s
            Email: %s
            Feedback: %s
            Rating: %d stars
            """.formatted(feedbackData.getFirstName(), feedbackData.getLastName(), feedbackData.getEmail(),
                feedbackData.getFeedback(), feedbackData.getRating()));

        try {
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send feedback to company: " + e.getMessage());
        }
    }
}

