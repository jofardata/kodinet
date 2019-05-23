package net.kodinet.kodinet.controllers;

import net.kodinet.kodinet.constants.ConstantsVariables;
import net.kodinet.kodinet.entities.Person;
import net.kodinet.kodinet.models.ApiResponse;
import net.kodinet.kodinet.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    PersonRepository personRepository;

    ApiResponse apiResponse = new ApiResponse();

    @PostMapping("/create")
    public ResponseEntity<?> create(Person person){

        Person person1 = personRepository.save(person);
        apiResponse.setResponseCode(ConstantsVariables.successCode);
        apiResponse.setResponseMessage("user registered");
        apiResponse.setData(person1);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
