package net.kodinet.kodinet.controllers;

import net.kodinet.kodinet.entities.Agent;
import net.kodinet.kodinet.entities.Freight;
import net.kodinet.kodinet.models.ApiResponse;
import net.kodinet.kodinet.repositories.AgentRepository;
import net.kodinet.kodinet.repositories.FreightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/freights")
public class FreightController {

    @Autowired
    FreightRepository freightRepository;
    ApiResponse apiResponse = new ApiResponse();
    @Autowired
    AgentRepository agentRepository;

    @PostMapping("/create/{agentId}")
    public ResponseEntity<?>create(@RequestBody Freight freight,@PathVariable Long agentId){

        Agent agent = agentRepository.getOne(agentId);
        freight.setAgent(agent);
        freightRepository.save(freight);
        apiResponse.setResponseCode("00");
        apiResponse.setResponseMessage("Agent created");

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/find-all")
    public ResponseEntity<?>findAll(){
        apiResponse.setResponseCode("00");
        apiResponse.setData(freightRepository.findAll());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
