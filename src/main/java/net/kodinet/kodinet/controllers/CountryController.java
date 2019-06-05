package net.kodinet.kodinet.controllers;

import net.kodinet.kodinet.entities.Country;
import net.kodinet.kodinet.models.ApiResponse;
import net.kodinet.kodinet.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/countries")
public class CountryController {

    @Autowired
    CountryRepository countryRepository;
    ApiResponse apiResponse = new ApiResponse();

    @PostMapping("/create")
    public ResponseEntity<?>create(@RequestBody Country country){

        countryRepository.save(country);
        apiResponse.setResponseCode("00");
        apiResponse.setData(countryRepository.save(country));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @GetMapping("/find-all")
    public ResponseEntity<?>findAll(){
        apiResponse.setData(countryRepository.findAll());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
