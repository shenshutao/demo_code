package com.touche;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Arrays;

@Slf4j
public class MockMailSender extends JavaMailSenderImpl {

    @Override
    public void testConnection() throws MessagingException {
        // do nothing
    }

    @Override
    protected void doSend(MimeMessage[] mimeMessages, Object[] originalMessages) throws MailException {
        if (mimeMessages == null || mimeMessages.length == 0) {
            log.info("Email message is Empty");
        } else {
            for (MimeMessage message : mimeMessages) {
                log.info("Sending email ...");

                try {
                    log.info("Mail from: " + Arrays.toString(message.getFrom()));
                    log.info("Mail to: " + Arrays.toString(message.getRecipients(Message.RecipientType.TO)));
                    log.info("Mail subject: " + message.getSubject());
                    log.info("Mail content: " + message.getContent());
                } catch (MessagingException | IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}