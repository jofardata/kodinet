package net.kodinet.kodinet.controllers;

import net.kodinet.kodinet.entities.DrivingLicense;
import net.kodinet.kodinet.entities.DrivingRfid;
import net.kodinet.kodinet.models.ApiResponse;
import net.kodinet.kodinet.repositories.DrivingrfidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;

@RestController
@RequestMapping("/drivingrfid")

public class DrivingrfidController {

    @Autowired
    DrivingrfidRepository drivingrfidRepository;
    ApiResponse apiResponse;

    @PostMapping("/createrfid")
    public ResponseEntity<?> save(@RequestBody DrivingRfid drivingRfid){
        apiResponse = new ApiResponse();
        Calendar cal = Calendar.getInstance();
        drivingRfid.setDateLivraison(cal.getTime());
        DrivingRfid drivingLicense1 = drivingrfidRepository.save(drivingRfid);
        apiResponse.setResponseCode("00");
        apiResponse.setResponseMessage("Rfid created");
        apiResponse.setData(drivingLicense1);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<?>findAll(){
        apiResponse = new ApiResponse();
        apiResponse.setData(drivingrfidRepository.findAll());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
