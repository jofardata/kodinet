package net.kodinet.kodinet.controllers;

import net.kodinet.kodinet.entities.DrivingLicense;
import net.kodinet.kodinet.models.ApiResponse;
import net.kodinet.kodinet.repositories.DrivingLicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/drivinglicenses")
public class DrivingLicenseController {

    @Autowired
    DrivingLicenseRepository drivingLicenseRepository;
    ApiResponse apiResponse = new ApiResponse();

    @PostMapping("/create")
    public ResponseEntity<?>save(@RequestBody DrivingLicense drivingLicense){

        drivingLicense.setCreatedOn(new Date());
       DrivingLicense drivingLicense1 = drivingLicenseRepository.save(drivingLicense);
        apiResponse.setResponseCode("00");
        apiResponse.setResponseMessage("Driving license created");
        apiResponse.setData(drivingLicense1);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<?>findAll(){
        apiResponse.setData(drivingLicenseRepository.findAll());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/filter-results/{start}/{end}/{town}")
    public ResponseEntity<?>filterResults(
            @PathVariable String start,
            @PathVariable String end,
            @PathVariable String town) throws ParseException {

        Date d1=new SimpleDateFormat("dd/MM/yyyy").parse(start);
        Date d2=new SimpleDateFormat("dd/MM/yyyy").parse(end);
        Long date1 = d1.getTime();
        Long date2 = d2.getTime();
        apiResponse.setData(drivingLicenseRepository.customResults(
                date1,date2,town
        ));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
