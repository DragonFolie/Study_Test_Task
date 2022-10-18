package com;

import com.controller.PersonController;
import com.entity.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.repository.PersonRepository;
import com.service.PersonService;
import junit.framework.AssertionFailedError;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@WebMvcTest(PersonController.class)
@TestPropertySource(locations="classpath:testApplication.properties")
public class PersonControllerTest {

    @Autowired
    private ObjectMapper mapper;


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @Autowired
    private Validator validator;

    @Autowired
    private PersonController personController;

    @MockBean
    private PersonRepository personRepository;



    @Test
    public  void  shouldCreateMockMvc(){
        assertNotNull(mockMvc);
    }

    @Test
    public void contextLoads() throws Exception {
        assertThat(personController).isNotNull();
    }




    @Test
    public  void personSuccessAdded(){

        Person person = new Person();
        person.setId(1L);
        person.setName("Test");
        person.setSurname("Test1");
        person.setDate_of_birth("Size" );

        personController.addNewPerson(person);

        assertNotNull(personService.findById(1L));

    }




    @Test
    public  void personListIsEmpty(){

        personController.addNewPerson(null);
        assertTrue( personService.findAllPerson().isEmpty()  );

    }


    @Test
    public  void personNotSuccessAddedWithNullIdParameter(){

        Person person = new Person();
        person.setId(null);
        person.setName("Test");
        person.setSurname("Test1");
        person.setDate_of_birth("Size" );

        personController.addNewPerson(person);

        assertTrue( personService.findAllPerson().isEmpty());

    }



    @Test
    public void shouldReturnListOfUsers() throws Exception {


//        Person person = new Person();
//
//        person.setName("Test");
//        person.setSurname("Test1");
//        person.setDate_of_birth("Size" );


        when(personService.findAllPerson())
                .thenReturn(List.of(new Person("Test","Test1","Size")));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/person/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name()").value("Test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.surname()").value("Test1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.date_of_birth()").value("Size"));

    }






    @Test
    public void getUserByIdSizeOfPersonNameInRangeOfValueShouldReturnTrue() throws Exception {

        Person person = new Person();

        person.setName("Correct");
        person.setSurname("Test1_2");
        person.setDate_of_birth("Size" );

        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        assertTrue(violations.isEmpty());


    }



    @Test
    public void getUserByIdSizeOfPersonNameMoreThan48ShouldReturnTrue() throws Exception {

        Person person = new Person();

        person.setName("Size more than 48: Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s");
        person.setSurname("Test1_2");
        person.setDate_of_birth("Size" );

        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        assertFalse(violations.isEmpty());


    }


    @Test
    public void getUserByIdSizeOfPersonNameLessThan2ShouldReturnTrue() throws Exception {

        Person person = new Person();

        person.setName("t");
        person.setSurname("Test1_2");
        person.setDate_of_birth("Size" );

        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        assertFalse(violations.isEmpty());


    }


    @Test
    public void getUserByIdSizeOfPersonNameIsNullShouldReturnTrue() throws Exception {

        Person person = new Person();

        person.setName(null);
        person.setSurname("Test1_2");
        person.setDate_of_birth("Size" );

        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        assertFalse(violations.isEmpty());


    }






    @Test
    public void getUserByIdSizeOfPersonSurNameMoreThan48ShouldReturnTrue() throws Exception {

        Person person = new Person();

        person.setSurname("Size more than 48: Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s");
        person.setName("Test1_2");
        person.setDate_of_birth("Size" );

        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        assertFalse(violations.isEmpty());


    }


    @Test
    public void getUserByIdSizeOfPersonNameInRangeValueShouldReturnTrue() throws Exception {

        Person person = new Person();

        person.setName("Surname");
        person.setSurname("Correct");
        person.setDate_of_birth("Size" );

        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        assertTrue(violations.isEmpty());


    }



    @Test
    public void getUserByIdSizeOfPersonSurNameLessThan2ShouldReturnTrue() throws Exception {

        Person person = new Person();

        person.setName("Test1");
        person.setSurname("T");
        person.setDate_of_birth("Size" );

        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        assertFalse(violations.isEmpty());


    }


    @Test
    public void getUserByIdSizeOfPersonSurNameInRangeValueShouldReturnTrue() throws Exception {

        Person person = new Person();

        person.setName("Test");
        person.setSurname("Size less than 48 and more than 2");
        person.setDate_of_birth("Test" );

        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        assertTrue(violations.isEmpty());


    }


    @Test
    public void getUserByIdSizeOfPersonSurNameIsNullShouldReturnTrue() throws Exception {

        Person person = new Person();

        person.setName("Test");
        person.setSurname(null);
        person.setDate_of_birth("Test" );

        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        assertFalse(violations.isEmpty());


    }






    @Test
    public void getUserByIdSizeOfPersonBirthdayInRangeValueShouldReturnTrue() throws Exception {

        Person person = new Person();

        person.setName("Test1");
        person.setSurname("Test");
        person.setDate_of_birth("Correct" );

        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        assertTrue(violations.isEmpty());


    }


    @Test
    public void getUserByIdSizeOfPersonBirthdayMoreThant10ShouldReturnTrue() throws Exception {

        Person person = new Person();

        person.setName("Test");
        person.setSurname("Test");
        person.setDate_of_birth("Size more than 10." );

        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        assertFalse(violations.isEmpty());


    }


    @Test
    public void getUserByIdSizeOfPersonBirthdayLessThan1ShouldReturnTrue() throws Exception {

        Person person = new Person();

        person.setName("Test");
        person.setSurname("surname" );
        person.setDate_of_birth(null);


        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        assertFalse(violations.isEmpty());


    }









    @Test
    public void getUserByIdTestShouldReturnTrue() throws Exception {

        Person person = new Person();

        person.setName("Test1");
        person.setSurname("Test1_2");
        person.setDate_of_birth("18 Years");


        when(personService.findById(anyLong())).thenReturn(java.util.Optional.of(person));

        mockMvc.perform(get("/person/9"))
                .andExpect(jsonPath("$.name").value("Test1"))
                .andExpect(jsonPath("$.surname").value("Test1_2"))
                .andExpect(jsonPath("$.date_of_birth").value("18 Years"))
                .andExpect(status().isOk());


    }



    /*

                        This test should return false

     */

    @Test
    public void getUserByIdTestWithInCorrectBirthDayShouldReturnFalse() throws Exception {

        Person person = new Person();

        person.setName("Test1");
        person.setSurname("Test1_2");
        person.setDate_of_birth("18 Years");


        when(personService.findById(anyLong())).thenReturn(java.util.Optional.of(person));

        mockMvc.perform(get("/person/9"))
                .andExpect(jsonPath("$.name").value("Test1"))
                .andExpect(jsonPath("$.surname").value("Test1_2"))
                .andExpect(jsonPath("$.date_of_birth").value("not correct"))
                .andExpect(status().isOk());

    }


    /*

                 This test should return false

     */
    @Test
    public void getUserByIdTestWithInCorrectSurnameShouldReturnFalse() throws Exception {

        Person person = new Person();

        person.setName("Test1");
        person.setSurname("Test1_2");
        person.setDate_of_birth("18 Years");


        when(personService.findById(anyLong())).thenReturn(java.util.Optional.of(person));

        mockMvc.perform(get("/person/9"))
                .andExpect(jsonPath("$.name").value("Test1"))
                .andExpect(jsonPath("$.surname").value("not correct"))
                .andExpect(jsonPath("$.date_of_birth").value("18 Years"))
                .andExpect(status().isOk());

    }



    /*

                     This test should return false

     */
    @Test
    public void getUserByIdTestWithInCorrectNameShouldReturnFalse() throws Exception {

        Person person = new Person();

        person.setName("Test1");
        person.setSurname("Test1_2");
        person.setDate_of_birth("18 Years");


        when(personService.findById(anyLong())).thenReturn(java.util.Optional.of(person));

        mockMvc.perform(get("/person/9"))
                .andExpect(jsonPath("$.name").value("not correct"))
                .andExpect(jsonPath("$.surname").value("Test1_2"))
                .andExpect(jsonPath("$.date_of_birth").value("18 Years"))
                .andExpect(status().isOk());

    }



    @Test
    public void savePersonTestShouldReturnTrue() throws Exception {

        Person person = new Person();

        person.setId(5L);
        person.setName("Test1");
        person.setSurname("Test1_2");
        person.setDate_of_birth("18 Years");


        when(personService.addNewPerson(any(Person.class))).thenReturn(person);


        mockMvc.perform(MockMvcRequestBuilders.post("/person/")
                        .content(new ObjectMapper().writeValueAsString(person))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Test1"))
                .andExpect(jsonPath("$.surname").value("Test1_2"))
                .andExpect(jsonPath("$.date_of_birth").value("18 Years"));


    }



    @Test
    public void updatePersonTestShouldReturnTrue() throws Exception {

        Person RECORD_1 = new Person();
        RECORD_1.setId(1L);
        RECORD_1.setName("Test1");
        RECORD_1.setSurname("Test1_2");
        RECORD_1.setDate_of_birth("22 Years");

        Person person = new Person();
        person.setId(5L);
        person.setName("Test2");
        person.setSurname("Test2_2");
        person.setDate_of_birth("55 Years");


        Mockito.when(personRepository.findById(RECORD_1.getId())).thenReturn(Optional.of(RECORD_1));
        Mockito.when(personRepository.save(person)).thenReturn(person);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/person/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(person));

        mockMvc.perform(mockRequest)
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$.name", is("Rayven Zambo")));

    }



//    @Test
//    public void deletePersonIfFound() throws Exception {
//
//
//
//        Person person = new Person();
//        person.setId(5L);
//        person.setName("Test2");
//        person.setSurname("Test2_2");
//        person.setDate_of_birth("55 Years");
//
//        doReturn(Optional.of(person)).when(this.personService).findById(5L);
//
//        mockMvc.perform(MockMvcRequestBuilders.delete("/person/5L", "Test2"))
//                .andExpect(status().isAccepted());
//    }

    @Test
    public void deletePersonIfNotFound() throws Exception {

        doReturn(Optional.empty()).when(this.personService).findById(6L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/person/", "Test"))
                .andExpect(status().is4xxClientError());
    }











}
