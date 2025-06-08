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
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Customer Feedback Form</title>
                </head>
                <body style="margin: 0; padding: 20px; font-family: Arial, sans-serif; background-color: #f5f5f5; line-height: 1.6;">
                    <div style="max-width: 600px; margin: 0 auto; background: white; border-radius: 12px; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); overflow: hidden;">
                          <div style="background: linear-gradient(135deg, #667eea 0%%, #764ba2 100%%); color: white; padding: 30px 20px; text-align: center;">
                                                    <h1 style="margin: 0; font-size: 28px; font-weight: 600;">Customer Feedback</h1>
                                                    <p style="margin: 10px 0 0 0; opacity: 0.9; font-size: 16px;">We appreciate your business and value your opinion</p>
                                                </div>
                        <div style="padding: 40px 30px;">
                            <div style="margin-bottom: 30px;">
                                <p style="margin: 0 0 15px 0; font-size: 18px; color: #333; font-weight: 500;">Dear Mr/Mrs %s,</p>
                                <p style="margin: 0; font-size: 16px; color: #666; line-height: 1.5;">Thank you for your recent purchase from our store. We hope you're satisfied with your experience.</p>
                            </div>
                            <form action="%s" method="GET" target="_blank">
                               <input type="hidden" name="firstName" value="%s">
                               <input type="hidden" name="lastName" value="%s">
                                <input type="hidden" name="email" value="%s">
                                <input type="hidden" name="feedbackId" value="%s">
                                <div style="margin-bottom: 25px;">
                                    <h3 style="margin: 0 0 20px 0; font-size: 20px; color: #333; font-weight: 600;">How did we do?</h3>
                                    <div>
                                        <label style="display: block; padding: 12px 16px; border: 2px solid #e1e5e9; border-radius: 8px; cursor: pointer; background: #fff; margin-bottom: 12px;">
                                                          <span style="display: inline-block; vertical-align: middle; margin-right: 15px;">
                                                            <input type="radio" name="rating" value="5" required>
                                                          </span>
                                                          <span style="display: inline-block; vertical-align: middle;">
                                                            <span style="font-size: 24px; color: #10b981; margin-right: 4px;">★</span>
                                                            <span style="font-size: 24px; color: #10b981; margin-right: 4px;">★</span>
                                                            <span style="font-size: 24px; color: #10b981; margin-right: 4px;">★</span>
                                                            <span style="font-size: 24px; color: #10b981; margin-right: 4px;">★</span>
                                                            <span style="font-size: 24px; color: #10b981; margin-right: 15px;">★</span>
                                                            <span style="font-size: 16px; color: #333; font-weight: 500;">Excellent</span>
                                                          </span>
                                                        </label>
                
                                                        <label style="display: block; padding: 12px 16px; border: 2px solid #e1e5e9; border-radius: 8px; cursor: pointer; background: #fff; margin-bottom: 12px;">
                                                          <span style="display: inline-block; vertical-align: middle; margin-right: 15px;">
                                                            <input type="radio" name="rating" value="4" required>
                                                          </span>
                                                          <span style="display: inline-block; vertical-align: middle;">
                                                            <span style="font-size: 24px; color: #10b981; margin-right: 4px;">★</span>
                                                            <span style="font-size: 24px; color: #10b981; margin-right: 4px;">★</span>
                                                            <span style="font-size: 24px; color: #10b981; margin-right: 4px;">★</span>
                                                            <span style="font-size: 24px; color: #10b981; margin-right: 4px;">★</span>
                                                            <span style="font-size: 24px; color: #d1d5db; margin-right: 15px;">★</span>
                                                            <span style="font-size: 16px; color: #333; font-weight: 500;">Very Good</span>
                                                          </span>
                                                        </label>
                
                                                        <label style="display: block; padding: 12px 16px; border: 2px solid #e1e5e9; border-radius: 8px; cursor: pointer; background: #fff; margin-bottom: 12px;">
                                                          <span style="display: inline-block; vertical-align: middle; margin-right: 15px;">
                                                            <input type="radio" name="rating" value="3" required>
                                                          </span>
                                                          <span style="display: inline-block; vertical-align: middle;">
                                                            <span style="font-size: 24px; color: #fbbf24; margin-right: 4px;">★</span>
                                                            <span style="font-size: 24px; color: #fbbf24; margin-right: 4px;">★</span>
                                                            <span style="font-size: 24px; color: #fbbf24; margin-right: 4px;">★</span>
                                                            <span style="font-size: 24px; color: #d1d5db; margin-right: 4px;">★</span>
                                                            <span style="font-size: 24px; color: #d1d5db; margin-right: 15px;">★</span>
                                                            <span style="font-size: 16px; color: #333; font-weight: 500;">Good</span>
                                                          </span>
                                                        </label>
                
                                                        <label style="display: block; padding: 12px 16px; border: 2px solid #e1e5e9; border-radius: 8px; cursor: pointer; background: #fff; margin-bottom: 12px;">
                                                          <span style="display: inline-block; vertical-align: middle; margin-right: 15px;">
                                                            <input type="radio" name="rating" value="2" required>
                                                          </span>
                                                          <span style="display: inline-block; vertical-align: middle;">
                                                            <span style="font-size: 24px; color: #f97316; margin-right: 4px;">★</span>
                                                            <span style="font-size: 24px; color: #f97316; margin-right: 4px;">★</span>
                                                            <span style="font-size: 24px; color: #d1d5db; margin-right: 4px;">★</span>
                                                            <span style="font-size: 24px; color: #d1d5db; margin-right: 4px;">★</span>
                                                            <span style="font-size: 24px; color: #d1d5db; margin-right: 15px;">★</span>
                                                            <span style="font-size: 16px; color: #333; font-weight: 500;">Fair</span>
                                                          </span>
                                                        </label>
                
                                                        <label style="display: block; padding: 12px 16px; border: 2px solid #e1e5e9; border-radius: 8px; cursor: pointer; background: #fff; margin-bottom: 12px;">
                                                          <span style="display: inline-block; vertical-align: middle; margin-right: 15px;">
                                                            <input type="radio" name="rating" value="1" required>
                                                          </span>
                                                          <span style="display: inline-block; vertical-align: middle;">
                                                            <span style="font-size: 24px; color: #ef4444; margin-right: 4px;">★</span>
                                                            <span style="font-size: 24px; color: #d1d5db; margin-right: 4px;">★</span>
                                                            <span style="font-size: 24px; color: #d1d5db; margin-right: 4px;">★</span>
                                                            <span style="font-size: 24px; color: #d1d5db; margin-right: 4px;">★</span>
                                                            <span style="font-size: 24px; color: #d1d5db; margin-right: 15px;">★</span>
                                                            <span style="font-size: 16px; color: #333; font-weight: 500;">Poor</span>
                                                          </span>
                                                        </label>
                                    </div>
                                </div>
                                <div style="margin-bottom: 25px;">
                                    <label for="feedback" style="display: block; margin-bottom: 12px; font-weight: 600; color: #333; font-size: 16px;">
                                        Tell us more about your experience:
                                    </label>
                                    <textarea
                                        id="feedback"
                                        name="feedback"
                                        rows="5"
                                        placeholder="Please share any additional comments, suggestions, or details about your experience with us..."
                                        style="width: 100%%; padding: 12px 16px; border: 2px solid #e1e5e9; border-radius: 8px; font-size: 16px; resize: vertical; min-height: 120px; box-sizing: border-box; font-family: Arial, sans-serif;"
                                    ></textarea>
                                </div>
                               
                             <button
                              type="submit"
                              style="background: linear-gradient(135deg, #667eea 0%%, #764ba2 100%%); color: white; border: none; padding: 16px 32px; border-radius: 8px; font-size: 16px; font-weight: 600; cursor: pointer; font-family: inherit; transition: all 0.3s ease;"
                              >
                              Submit Feedback
                              </button>
                            </form>
                        </div>
                        <div style="background: #f8f9fa; padding: 20px 30px; text-align: center; border-top: 1px solid #e1e5e9;">
                            <p style="margin: 0; color: #666; font-size: 14px;">Your feedback helps us improve our products and services. Thank you for your time!</p>
                        </div>
                    </div>
                
                    <style>
                      form:not(:has(input[name="rating"]:checked)) button[type="submit"] {
                                        pointer-events: none;
                                        opacity: 0.5;
                                      }
                        label:hover {
                            border-color: #667eea;
                            background-color: #f8faff;
                        }
                        input[type="radio"]:checked + span {
                            color: #667eea;
                        }
                        textarea:focus {
                            border-color: #667eea;
                            outline: none;
                        }
                        button:hover {
                            background: #5a67d8;
                        }
                         
                        @media (max-width: 480px) {
                            body {
                                padding: 10px;
                            }
                            label span {
                                font-size: 20px;
                            }
                            label {
                                padding: 10px 12px;
                            }
                            input, textarea, button {
                                font-size: 16px;
                            }
                        }
                        @media (max-width: 360px) {
                            label span {
                                font-size: 18px;
                            }
                            label {
                                line-height: 1.8;
                            }
                        }
                    </style>
                
                </body>
                </html>
""".formatted(userInput.getFirstName(),feadbackurl,userInput.getFirstName(), userInput.getLastName(), userInput.getEmail(), feedbackId);
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
            Feedback ID: %s
            """.formatted(feedbackData.getFirstName(), feedbackData.getLastName(), feedbackData.getEmail(),
                feedbackData.getFeedback(), feedbackData.getRating(), feedbackData.getFeedbackId()));

        try {
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send feedback to company: " + e.getMessage());
        }
    }
}

