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
import org.springframework.data.domain.PageRequest;
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

    @PostMapping("/create")
    public ResponseEntity<?>create(@RequestBody GoPass goPass){
        goPass.setCreatedOn(new Date());
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

    @GetMapping("/pages")
    public ResponseEntity<?> findPagedData(@RequestParam int page, @RequestParam int size) {

        PageRequest pageable = PageRequest.of(page, size);
        apiResponse.setData(gopassRepository.findPagedData(pageable));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/pages/dates")
    public ResponseEntity<?> findBetweenDates(@RequestParam Long date1,
                                              @RequestParam Long date2,
                                              @RequestParam int page,
                                              @RequestParam int size) {
        PageRequest pageable = PageRequest.of(page, size);
        apiResponse.setData(gopassRepository.findBetweenDates(new Date(date1),
                new Date(date2), pageable));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
