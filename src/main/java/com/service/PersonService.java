package com.service;

import com.entity.Person;
import com.repository.PersonRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Component
@Service
@Data
@RequiredArgsConstructor
public class PersonService {

    private PersonRepository personRepository;



    @Autowired
    public void setPersonRepository(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAllPerson(){

        return  this.personRepository.findAll();


    }

    public Optional<Person> findById(Long id){

        return  personRepository.findById(id);
    }


    public Person addNewPerson(Person person){

        return personRepository.save(person);

    }

    public Person updatePerson(Person person){

         return personRepository.save(person);

    }


    public void deletePersonById(Long id){

        personRepository.deleteById(id);

    }











}
