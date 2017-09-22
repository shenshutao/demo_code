package com.shutao;


import com.dumbster.smtp.SimpleSmtpServer;
import com.dumbster.smtp.SmtpMessage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.mail.internet.MimeMessage;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SomeTest {

    @Autowired
    private JavaMailSender sender;

//    private SimpleSmtpServer server;
//
//
//    @Before
//    public void setUp() throws Exception {
//        server = SimpleSmtpServer.start(SimpleSmtpServer.AUTO_SMTP_PORT);
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        server.stop();
//    }


    @Test
    public void sendEmail() throws Exception {
        try (SimpleSmtpServer dumbster = SimpleSmtpServer.start(2500)) {
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setTo("set-your-recipient-email-here@gmail.com");
            helper.setText("How are you?");
            helper.setSubject("Hi");

            sender.send(message);

            List<SmtpMessage> emails = dumbster.getReceivedEmails();
            assertEquals(1, emails.size());
            SmtpMessage email = emails.get(0);
            assertTrue(true);
            assertEquals("Hi", email.getHeaderValue("Subject"));
            assertEquals("How are you?", email.getBody());
            assertEquals("set-your-recipient-email-here@gmail.com", email.getHeaderValue("To"));
        }
    }
}