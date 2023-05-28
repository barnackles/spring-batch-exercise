package com.kodilla.csvconverterexercise.domain;

import java.time.LocalDate;

public class PersonIn {

    private final String name;
    private final String lastName;
    private final LocalDate dateOfBirth;

    public PersonIn(String name, String lastName, LocalDate dateOfBirth) {
        this.name = name;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
}
