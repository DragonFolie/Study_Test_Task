package com.controller;


import com.entity.Person;
import com.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping(value = "/person")
@Validated
@Controller
public  class PersonController {


    private PersonService personService;

    @Autowired
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping(path = "/")
    @ResponseStatus(HttpStatus.OK)
    public List<Person> findAll(){

        List<Person> list= personService.findAllTeam();
        if (list.isEmpty()){
            throw new NoSuchElementException("No such person founded in list of persons ");
        }

        return list;

    }



    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Person> findById(@PathVariable("id")Long id){

        Optional<Person> list= personService.findById(id);
        if (list.isEmpty()){
            throw new NoSuchElementException("No such person founded in list of persons ");
        }

        return list;


    }



    @PostMapping(path = "/")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Person> addNewPerson(@Valid @RequestBody Person person ){

//        personService.addNewPerson(person);
//        return ResponseEntity.ok().build() ;
        return new ResponseEntity<>(personService.addNewPerson(person), HttpStatus.CREATED);

    }



    @PutMapping(path = "/")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Person> updatePerson(@Valid @RequestBody Person person){

        personService.updatePerson(person);
        return ResponseEntity.ok().build() ;

    }


    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Person> deletePersonById(@PathVariable("id")Long id){

        personService.deletePersonById(id);
        return ResponseEntity.ok().build() ;

    }









}
