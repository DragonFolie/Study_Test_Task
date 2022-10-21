package com.service;

import com.entity.Person;
import com.repository.PersonRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@DataJpaTest
@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @Captor
    private ArgumentCaptor<Person> emailCaptor;

    private AutoCloseable autoCloseable;
    private PersonService personService;
    private Person person = new Person();


    @BeforeEach
    void setUp() {

        person.setId(1L);
        person.setName("Test1");
        person.setSurname("Test1_2");
        person.setDate_of_birth("18 Years");
        autoCloseable = MockitoAnnotations.openMocks(this);
        personService = new PersonService(personRepository);
    }


    @Test
    void verifyThatFindAllPersonInvoke() {

        personService.findAllPerson();
        verify(personRepository).findAll();

    }


    @Test
    void noSuchElementExpectedInFindAllPersonMethod() {

        List<Person> list = personService.findAllPerson();

        assertEquals(0, list.size());
        verify(personRepository).findAll();


    }


    @Test
    void verifyThatFindByIdInvoke() {

        personService.findById(1L);
        verify(personRepository).findById(1L);

    }

    @Test
    void verifyThatAddNewPersonInvoke() {

        personService.addNewPerson(person);
        verify(personRepository).save(person);

    }

    @Test
    void checkThatWeAddedTheSameElementWhichWeAGiveToRepository() {

        ArgumentCaptor<Person> personArgumentCaptor = ArgumentCaptor.forClass(Person.class);
        personService.addNewPerson(person);
        verify(personRepository).save(personArgumentCaptor.capture());


        Person personTest = personArgumentCaptor.getValue();
        assertThat(personTest).isEqualTo(person);
        verify(personRepository).save(any());


    }

    @Test
    void verifyThatUpdatePersonInvoke() {
        personService.updatePerson(person);
        verify(personRepository).save(person);
    }

    @Test
    void checkThatWeUpdateHaveTheSameElementWhichWeAGiveToRepository() {

        ArgumentCaptor<Person> personArgumentCaptor = ArgumentCaptor.forClass(Person.class);
        personService.updatePerson(person);
        verify(personRepository).save(personArgumentCaptor.capture());


        Person personTest = personArgumentCaptor.getValue();
        assertThat(personTest).isEqualTo(person);
        verify(personRepository).save(any());


    }


    @Test
    void verifyThatDeletePersonByIdInvoke() {
        personService.deletePersonById(11L);
        verify(personRepository).deleteById(11L);
    }
}