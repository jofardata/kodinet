package net.kodinet.kodinet.controllers;

import net.kodinet.kodinet.entities.Agent;
import net.kodinet.kodinet.entities.Airline;
import net.kodinet.kodinet.entities.Airport;
import net.kodinet.kodinet.entities.Freight;
import net.kodinet.kodinet.models.ApiResponse;
import net.kodinet.kodinet.repositories.AgentRepository;
import net.kodinet.kodinet.repositories.AirlineRepository;
import net.kodinet.kodinet.repositories.AirportRepository;
import net.kodinet.kodinet.repositories.FreightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/freights")
public class FreightController {

    @Autowired
    FreightRepository freightRepository;
    ApiResponse apiResponse = new ApiResponse();
    @Autowired
    AgentRepository agentRepository;
    @Autowired
    AirportRepository airportRepository;
    @Autowired
    AirlineRepository airlineRepository;

    @PostMapping("/create")
    public ResponseEntity<?>create(@RequestBody Freight freight){
        freight.setCreatedOn(new Date());
        freightRepository.save(freight);
        apiResponse.setResponseCode("00");
        apiResponse.setResponseMessage("Freight created");

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/find-all")
    public ResponseEntity<?>findAll(){
        apiResponse.setResponseCode("00");
        apiResponse.setData(freightRepository.findAll());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
