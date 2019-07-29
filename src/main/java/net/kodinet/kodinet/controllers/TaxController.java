package net.kodinet.kodinet.controllers;

import net.kodinet.kodinet.entities.FiscalAdministration;
import net.kodinet.kodinet.entities.Sector;
import net.kodinet.kodinet.entities.Tax;
import net.kodinet.kodinet.models.ApiResponse;
import net.kodinet.kodinet.repositories.FiscalAdministrationRepository;
import net.kodinet.kodinet.repositories.SectorRepository;
import net.kodinet.kodinet.repositories.TaxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sun.rmi.runtime.Log;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/tax")
public class TaxController {

    @Autowired
    TaxRepository taxRepository;

    @Autowired
    FiscalAdministrationRepository fiscalAdministrationRepository;

    @Autowired
    SectorRepository sectorRepository;

    ApiResponse apiResponse = new ApiResponse();

    @PostMapping("/insert/{fiscal-administration-id}/{sector-id}")
    public ResponseEntity<?> insert(@RequestBody Tax tax, @PathVariable("fiscal-administration-id") Long fiscalAdminId, @PathVariable("sector-id")Long sectorId){
        apiResponse = new ApiResponse();

        try {
            FiscalAdministration fiscalAdministration = fiscalAdministrationRepository.getOne(fiscalAdminId);
            Sector sector = sectorRepository.getOne(sectorId);
            tax.setFiscalAdministration(fiscalAdministration);
            tax.setSector(sector);
            taxRepository.save(tax);
            apiResponse.setResponseCode("00");
            apiResponse.setResponseMessage("Enregistrement effectué avec succès");
        } catch (Exception ex){
            apiResponse.setResponseCode("01");
            apiResponse.setResponseMessage(ex.getMessage());
        }

        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @GetMapping("/find-all")
    public ResponseEntity<?> readAll(){
        apiResponse = new ApiResponse();

        try {
            List<Tax> taxes = taxRepository.findAll();
            apiResponse.setResponseCode("00");
            apiResponse.setData(taxes);
        } catch (Exception ex){
            apiResponse.setResponseCode("01");
            apiResponse.setResponseMessage(ex.getMessage());
        }
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
