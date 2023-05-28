package com.kodilla.csvconverterexercise.config;

import com.kodilla.csvconverterexercise.domain.PersonIn;
import com.kodilla.csvconverterexercise.domain.PersonOut;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDate;

public class PersonProcessor implements ItemProcessor<PersonIn, PersonOut> {
    @Override
    public PersonOut process(PersonIn item) throws Exception {

        return new PersonOut(item.getName(), item.getLastName(), calculateAge(item.getDateOfBirth()));
    }

    public int calculateAge(LocalDate dateOfBirth) {

        return LocalDate.now().getYear() - dateOfBirth.getYear();

    }
}
