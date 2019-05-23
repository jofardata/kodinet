package net.kodinet.kodinet.controllers;

import net.kodinet.kodinet.constants.ConstantsVariables;
import net.kodinet.kodinet.entities.Person;
import net.kodinet.kodinet.models.ApiResponse;
import net.kodinet.kodinet.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

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

    @GetMapping("/read-all")
    public ResponseEntity<?>read(@RequestParam int page,@RequestParam int size){
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC,"id"));
        Pageable pageable = new PageRequest(page,size,sort);
        Collection<Person>people = personRepository.findAll(pageable).getContent();
        apiResponse.setResponseCode(ConstantsVariables.successCode);
        apiResponse.setResponseMessage("data found");
        apiResponse.setData(people);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/read-one/{id}")
    public ResponseEntity<?>readOne(@PathVariable Long id){
        Person person = personRepository.getOne(id);
        apiResponse.setResponseCode(ConstantsVariables.successCode);
        apiResponse.setResponseMessage("data found");
        apiResponse.setData(person);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/read-by-input/{input}")
    public ResponseEntity<?>readByInput(@PathVariable String input){

        Person person = personRepository.findByBdnIdOrNationalIdOrPhoneOrEmail(input);
        if (person!=null){
            apiResponse.setResponseCode(ConstantsVariables.successCode);
            apiResponse.setResponseMessage("data found");
            apiResponse.setData(person);
        }else{
            apiResponse.setResponseCode(ConstantsVariables.errorCode);
            apiResponse.setResponseMessage("data not found");
        }
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
