package net.kodinet.kodinet.controllers;

import net.kodinet.kodinet.entities.Ministry;
import net.kodinet.kodinet.models.ApiResponse;
import net.kodinet.kodinet.repositories.MinistryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("/ministry")
@RestController
public class MinistryController {
    @Autowired
    MinistryRepository ministryRepository;

    ApiResponse apiResponse = new ApiResponse();

    @GetMapping("/find-all")
    public ResponseEntity<?> readAll(){
        apiResponse.setResponseCode("00");
        List<Ministry> ministries = ministryRepository.findAll();
        apiResponse.setData(ministries);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
