package net.kodinet.kodinet.controllers;

import net.kodinet.kodinet.entities.Agent;
import net.kodinet.kodinet.entities.Airline;
import net.kodinet.kodinet.entities.Airport;
import net.kodinet.kodinet.entities.Embarkment;
import net.kodinet.kodinet.models.ApiResponse;
import net.kodinet.kodinet.repositories.AgentRepository;
import net.kodinet.kodinet.repositories.AirlineRepository;
import net.kodinet.kodinet.repositories.AirportRepository;
import net.kodinet.kodinet.repositories.EmbarkementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/embarkements")
public class EmbarkementController {

    @Autowired
    EmbarkementRepository embarkementRepository;
    @Autowired
    AgentRepository agentRepository;
    ApiResponse apiResponse = new ApiResponse();
    @Autowired
    AirportRepository airportRepository;
    @Autowired
    AirlineRepository airlineRepository;
    @PostMapping("/create")
    public ResponseEntity<?>create(@RequestBody Embarkment embarkment)
                                   {
        embarkment.setCreatedOn(new Date());
        apiResponse.setResponseCode("00");
        apiResponse.setResponseMessage("Embarkement created");
        embarkementRepository.save(embarkment);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/find-all")
    public ResponseEntity<?>findAll(){

        apiResponse.setResponseCode("00");
        apiResponse.setData(embarkementRepository.findAll());

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
