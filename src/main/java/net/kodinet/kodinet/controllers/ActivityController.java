package net.kodinet.kodinet.controllers;

import net.kodinet.kodinet.entities.Activity;
import net.kodinet.kodinet.models.ApiResponse;
import net.kodinet.kodinet.repositories.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/activities")
@CrossOrigin(origins = "*")
public class ActivityController {

    @Autowired
    ActivityRepository activityRepository;
    ApiResponse apiResponse = new ApiResponse();

    @PostMapping("/create")
    public ResponseEntity<?>create(@RequestBody Activity activity){
        activity.setCreatedAt(System.currentTimeMillis());
        activity.setCreatedOn(new Date());
        activityRepository.save(activity);
        apiResponse.setResponseCode("00");
        apiResponse.setResponseMessage("activity created");
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<?>findAll(){
        apiResponse.setResponseCode("00");
        apiResponse.setData(activityRepository.findAll());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
