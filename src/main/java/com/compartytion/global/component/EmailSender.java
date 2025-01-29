package com.compartytion.global.component;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Log4j2
@Component
@RequiredArgsConstructor
public class EmailSender {

  private final JavaMailSender mailSender;
  private final SimpleMailMessage mailMessage;

  private SimpleMailMessage getMailMessageMessage(String to, String subject, String text) {
    SimpleMailMessage message = new SimpleMailMessage(mailMessage);
    message.setTo(to);
    message.setSubject(subject);
    message.setText(text);
    return message;
  }

  @Async
  public void sendMail(String to, String subject, String text) {
    SimpleMailMessage message = getMailMessageMessage(to, subject, text);
    try {
      mailSender.send(message);
      log.debug("Email sent");
    } catch (MailException e) {
      log.error(e.getMessage());
    }
  }

}
