package org.nexus.nexkartfrontend.customer;



import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.*;
import org.nexus.nexkartfrontend.Utility;
import org.nexus.nexkartfrontend.setting.EmailSettingBag;
import org.nexus.nexkartfrontend.setting.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.UnsupportedEncodingException;


@Controller
public class ForgotPasswordController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private SettingService settingService;

    @GetMapping("/forgot_password")
    public String showRequestForm() {
        return "customer/forgot_password_form";
    }

    @PostMapping("/forgot_password")
    public String processRequestForm(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");
        try {
            String token = customerService.updateResetPasswordToken(email);
            String link = Utility.getSiteURL(request) + "/reset_password?token=" + token;
            sendEmail(link, email);

            model.addAttribute("message", "We have sent a reset password link to your email."
                    + " Please check.");
        } catch (CustomerNotFoundException e) {
            model.addAttribute("error", e.getMessage());
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Could not send email");
        }

        return "customer/forgot_password_form";
    }

    private void sendEmail(String link, String email)
            throws UnsupportedEncodingException, MessagingException {

        EmailSettingBag emailSettings = settingService.getEmailSettings();
        JavaMailSenderImpl mailSender = Utility.prepareMailSender(emailSettings);

        String toAddress = email;
        String subject = "Here's the link to reset your password";

        String content =
                "<div style='max-width: 600px; margin: 0 auto; font-family: -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, sans-serif;'>" +
                        "<div style='background: #f0f9ff; padding: 2rem; border-radius: 12px;'>" +
                        "<div style='text-align: center; margin-bottom: 1.5rem;'>" +
                        "<div style='display: inline-block; background: #3B82F6; padding: 12px; border-radius: 8px; margin-bottom: 1rem;'>" +
                        "<svg style='width: 32px; height: 32px; color: white;' fill='none' stroke='currentColor' viewBox='0 0 24 24' xmlns='http://www.w3.org/2000/svg'><path stroke-linecap='round' stroke-linejoin='round' stroke-width='2' d='M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z'></path></svg>" +
                        "</div>" +
                        "<h1 style='font-size: 1.5rem; color: #1e3a8a; margin-bottom: 1rem; font-weight: 600;'>Password Reset Request</h1>" +
                        "</div>" +

                        "<div style='color: #4b5563; line-height: 1.6; margin-bottom: 1.5rem;'>" +
                        "<p style='margin-bottom: 1rem;'>Hello,</p>" +
                        "<p style='margin-bottom: 1rem;'>You have requested to reset your password. Click the button below to set up a new password:</p>" +
                        "<div style='text-align: center; margin: 2rem 0;'>" +
                        "<a href='" + link + "' style='display: inline-block; background: #3B82F6; color: white; padding: 12px 24px; border-radius: 8px; text-decoration: none; font-weight: 500; transition: background-color 0.2s;'>Reset Password</a>" +
                        "</div>" +
                        "<p style='margin-bottom: 1rem; font-size: 0.875rem; color: #6b7280;'>If you didn't request this password reset, you can safely ignore this email.</p>" +
                        "</div>" +

                        "<div style='border-top: 1px solid #e5e7eb; padding-top: 1.5rem; text-align: center;'>" +
                        "<p style='font-size: 0.875rem; color: #6b7280;'>" +
                        "Need help? <a href='springboot2559@gmail.com' style='color: #3B82F6; text-decoration: none;'>Contact Support</a>" +
                        "</p>" +
                        "</div>" +
                        "</div>" +

                        "<div style='text-align: center; margin-top: 2rem; color: #6b7280; font-size: 0.875rem;'>" +
                        "<p>© 2024 Nexkart All rights reserved.</p>" +
                        "</div>" +
                        "</div>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(emailSettings.getFromAddress(), emailSettings.getSenderName());
        helper.setTo(toAddress);
        helper.setSubject(subject);

        helper.setText(content, true);
        mailSender.send(message);
    }

    @GetMapping("/reset_password")
    public String showResetForm(@Param("token") String token, Model model) {
        Customer customer = customerService.getByResetPasswordToken(token);
        if (customer != null) {
            model.addAttribute("token", token);
        } else {
            model.addAttribute("pageTitle", "Invalid Token");
            model.addAttribute("message", "Invalid Token");
            return "message";
        }

        return "customer/reset_password_form";
    }

    @PostMapping("/reset_password")
    public String processResetForm(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        try {
            customerService.updatePassword(token, password);

            model.addAttribute("pageTitle", "Reset Your Password");
            model.addAttribute("title", "Reset Your Password");
            model.addAttribute("message", "You have successfully changed your password.");

        } catch (CustomerNotFoundException e) {
            model.addAttribute("pageTitle", "Invalid Token");
            model.addAttribute("message", e.getMessage());
        }

        return "message";
    }

}