package com.kodilla.csvconverterexercise.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonProcessorTest {


    @Test
    void calculateAge() {

        PersonProcessor processor = new PersonProcessor();

        //given
        String dateOfBirth1 = "1920-01-01";
        String dateOfBirth2 = "1965-05-07";
        String dateOfBirth3 = "1989-12-15";

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