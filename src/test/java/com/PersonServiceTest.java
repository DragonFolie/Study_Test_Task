package com;


import com.entity.Person;
import com.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(value = "/testApplication.properties")
public class PersonServiceTest {

    @MockBean
    private PersonService personService;



    @Autowired
    private MockMvc mockMvc;



    @Test
    public void shouldSaveToDataBase() throws Exception {

        Person person = new Person();
        person.setId(1L);
        person.setName("Test1");
        person.setSurname("Test1_2");
        person.setDate_of_birth("18 years" );

//        this.mockMvc
//                .perform(MockMvcRequestBuilders.put("/person/{name}",person))
//                .andDo(print())
//                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
//                .andDo(print());

        personService.addNewPerson(person);
        when(personService.findById(anyLong())).thenReturn(java.util.Optional.of(person));

        mockMvc.perform(MockMvcRequestBuilders.get("/person/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                 .andDo(print())
                 .andExpect(status().isOk());
    }

//    @Test
//    void should() throws Exception {
//
//        Person person = new Person();
//        person.setId(1L);
//        person.setName("Test");
//        person.setSurname("Test1");
//        person.setDate_of_birth("Size" );
//
//        List<Person> list = new ArrayList<>();
//        list.add(person);
//
//        when(personService
//                .findAllPerson())
//                .thenReturn(list);
//
//        this.mockMvc
//                .perform(MockMvcRequestBuilders.get("/person/"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.name()").value("Test"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.surname()").value("Test1"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.date_of_birth()").value("10 Year"));;
//
//
//
//    }
//
//    @Test
//    public void updatePersonTestShouldReturnTrue() throws Exception {
//
//        Person RECORD_1 = new Person();
//        RECORD_1.setId(1L);
//        RECORD_1.setName("Test1");
//        RECORD_1.setSurname("Test1_2");
//        RECORD_1.setDate_of_birth("22 Years");
//
//        Person person = new Person();
//        person.setId(5L);
//        person.setName("Test2");
//        person.setSurname("Test2_2");
//        person.setDate_of_birth("55 Years");
//
//
////        Mockito.when(personRepository.findById(RECORD_1.getId())).thenReturn(Optional.of(RECORD_1));
////        Mockito.when(personRepository.save(person)).thenReturn(person);
//
//     when(personService.findById(RECORD_1.getId()))
//
//
//    }


}
