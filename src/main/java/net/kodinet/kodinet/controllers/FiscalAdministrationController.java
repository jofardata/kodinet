package net.kodinet.kodinet.controllers;

import net.kodinet.kodinet.entities.FiscalAdministration;
import net.kodinet.kodinet.models.ApiResponse;
import net.kodinet.kodinet.repositories.FiscalAdministrationRepository;
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
@RequestMapping("/fiscal-administration")
public class FiscalAdministrationController {

    @Autowired
    FiscalAdministrationRepository fiscalAdministrationRepository;

    ApiResponse apiResponse = new ApiResponse();

    @GetMapping("/find-all")
    public ResponseEntity<?> ReadAll(){
        apiResponse = new ApiResponse();

        try {
            List<FiscalAdministration> fiscalAdministrations = fiscalAdministrationRepository.findAll();
            apiResponse.setResponseCode("00");
            apiResponse.setData(fiscalAdministrations);
        } catch (Exception ex){
            apiResponse.setResponseCode("01");
            apiResponse.setResponseMessage(ex.getMessage());
        }
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
