package net.kodinet.kodinet.controllers;

import net.kodinet.kodinet.entities.Sector;
import net.kodinet.kodinet.models.ApiResponse;
import net.kodinet.kodinet.repositories.SectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/sector")
public class SectorController {
    @Autowired
    SectorRepository sectorRepository;

    ApiResponse apiResponse = new ApiResponse();

    @GetMapping("/read-all")
    public ResponseEntity<?> readAll(){
        apiResponse = new ApiResponse();

        try {
            List<Sector> sectors = sectorRepository.findAll();
            apiResponse.setResponseCode("00");
            apiResponse.setData(sectors);
        }catch (Exception ex){
            apiResponse.setResponseCode("01");
            apiResponse.setResponseMessage(ex.getMessage());
        }

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
