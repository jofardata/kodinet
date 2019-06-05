package net.kodinet.kodinet.controllers;

import net.kodinet.kodinet.entities.Airport;
import net.kodinet.kodinet.entities.Town;
import net.kodinet.kodinet.models.ApiResponse;
import net.kodinet.kodinet.repositories.AirportRepository;
import net.kodinet.kodinet.repositories.TownRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/airports")
public class AirportController {

    @Autowired
    AirportRepository airportRepository;
    @Autowired
    TownRepository townRepository;
    ApiResponse apiResponse = new ApiResponse();

    @PostMapping("/create/{townId}")
    public ResponseEntity<?>create(@RequestBody Airport airport,@PathVariable Long townId){

        Town town = townRepository.getOne(townId);
        airport.setTown(town);
        airportRepository.save(airport);
        apiResponse.setResponseCode("00");
        apiResponse.setResponseMessage("Airport Created");
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @GetMapping("find-all")
    public ResponseEntity<?>findAll(){
        apiResponse.setData(airportRepository.findAll());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
