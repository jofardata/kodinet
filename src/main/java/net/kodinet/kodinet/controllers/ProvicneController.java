package net.kodinet.kodinet.controllers;

import net.kodinet.kodinet.entities.Country;
import net.kodinet.kodinet.entities.Province;
import net.kodinet.kodinet.models.ApiResponse;
import net.kodinet.kodinet.repositories.CountryRepository;
import net.kodinet.kodinet.repositories.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/provinces")
public class ProvicneController {

    @Autowired
    ProvinceRepository provinceRepository;
    @Autowired
    CountryRepository countryRepository;
    ApiResponse apiResponse = new ApiResponse();

    @PostMapping("/create/{countryId}")
    public ResponseEntity<?>create(@RequestBody Province province,@PathVariable Long countryId){
        Country country = countryRepository.getOne(countryId);
        province.setCountry(country);
        apiResponse.setResponseCode("00");
        apiResponse.setResponseMessage("Province created");
        apiResponse.setData(provinceRepository.save(province));

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }
    @GetMapping("/findAll")
    public ResponseEntity<?>findAll(){
        apiResponse.setResponseCode("00");
        apiResponse.setData(provinceRepository.findAll());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @GetMapping("/findByCountry/{countryId}")
    public ResponseEntity<?>findByCountryId(@PathVariable Long countryId){
        apiResponse.setData(provinceRepository.findByCountryId(countryId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
