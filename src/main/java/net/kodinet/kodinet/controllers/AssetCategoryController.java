package net.kodinet.kodinet.controllers;

import net.kodinet.kodinet.entities.AssetCategory;
import net.kodinet.kodinet.models.ApiResponse;
import net.kodinet.kodinet.repositories.AssetCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/assets-categories")
public class AssetCategoryController {

    @Autowired
    AssetCategoryRepository assetCategoryRepository;
    ApiResponse apiResponse = new ApiResponse();
    @PostMapping("/create")
    public ResponseEntity<?>create(AssetCategory assetCategory){

        assetCategoryRepository.save(assetCategory);
        apiResponse.setResponseCode("00");
        apiResponse.setResponseMessage("category created");
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/read-all")
    public ResponseEntity<?>findAll(){
        return new ResponseEntity<>(assetCategoryRepository.findAll(), HttpStatus.OK);
    }
}
