package net.kodinet.kodinet.controllers;

import net.kodinet.kodinet.entities.AssetCategory;
import net.kodinet.kodinet.models.ApiResponse;
import net.kodinet.kodinet.repositories.AssetCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/assets-categories")
@CrossOrigin(origins = "*")
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
        apiResponse.setData(assetCategoryRepository.findAll());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    //FIND BY NAME
    //============
    @GetMapping("/find-by-name/{asset}")
    public ResponseEntity<?> findByName(@PathVariable("asset") String asset){
        apiResponse = new ApiResponse();
        try {
            AssetCategory assetCategory = assetCategoryRepository.findByName(asset);
            apiResponse.setResponseCode("00");
            apiResponse.setData(assetCategory);
        } catch (Exception ex){
            apiResponse.setResponseCode("01");
            apiResponse.setResponseMessage(ex.getMessage());
        }
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
