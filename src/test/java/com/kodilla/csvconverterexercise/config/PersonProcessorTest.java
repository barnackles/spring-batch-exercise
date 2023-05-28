package com.kodilla.csvconverterexercise.config;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonProcessorTest {


    @Test
    void calculateAge() {

        PersonProcessor processor = new PersonProcessor();

        //given
        LocalDate dateOfBirth1 = LocalDate.of(1920, 1, 1);
        LocalDate dateOfBirth2 = LocalDate.of(1965, 4, 18);
        LocalDate dateOfBirth3 = LocalDate.of(1989, 12, 15);

        // when
        int age1 = processor.calculateAge(dateOfBirth1);
        int age2 = processor.calculateAge(dateOfBirth2);
        int age3 = processor.calculateAge(dateOfBirth3);


        System.out.println(age1);
        System.out.println(age2);
        System.out.println(age3);


        //then
        assertEquals(age1, 103);
        assertEquals(age2, 58);
        assertEquals(age3, 34);



    }
}