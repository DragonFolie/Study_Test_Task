package com.repository;

import com.controller.PersonController;
import com.entity.Person;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
class PersonRepositoryTest {
    @Autowired
    private PersonRepository personRepository;


    private Person person = new Person();

    @BeforeEach
    public void setUp(){
        person.setId(5L);
        person.setName("Test1");
        person.setSurname("Test1_2");
        person.setDate_of_birth("18 Years");



    }


    @Test
    public void shouldCheckIfPersonExists(){

        personRepository.save(person);
        assertTrue(personRepository.existsById(1L));

    }

}