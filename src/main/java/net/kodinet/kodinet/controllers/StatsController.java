package net.kodinet.kodinet.controllers;

import net.kodinet.kodinet.models.ApiResponse;
import net.kodinet.kodinet.models.Stats;
import net.kodinet.kodinet.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stats")
public class StatsController {

    @Autowired
    AgentRepository agentRepository;
    @Autowired
    AssetRepository assetRepository;
    @Autowired
    DrivingLicenseRepository drivingLicenseRepository;
    @Autowired
    EmbarkementRepository embarkementRepository;
    @Autowired
    FreightRepository freightRepository;
    @Autowired
    GopassRepository gopassRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    VignetteRepository vignetteRepository;
    ApiResponse apiResponse = new ApiResponse();

    @GetMapping("/findAll")
    public ResponseEntity<?>findAll(){

        Stats stats = new Stats();
        stats.setTotalAgent(agentRepository.findCount());
        stats.setTotalAsset(assetRepository.findCount());
        stats.setTotalDrvingLicense(drivingLicenseRepository.findCount());
        stats.setTotalBoarding(embarkementRepository.findCount());
        stats.setTotalFreight(freightRepository.findCount());
        stats.setTotalGopass(gopassRepository.findCount());
        stats.setTotalPerson(personRepository.findCount());
        stats.setTotalVignette(vignetteRepository.findCount());
        apiResponse.setData(stats);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
