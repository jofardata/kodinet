package net.kodinet.kodinet.controllers;

import net.kodinet.kodinet.entities.Agent;
import net.kodinet.kodinet.entities.Airline;
import net.kodinet.kodinet.entities.Airport;
import net.kodinet.kodinet.entities.GoPass;
import net.kodinet.kodinet.models.ApiResponse;
import net.kodinet.kodinet.repositories.AgentRepository;
import net.kodinet.kodinet.repositories.AirlineRepository;
import net.kodinet.kodinet.repositories.AirportRepository;
import net.kodinet.kodinet.repositories.GopassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/gopasses")
public class GoPassController {
    @Autowired
    GopassRepository gopassRepository;
    ApiResponse apiResponse = new ApiResponse();
    @Autowired
    AgentRepository agentRepository;
    @Autowired
    AirportRepository airportRepository;
    @Autowired
    AirlineRepository airlineRepository;

    @PostMapping("/create/{agentId}/{departureAirport}/{arrivalAirport}/{airlineId}")
    public ResponseEntity<?>create(@RequestBody GoPass goPass,
                                   @PathVariable Long agentId,
                                   @PathVariable Long departureAirport,
                                   @PathVariable Long arrivalAirport,
                                   @PathVariable Long airlineId){
        Agent agent = agentRepository.getOne(agentId);
        Airport airportA = airportRepository.getOne(departureAirport);
        Airport aiportB = airportRepository.getOne(arrivalAirport);
        goPass.setDepartureAirport(airportA);
        goPass.setArrivalAirport(aiportB);
        goPass.setCreatedOn(new Date());
        goPass.setAgent(agent);
        Airline airline = airlineRepository.getOne(airlineId);
        goPass.setAirline(airline);
        apiResponse.setResponseCode("00");
        apiResponse.setResponseMessage("GoPass Created");
        apiResponse.setData(gopassRepository.save(goPass));

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/find-all")
    public ResponseEntity<?>findAll(){
        apiResponse.setResponseCode("00");
        apiResponse.setData(gopassRepository.findAll());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
