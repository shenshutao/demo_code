package com.touche.batch;

import com.touche.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by shutao on 14/9/17.
 */
@Slf4j
public class PersonFieldSetMapper implements FieldSetMapper<Person> {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/d");

    @Override
    public Person mapFieldSet(FieldSet fieldSet) throws BindException {
        Person person = new Person();
        person.setLastName(fieldSet.readString(0));
        person.setFirstName(fieldSet.readString(1));
        String dateOfBirthStr = fieldSet.readString(2);
        person.setDateOfBirth(LocalDate.parse(dateOfBirthStr, formatter));
        person.setEmail(fieldSet.readString(3));

        return person;
    }
}

