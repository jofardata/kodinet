package net.kodinet.kodinet.controllers;

import net.kodinet.kodinet.entities.Embarkment;
import net.kodinet.kodinet.models.ApiResponse;
import net.kodinet.kodinet.repositories.EmbarkementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/embarkements")
public class EmbarkementController {

    @Autowired
    EmbarkementRepository embarkementRepository;
    ApiResponse apiResponse = new ApiResponse();

    @PostMapping("/create")
    public ResponseEntity<?>create(@RequestBody Embarkment embarkment){
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
