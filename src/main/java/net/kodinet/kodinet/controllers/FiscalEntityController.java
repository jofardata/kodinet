package net.kodinet.kodinet.controllers;

import net.kodinet.kodinet.entities.FiscalEntity;
import net.kodinet.kodinet.models.ApiResponse;
import net.kodinet.kodinet.repositories.FiscalEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/fiscalentity")
public class FiscalEntityController {
    @Autowired
    FiscalEntityRepository fiscalEntityRepository;
    ApiResponse apiResponse = new ApiResponse();

    @PostMapping("/insert")
    public ResponseEntity<?> insert(@RequestBody FiscalEntity fiscalEntity){
        fiscalEntityRepository.save(fiscalEntity);
        //return new ResponseEntity<>("Enregistrement effectué", HttpStatus.OK);
        apiResponse.setResponseCode("00");
        apiResponse.setResponseMessage("Enregistrement effectué");
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/find-entity/{type}")
    public ResponseEntity<?> findCenter(@PathVariable("type") String type){
        //apiResponse.setData(fiscalEntityRepository.findAll());
        apiResponse.setData(fiscalEntityRepository.findByEntityType(type));
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/find-all")
    public ResponseEntity<?> findAll(){
        apiResponse.setData(fiscalEntityRepository.findAll());
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }
}
