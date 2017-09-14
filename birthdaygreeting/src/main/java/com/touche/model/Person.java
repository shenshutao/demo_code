package com.touche.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

/**
 * Created by shutao on 14/9/17.
 */
@Setter
@Getter
@ToString
public class Person {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String email;
}
