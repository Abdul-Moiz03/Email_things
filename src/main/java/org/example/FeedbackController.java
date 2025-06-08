package org.example;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller

@RequestMapping("/api")
public class FeedbackController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/send-email")
    public ResponseEntity<Map<String, Object>> sendEmail(@RequestBody UserInput userInput) {
        try {
            if (userInput.getFirstName() == null || userInput.getLastName() == null || userInput.getEmail() == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Missing required fields"));
            }
            String feedbackId = emailService.sendInitialEmail(userInput);
            return ResponseEntity.ok(Map.of("message", "Email sent successfully", "feedbackId", feedbackId));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to send email: " + e.getMessage()));
        }
    }

//    @PostMapping("/submit-feedback")
//    public ResponseEntity<Map<String, Object>> submitFeedback(@RequestBody FeedbackData feedbackData) {
//        try {
//            if (feedbackData.getFirstName() == null || feedbackData.getLastName() == null ||
//                    feedbackData.getEmail() == null || feedbackData.getFeedback() == null ||
//                    feedbackData.getRating() == 0 || feedbackData.getFeedbackId() == null) {
//                return ResponseEntity.badRequest().body(Map.of("error", "Missing required fields"));
//            }
//            emailService.sendFeedbackToCompany(feedbackData);
//            return ResponseEntity.ok(Map.of("message", "Feedback submitted and forwarded successfully"));
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body(Map.of("error", "Failed to forward feedback: " + e.getMessage()));
//        }
//    }
//@PostMapping(value = "/submit-feedback", consumes = {"application/x-www-form-urlencoded", "application/json"})
//public ResponseEntity<String> submitFeedback(
//        @RequestParam("firstName") String firstName,
//        @RequestParam("lastName") String lastName,
//        @RequestParam("email") String email,
//        @RequestParam("feedbackId") String feedbackId,
//        @RequestParam("rating") String rating,
//        @RequestParam("feedback") String feedback
//) {
//    System.out.println("Received feedback request with Content-Type: " );
//    System.out.println("First Name: " + firstName);
//    System.out.println("Last Name: " + lastName);
//    System.out.println("Email: " + email);
//    System.out.println("Feedback: " + feedback);
//    System.out.println("Rating: " + rating);
//    System.out.println("Feedback ID: " + feedbackId);
//   emailService.sendFeedbackToCompany(firstName);
//
//    return ResponseEntity.ok("Feedback submitted successfully!");
//}
@GetMapping("/submit-feedback")
public String submitFeedback(@ModelAttribute FeedbackData feedbackData) {
    try {
        // Check if both feedback and rating are empty
        if ((feedbackData.getFeedback() == null || feedbackData.getFeedback().isBlank()) &&
                (feedbackData.getRating() == 0 )) {

            return "invalid"; // Show invalid.html
        }

        emailService.sendFeedbackToCompany(feedbackData);
        return "thankyou"; // Show thankyou.html
    } catch (Exception e) {
        // Log the exception if needed
        e.printStackTrace(); // or use a logger
        return "invalid"; // Show invalid.html on error
    }
}
}