package com.email_api.email_api.controllers;

import com.email_api.email_api.dto.LoginEmailDTO;
import com.email_api.email_api.login_emails.LoginConfirmationEmail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;

@RestController
@RequestMapping("api/login")
public class LoginEmailsController {

    private final LoginConfirmationEmail loginConfirmationEmail;

    public LoginEmailsController(LoginConfirmationEmail loginConfirmationEmail) {

        this.loginConfirmationEmail = loginConfirmationEmail;
    }

    @PostMapping()
    public ResponseEntity<String> sendLoginEmailConformation(@RequestBody LoginEmailDTO emailData)
            throws MessagingException, IOException {

        loginConfirmationEmail.LoginConfirmationSendEmail(
                emailData.getUserEmail()
                ,emailData.getEmailName()
                ,emailData.getUserName());

        return ResponseEntity.status(HttpStatus.GONE).body("EMAIL SEND");
    }
}
