package net.kodinet.kodinet.controllers;

import net.kodinet.kodinet.entities.ExchangeRate;
import net.kodinet.kodinet.models.ApiResponse;
import net.kodinet.kodinet.repositories.ExchangeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exchange-rate")
public class ExchangeRateController {
    @Autowired
    ExchangeRateRepository exchangeRateRepository;

    ApiResponse apiResponse = new ApiResponse();

    @PostMapping("/insert")
    public ResponseEntity<?> insert(@RequestBody ExchangeRate exchangeRate){
        apiResponse = new ApiResponse();
        exchangeRateRepository.deleteAll();
        ExchangeRate created = exchangeRateRepository.save(exchangeRate);
        apiResponse.setResponseCode("00");
        apiResponse.setResponseMessage("Nouveau taux ajouté");
        apiResponse.setData(created);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/get-rate")
    public ResponseEntity<?> read(){
        apiResponse = new ApiResponse();
        apiResponse.setResponseCode("00");
        apiResponse.setData(exchangeRateRepository.findFirst());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
