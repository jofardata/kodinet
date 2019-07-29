package net.kodinet.kodinet.controllers;

import net.kodinet.kodinet.entities.AssetCategory;
import net.kodinet.kodinet.entities.AssetsCategoriesTaxes;
import net.kodinet.kodinet.entities.Tax;
import net.kodinet.kodinet.models.ApiResponse;
import net.kodinet.kodinet.repositories.AssetCategoryRepository;
import net.kodinet.kodinet.repositories.AssetsCategoriesTaxesRepository;
import net.kodinet.kodinet.repositories.TaxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/asset-category-tax")
public class AssetsCategoriesTaxesController {

    @Autowired
    AssetsCategoriesTaxesRepository assetsCategoriesTaxesRepository;

    @Autowired
    AssetCategoryRepository assetCategoryRepository;

    @Autowired
    TaxRepository taxRepository;

    ApiResponse apiResponse = new ApiResponse();

    @PostMapping("/insert/{asset-category-id}/{tax-id}")
    public ResponseEntity<?> insert(@RequestBody AssetsCategoriesTaxes assetsCategoriesTaxes, @PathVariable("asset-category-id") Long assetCatId, @PathVariable("tax-id") String taxId){
        apiResponse = new ApiResponse();

        try {
            assetsCategoriesTaxes.setIdAssetCat(assetCatId);
            assetsCategoriesTaxes.setIdTax(taxId);
            AssetCategory assetCategory = assetCategoryRepository.getOne(assetCatId);
            Tax tax = taxRepository.getOne(taxId);
            assetsCategoriesTaxes.setAssetCategory(assetCategory);
            assetsCategoriesTaxes.setTax(tax);
            assetsCategoriesTaxesRepository.save(assetsCategoriesTaxes);
            apiResponse.setResponseCode("00");
            apiResponse.setResponseMessage("Enregistrement effectué avec succès");
        } catch (Exception ex){
            apiResponse.setResponseCode("01");
            apiResponse.setResponseMessage(ex.getMessage());
        }

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/read-all")
    public ResponseEntity<?> readAll(){
        apiResponse = new ApiResponse();
        try {
            List<AssetsCategoriesTaxes> assetsCategoriesTaxesList = assetsCategoriesTaxesRepository.findAll();
            apiResponse.setResponseCode("00");
            apiResponse.setData(assetsCategoriesTaxesList);
        } catch (Exception ex){
            apiResponse.setResponseCode("01");
            apiResponse.setResponseMessage(ex.getMessage());
        }
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
