package net.kodinet.kodinet.controllers;

import net.kodinet.kodinet.entities.Province;
import net.kodinet.kodinet.entities.Town;
import net.kodinet.kodinet.models.ApiResponse;
import net.kodinet.kodinet.repositories.ProvinceRepository;
import net.kodinet.kodinet.repositories.TownRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/towns")
public class TownController {
    @Autowired
    TownRepository townRepository;
    @Autowired
    ProvinceRepository provinceRepository;
    ApiResponse apiResponse = new ApiResponse();

    @PostMapping("/create/{provinceId}")
    public ResponseEntity<?>create(@RequestBody Town town,
                                   Long provinceId){

        Province province = provinceRepository.getOne(provinceId);
        town.setProvince(province);
        apiResponse.setResponseCode("00");
        apiResponse.setData(townRepository.save(town));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/find-all")
    public ResponseEntity<?>findAll(){
        apiResponse.setData(townRepository.findAll());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/findByProvince/{provinceId}")
    public ResponseEntity<?>findByProvinceId(@PathVariable Long provinceId){
        apiResponse.setData(townRepository.findByProvinceId(provinceId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
