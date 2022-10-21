package com;

import com.controller.PersonController;
import com.entity.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.repository.PersonRepository;
import com.service.PersonService;
import junit.framework.AssertionFailedError;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.NestedServletException;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.*;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PersonController.class)
@TestPropertySource(locations = "classpath:testApplication.properties")
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
    public void shouldReturnIsOkStatus() throws Exception {

        Person person = new Person();


        List<Person> list = new ArrayList<>(Arrays.asList(person, person));

        when(personService.findAllPerson()).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/person/"))
                .andExpect(status().isOk());
    }


    @Test
    public void shouldReturnIs4xxClientError() throws Exception {

        Person person = new Person();

        person.setId(1L);
        person.setName("Test");
        person.setSurname("Test1");
        person.setDate_of_birth("Size");


        List<Person> list = new ArrayList<>(Arrays.asList(person, person));

        when(personService.findAllPerson()).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/person/"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void getPersonByIdSuccess() throws Exception {

        Person person = new Person();

        person.setId(1L);
        person.setName("Test");
        person.setSurname("Test1");
        person.setDate_of_birth("Size");

        Mockito.when(personService.findById(person.getId())).thenReturn(java.util.Optional.of(person));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/person/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Test")));
    }


    @Test
    public void getPersonByIdNotSuccess() throws Exception {

        Person person = new Person();

        person.setId(1L);
        person.setName("Test");
        person.setSurname("Test1");
        person.setDate_of_birth("Size");

        Mockito.when(personService.findById(person.getId())).thenReturn(java.util.Optional.of(person));



        NestedServletException thrown = assertThrows(
                NestedServletException.class,
                () -> mockMvc.perform(MockMvcRequestBuilders
                                .get("/person/5")
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", notNullValue()))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Test"))),
                "The main reason why we have this exception is NoSuchElementException with id  5 , but we catch on NestedServletException  " +
                        "org.springframework.web.util.NestedServletException: Request processing failed; " +
                        "nested exception is java.util.NoSuchElementException: No such person founded in list of persons"
        );
    }

    @Test
    public void getPersonByIdWithIllegalArguments() throws Exception {

        Person person = new Person();

        person.setId(1L);
        person.setName("Test");
        person.setSurname("Test1");
        person.setDate_of_birth("Size");

        Mockito.when(personService.findById(person.getId())).thenReturn(java.util.Optional.of(person));



        NestedServletException thrown = assertThrows(
                NestedServletException.class,
                () -> mockMvc.perform(MockMvcRequestBuilders
                                .get("/person/-1")
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", notNullValue()))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Test"))),
                "The main reason why we have this exception is NoSuchElementException with id  -1 , but we catch on NestedServletException  " +
                        "org.springframework.web.util.NestedServletException: Request processing failed; " +
                        "nested exception is java.util.NoSuchElementException: No such person founded in list of persons"
        );
    }


    @Test
    public void shouldReturnListOfUsers() throws Exception {


        Person person = new Person();

        person.setId(1L);
        person.setName("Test");
        person.setSurname("Test1");
        person.setDate_of_birth("Size");


        List<Person> list = new ArrayList<>(Arrays.asList(person, person));

        when(personService.findAllPerson()).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/person/"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("Test")));


    }


    @Test()
    public void shouldReturnNoSuchElementExceptionInFindAllMethod() throws Exception {


        List<Person> list = new ArrayList<>();

        when(personService.findAllPerson()).thenReturn(list);

        NestedServletException thrown = assertThrows(
                NestedServletException.class,
                () -> mockMvc.perform(MockMvcRequestBuilders
                                .get("/person/"))
                        .andExpect(status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(0)))
                        .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is(null))),
                "The main reason why we have this exception is NoSuchElementException, but we catch on NestedServletException  " +
                        "org.springframework.web.util.NestedServletException: Request processing failed; " +
                        "nested exception is java.util.NoSuchElementException: No such person founded in list of persons"

        );


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
                .andDo(print())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Test1"))
                .andExpect(jsonPath("$.surname").value("Test1_2"))
                .andExpect(jsonPath("$.date_of_birth").value("18 Years"));


    }


    @Test
    public void savePersonTestShouldReturnException() throws Exception {

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
    public void updatePersonRecordSuccess() throws Exception {


        Person person = new Person();
        person.setId(5L);
        person.setName("Test2");
        person.setSurname("Test2_2");
        person.setDate_of_birth("55 Years");

        Mockito.when(personService.updatePerson(person)).thenReturn(person);


        mockMvc.perform(MockMvcRequestBuilders.put("/person/")
                        .content(new ObjectMapper().writeValueAsString(person))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


    }


    @Test
    public void deletePersonByIdSuccess() throws Exception {

        Person person = new Person();
        person.setId(5L);
        person.setName("Test2");
        person.setSurname("Test2_2");
        person.setDate_of_birth("55 Years");

        Mockito.when(personService.findById(person.getId())).thenReturn(Optional.of(person));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/person/{id}", 5L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deletePersonByIdNotSuccess() throws Exception {

        Person person = new Person();
        person.setId(5L);
        person.setName("Test2");
        person.setSurname("Test2_2");
        person.setDate_of_birth("55 Years");

        Mockito.when(personService.findById(person.getId())).thenReturn(Optional.of(person));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/person/{id}", 555555L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    public void deletePersonByIdWithIllegalArgument() throws Exception {

        Person person = new Person();
        person.setId(5L);
        person.setName("Test2");
        person.setSurname("Test2_2");
        person.setDate_of_birth("55 Years");

        Mockito.when(personService.findById(person.getId())).thenReturn(Optional.of(person));


        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> mockMvc.perform(MockMvcRequestBuilders
                                .delete("/person/{id}", null)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk()),
                "Expected to delete with correct argument, but we use illegal argument"
        );
    }


    @Test
    public void deletePersonIfNotFound() throws Exception {

        doReturn(Optional.empty()).when(this.personService).findById(6L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/person/", "Test"))
                .andExpect(status().is4xxClientError());
    }


    @Test
    public void shouldCreateMockMvc() {
        assertNotNull(mockMvc);
    }

    @Test
    public void contextLoads() throws Exception {
        assertThat(personController).isNotNull();
    }


    @Test
    public void personSuccessAdded() {

        Person person = new Person();
        person.setId(1L);
        person.setName("Test");
        person.setSurname("Test1");
        person.setDate_of_birth("Size");

        personController.addNewPerson(person);

        assertNotNull(personService.findById(1L));

    }


    @Test
    public void personListIsEmpty() {

        personController.addNewPerson(null);
        assertTrue(personService.findAllPerson().isEmpty());

    }


    @Test
    public void personNotSuccessAddedWithNullIdParameter() {

        Person person = new Person();
        person.setId(null);
        person.setName("Test");
        person.setSurname("Test1");
        person.setDate_of_birth("Size");

        personController.addNewPerson(person);

        assertTrue(personService.findAllPerson().isEmpty());

    }


    @Test
    public void getUserByIdSizeOfPersonNameInRangeOfValueShouldReturnTrue() throws Exception {

        Person person = new Person();

        person.setName("Correct");
        person.setSurname("Test1_2");
        person.setDate_of_birth("Size");

        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        assertTrue(violations.isEmpty());


    }


    @Test
    public void getUserByIdSizeOfPersonNameMoreThan48ShouldReturnTrue() throws Exception {

        Person person = new Person();

        person.setName("Size more than 48: Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s");
        person.setSurname("Test1_2");
        person.setDate_of_birth("Size");

        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        assertFalse(violations.isEmpty());


    }


    @Test
    public void getUserByIdSizeOfPersonNameLessThan2ShouldReturnTrue() throws Exception {

        Person person = new Person();

        person.setName("t");
        person.setSurname("Test1_2");
        person.setDate_of_birth("Size");

        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        assertFalse(violations.isEmpty());


    }


    @Test
    public void getUserByIdSizeOfPersonNameIsNullShouldReturnTrue() throws Exception {

        Person person = new Person();

        person.setName(null);
        person.setSurname("Test1_2");
        person.setDate_of_birth("Size");

        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        assertFalse(violations.isEmpty());


    }


    @Test
    public void getUserByIdSizeOfPersonSurNameMoreThan48ShouldReturnTrue() throws Exception {

        Person person = new Person();

        person.setSurname("Size more than 48: Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s");
        person.setName("Test1_2");
        person.setDate_of_birth("Size");

        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        assertFalse(violations.isEmpty());


    }


    @Test
    public void getUserByIdSizeOfPersonNameInRangeValueShouldReturnTrue() throws Exception {

        Person person = new Person();

        person.setName("Surname");
        person.setSurname("Correct");
        person.setDate_of_birth("Size");

        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        assertTrue(violations.isEmpty());


    }


    @Test
    public void getUserByIdSizeOfPersonSurNameLessThan2ShouldReturnTrue() throws Exception {

        Person person = new Person();

        person.setName("Test1");
        person.setSurname("T");
        person.setDate_of_birth("Size");

        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        assertFalse(violations.isEmpty());


    }


    @Test
    public void getUserByIdSizeOfPersonSurNameInRangeValueShouldReturnTrue() throws Exception {

        Person person = new Person();

        person.setName("Test");
        person.setSurname("Size less than 48 and more than 2");
        person.setDate_of_birth("Test");

        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        assertTrue(violations.isEmpty());


    }


    @Test
    public void getUserByIdSizeOfPersonSurNameIsNullShouldReturnTrue() throws Exception {

        Person person = new Person();

        person.setName("Test");
        person.setSurname(null);
        person.setDate_of_birth("Test");

        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        assertFalse(violations.isEmpty());


    }


    @Test
    public void getUserByIdSizeOfPersonBirthdayInRangeValueShouldReturnTrue() throws Exception {

        Person person = new Person();

        person.setName("Test1");
        person.setSurname("Test");
        person.setDate_of_birth("Correct");

        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        assertTrue(violations.isEmpty());


    }


    @Test
    public void getUserByIdSizeOfPersonBirthdayMoreThant10ShouldReturnTrue() throws Exception {

        Person person = new Person();

        person.setName("Test");
        person.setSurname("Test");
        person.setDate_of_birth("Size more than 10.");

        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        assertFalse(violations.isEmpty());


    }


    @Test
    public void getUserByIdSizeOfPersonBirthdayLessThan1ShouldReturnTrue() throws Exception {

        Person person = new Person();

        person.setName("Test");
        person.setSurname("surname");
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


    @Test
    public void getUserByIdTestWithInCorrectBirthDayShouldReturnFalse() throws Exception {

        Person person = new Person();

        person.setName("Test1");
        person.setSurname("Test1_2");
        person.setDate_of_birth("18 Years");


        when(personService.findById(anyLong())).thenReturn(java.util.Optional.of(person));


        AssertionError thrown = assertThrows(
                AssertionError.class,
                () -> mockMvc.perform(get("/person/9"))
                        .andExpect(jsonPath("$.name").value("Test1"))
                        .andExpect(jsonPath("$.surname").value("Test1_2"))
                        .andExpect(jsonPath("$.date_of_birth").value("not correct"))
                        .andExpect(status().isOk()),
                "Expected AssertionError when we try to assertEqual to 'not correct' field with Person object field"
        );

    }


    @Test
    public void getUserByIdTestWithInCorrectSurnameShouldReturnFalse() throws Exception {

        Person person = new Person();

        person.setName("Test1");
        person.setSurname("Test1_2");
        person.setDate_of_birth("18 Years");


        when(personService.findById(anyLong())).thenReturn(java.util.Optional.of(person));


        AssertionError thrown = assertThrows(
                AssertionError.class,
                () -> mockMvc.perform(get("/person/9"))
                        .andExpect(jsonPath("$.name").value("Test1"))
                        .andExpect(jsonPath("$.surname").value("not correct"))
                        .andExpect(jsonPath("$.date_of_birth").value("18 Years"))
                        .andExpect(status().isOk()),
                "Expected AssertionError when we try to assertEqual to 'not correct' field with Person object field"
        );

    }


    @Test
    public void getUserByIdTestWithInCorrectNameShouldReturnFalse() throws Exception {

        Person person = new Person();

        person.setId(9L);
        person.setName("Test1");
        person.setSurname("Test1_2");
        person.setDate_of_birth("18 Years");


        when(personService
                .findById(anyLong()))
                .thenReturn(Optional.of(person));

        AssertionError thrown = assertThrows(
                AssertionError.class,
                () -> mockMvc.perform(get("/person/9"))
                        .andExpect(jsonPath("$.name").value("not correct"))
                        .andExpect(jsonPath("$.surname").value("Test1_2"))
                        .andExpect(jsonPath("$.date_of_birth").value("18 Years"))
                        .andExpect(status().isOk()),
                "Expected AssertionError when we try to assertEqual to 'not correct' field with Person object field"
        );


    }


}
