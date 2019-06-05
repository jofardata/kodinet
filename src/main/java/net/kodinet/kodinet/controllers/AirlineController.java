package net.kodinet.kodinet.controllers;

import net.kodinet.kodinet.entities.Airline;
import net.kodinet.kodinet.models.ApiResponse;
import net.kodinet.kodinet.repositories.AirlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/airlines")
public class AirlineController {

    @Autowired
    AirlineRepository airlineRepository;
    ApiResponse apiResponse = new ApiResponse();

    @PostMapping("/create")
    public ResponseEntity<?>create(@RequestBody Airline airline){
        apiResponse.setResponseCode("00");
        apiResponse.setData(airlineRepository.save(airline));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @GetMapping("/find-all")
    public ResponseEntity<?>findAll(){
        apiResponse.setResponseCode("00");
        apiResponse.setData(airlineRepository.findAll());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
