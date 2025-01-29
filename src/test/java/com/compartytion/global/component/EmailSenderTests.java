package com.compartytion.global.component;


import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetup;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.compartytion.global.config.MailConfig;


@ExtendWith({SpringExtension.class, GreenMailExtension.class})
@TestPropertySource(locations = "classpath:application.properties")
@ContextConfiguration(classes = {EmailSender.class, MailConfig.class})
@ActiveProfiles("test")
public class EmailSenderTests {

  @RegisterExtension
  private static final GreenMailExtension greenMail = new GreenMailExtension(
      new ServerSetup(23212, "127.0.0.1", ServerSetup.PROTOCOL_SMTP))
      .withConfiguration(GreenMailConfiguration.aConfig()
          .withUser("username", "password"))
      .withPerMethodLifecycle(false);

  @Autowired
  private EmailSender emailSender;


  @BeforeEach
  public void init() throws Exception {
    greenMail.purgeEmailFromAllMailboxes();
  }


  @Test
  @DisplayName("이메일 전송 테스트")
  void givenToSubjectText_whenSendMail_thenShouldReceiveEmail() throws Exception {
    // Given
    String to = "to@example.com";
    String subject = "subject";
    String text = "Hello World!";

    // When
    emailSender.sendMail(to, subject, text);
    greenMail.waitForIncomingEmail(1);
    MimeMessage[] receivedMessages = greenMail.getReceivedMessages();

    // Then
    MimeMessage receivedMessage = receivedMessages[0];
    assertEquals(subject, receivedMessage.getSubject());
    assertEquals(text, receivedMessage.getContent());
  }

}
