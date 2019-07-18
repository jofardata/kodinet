package net.kodinet.kodinet.controllers;

import net.kodinet.kodinet.entities.Combination;
import net.kodinet.kodinet.models.ApiResponse;
import net.kodinet.kodinet.repositories.CombinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/combination")
@CrossOrigin(origins = "*")
public class CombinationController {
    @Autowired
    CombinationRepository combinationRepository;
    ApiResponse apiResponse = new ApiResponse();

    @PostMapping("/insert")
    public ResponseEntity<?> insert(@RequestBody Combination combination){
        apiResponse = new ApiResponse();
        Combination created = combinationRepository.save(combination);
        apiResponse.setResponseCode("00");
        apiResponse.setResponseMessage("combination created");
        apiResponse.setData(created);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/read-all")
    public ResponseEntity<?> readAll(){
        Collection<Combination> combinations = combinationRepository.findAll();
        apiResponse.setResponseCode("00");
        apiResponse.setData(combinations);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
