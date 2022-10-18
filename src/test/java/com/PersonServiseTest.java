package com;


import com.entity.Person;
import com.service.PersonService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(PersonControllerTest.class)
public class PersonServiseTest {

    @MockBean
    private PersonService personService;


    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void should() throws Exception {
//        when(serviceMock.register(employeeForRegister)).thenReturn(successfullyRegisteredEmployee);
//        when(serviceMock.register(any())).thenReturn(successfullyRegisteredEmployee);

        when(personService.findAllPerson()).thenReturn(List.of(new Person("Test1","Test11","18 y.o")));

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/person/")
                .header("X-Foo","test"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name()").value("Test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname()").value("Test1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.date_of_birth()").value("10 Year"));;



    }
}
