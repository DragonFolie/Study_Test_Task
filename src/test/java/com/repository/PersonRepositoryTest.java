package com.repository;

import com.entity.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    private Person person;

    @BeforeEach
    public void setUp() {
        person = new Person();

        person.setName("Test1");
        person.setSurname("Test1_2");
        person.setDate_of_birth("18 Years");

    }


    @AfterEach
    void tearDown() {
        personRepository.deleteAll();
    }


    @Test
    public void shouldCheckIfPersonExists() {

        personRepository.save(person);
        assertTrue(personRepository.existsById(1L));


    }


    @Test
    public void getAllPerson() {

        personRepository.save(person);
        List<Person> list = personRepository.findAll();
        assertEquals(1, list.size());


    }

        /*
                PLEASE RUN THIS TEST INDIVIDUALLY
        */
    @Test
    public void getPersonById() {

            /*
                 PLEASE RUN THIS TEST INDIVIDUALLY
            */

        personRepository.save(person);
        Optional<Person> byId = personRepository.findById(1L);

        assertEquals(person.getId(), byId.get().getId());
        assertEquals(person.getName(), byId.get().getName());

    }


        /*
                PLEASE RUN THIS TEST INDIVIDUALLY
        */

    @Test
    public void savePerson() {

            /*
                 PLEASE RUN THIS TEST INDIVIDUALLY
            */

        personRepository.save(person);
        Person newPerson = personRepository.getById(1L);

        assertEquals(person.toString(), newPerson.toString());

    }


        /*
                PLEASE RUN THIS TEST INDIVIDUALLY
        */

    @Test
    public void deleteById() {

            /*
                 PLEASE RUN THIS TEST INDIVIDUALLY
            */

        personRepository.save(person);
        personRepository.deleteById(1L);
        assertFalse(personRepository.existsById(1L));

    }

}