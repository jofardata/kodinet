package net.kodinet.kodinet.controllers;

import net.kodinet.kodinet.entities.Asset;
import net.kodinet.kodinet.entities.Person;
import net.kodinet.kodinet.models.ApiResponse;
import net.kodinet.kodinet.repositories.AssetRepository;
import net.kodinet.kodinet.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Date;

@RestController
@RequestMapping("/assets")
public class AssetController {

    @Autowired
    AssetRepository assetRepository;
    @Autowired
    PersonRepository personRepository;
    ApiResponse apiResponse = new ApiResponse();

    @PostMapping("/create/{personId}")
    public ResponseEntity<?>create(@RequestBody Asset asset, @PathVariable Long personId){
        Person person = personRepository.getOne(personId);
        asset.setPerson(person);
        asset.setCreationDate(System.currentTimeMillis());
        asset.setCreatedOn(new Date());
        Asset created= assetRepository.save(asset);
        apiResponse.setResponseCode("00");
        apiResponse.setResponseMessage("Asset created");
        apiResponse.setData(created);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }

    @GetMapping("/find-one")
    public ResponseEntity<?>findOne(@PathVariable Long id){
        Asset asset=assetRepository.getOne(id);
        apiResponse.setResponseCode("00");
        apiResponse.setData(asset);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/find-by-category/{id}")
    public ResponseEntity<?>findByCategory(@PathVariable Long id){
        Collection<Asset> assets=assetRepository.findByAssetCategoryId(id);
        apiResponse.setResponseCode("00");
        apiResponse.setData(assets);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/find-by-person/{id}")
    public ResponseEntity<?>findByPerson(@PathVariable Long id){
        Collection<Asset> assets=assetRepository.findByPersonId(id);
        apiResponse.setResponseCode("00");
        apiResponse.setData(assets);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
