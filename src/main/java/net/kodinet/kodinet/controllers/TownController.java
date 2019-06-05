package net.kodinet.kodinet.controllers;

import net.kodinet.kodinet.entities.Town;
import net.kodinet.kodinet.models.ApiResponse;
import net.kodinet.kodinet.repositories.TownRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/towns")
public class TownController {
    @Autowired
    TownRepository townRepository;
    ApiResponse apiResponse = new ApiResponse();

    @PostMapping("/create")
    public ResponseEntity<?>create(@RequestBody Town town){

        apiResponse.setResponseCode("00");
        apiResponse.setData(townRepository.save(town));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/find-all")
    public ResponseEntity<?>findAll(){
        apiResponse.setData(townRepository.findAll());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
