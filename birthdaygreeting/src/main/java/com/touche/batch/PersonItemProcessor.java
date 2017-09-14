package com.touche.batch;

import com.touche.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import java.time.LocalDate;
import java.time.MonthDay;

/**
 * Created by shutao on 14/9/17.
 */
@Slf4j
public class PersonItemProcessor implements ItemProcessor<Person, SimpleMailMessage> {

    @Value("${mail.from}")
    private String mailFrom;

    @Override
    public SimpleMailMessage process(final Person person) throws Exception {
        final LocalDate dateOfBirth = person.getDateOfBirth();

        LocalDate today = LocalDate.now();
        MonthDay birthday = MonthDay.of(dateOfBirth.getMonth(), dateOfBirth.getDayOfMonth());
        MonthDay currentMonthDay = MonthDay.from(today);

        if (currentMonthDay.equals(birthday)) {
            // It's this person's birthday !!!
            final String lastName = person.getLastName();
            final String mailTo = person.getEmail();

            log.info("it's the birthday of " + lastName + ", send greeting email !");

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(mailFrom);
            message.setTo(mailTo);
            message.setSubject("Happy birthday!");
            message.setText("Happy birthday, dear " + lastName + "!");

            return message;
        } else {
            log.debug("it's not the birthday of " + person.getLastName());
            return null;
        }
    }
}
