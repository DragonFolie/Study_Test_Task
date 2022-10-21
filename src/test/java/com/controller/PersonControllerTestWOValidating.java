package com.controller;

import com.controller.PersonController;
import com.entity.Person;
import com.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonControllerTestWOValidating {

    @MockBean
    private LocalValidatorFactoryBean validator;

    @MockBean
    private PersonService personService;


    @Autowired
    private PersonController personController;


    @Test
    public  void personNotSuccessAddedWithNullNameParameter(){




        Person person = new Person();
        person.setId(1L);
        person.setName(null);
        person.setSurname("Test1");
        person.setDate_of_birth("Size" );

        NullPointerException thrown = assertThrows(
                NullPointerException.class,
                () -> personController.addNewPerson(person),
                "Expected addNewPerson() to throw, but it didn't"
        );


    }


    @Test
    public  void personNotSuccessAddedWithNullSurNameParameter(){

        Person person = new Person();
        person.setId(1L);
        person.setName("Test1");
        person.setSurname(null);
        person.setDate_of_birth("Size" );


            NullPointerException thrown = assertThrows(
                    NullPointerException.class,
                    () -> personController.addNewPerson(person),
                    "Expected addNewPerson() to throw, but it didn't"
            );



    }


    @Test
    public  void personNotSuccessAddedWithNullDateOfBirthParameter() {

        Person person = new Person();
        person.setId(1L);
        person.setName("Test1");
        person.setSurname("Test1");
        person.setDate_of_birth(null);



        NullPointerException thrown = assertThrows(
                NullPointerException.class,
                () -> personController.addNewPerson(person),
                "Expected addNewPerson() to throw, but it didn't"
        );


    }
        @Test
        public  void nullPointerExceptionWhenAddNullParameterToAddNewPersonMethod(){


            NullPointerException thrown = assertThrows(
                    NullPointerException.class,
                    () -> personController.addNewPerson(null),
                    "Expected addNewPerson() to throw, but it didn't"
            );

        }
    }




