package net.kodinet.kodinet.controllers;

import net.kodinet.kodinet.entities.DrivingLicense;
import net.kodinet.kodinet.models.ApiResponse;
import net.kodinet.kodinet.repositories.DrivingLicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/drivinglicenses")
public class DrivingLicenseController {

    @Autowired
    DrivingLicenseRepository drivingLicenseRepository;
    @Autowired
    JdbcTemplate jdbcTemplate;
    ApiResponse apiResponse = new ApiResponse();

    @PostMapping("/create")
    public ResponseEntity<?>save(@RequestBody DrivingLicense drivingLicense){

        Calendar cal = Calendar.getInstance();
        drivingLicense.setCreatedOn(cal.getTime());
        cal.add(Calendar.YEAR, 1); //
        drivingLicense.setDateExp(cal.getTime());
        DrivingLicense drivingLicense1 = drivingLicenseRepository.save(drivingLicense);
        apiResponse.setResponseCode("00");
        apiResponse.setResponseMessage("Driving license created");
        apiResponse.setData(drivingLicense1);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<?>findAll(){
        apiResponse = new ApiResponse();
        apiResponse.setData(drivingLicenseRepository.findAll());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/filter-results/{start}/{end}/{town}")
    public ResponseEntity<?>filterResults(
            @PathVariable String start,
            @PathVariable String end,
            @PathVariable String town) throws ParseException {

        Date d1=new SimpleDateFormat("dd/MM/yyyy").parse(start);
        Date d2=new SimpleDateFormat("dd/MM/yyyy").parse(end);
        Long date1 = d1.getTime();
        Long date2 = d2.getTime();
        apiResponse.setData(drivingLicenseRepository.customResults(
                date1,date2,town
        ));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/pages")
    public ResponseEntity<?>findPagedData(@RequestParam int page,@RequestParam int size){

        PageRequest pageable = PageRequest.of(page,size);
        apiResponse.setData(drivingLicenseRepository.findPagedData(pageable));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @GetMapping("/pages/dates")
    public ResponseEntity<?>findBetweenDates(@RequestParam Long date1,
                                             @RequestParam Long date2,
                                             @RequestParam int page,
                                             @RequestParam int size){
        PageRequest pageable = PageRequest.of(page,size);
        apiResponse.setData(drivingLicenseRepository.findBetweenDates(new Date(date1),
                new Date(date2), pageable));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/permis_not_printed/{town}")
    public ResponseEntity<?>permis_not_printed(
            @PathVariable String town){
        apiResponse = new ApiResponse();
        apiResponse.setData(drivingLicenseRepository.permis_not_printed(town));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/update_printed_drivingLicence/{id}")
    public ResponseEntity<?>update_printed_permis(@PathVariable("id") Long id){

        apiResponse = new ApiResponse();
        try {
            jdbcTemplate.execute("UPDATE driving_licenses set printed = true where id =" + id);
            apiResponse.setResponseCode("00");
            apiResponse.setResponseMessage("Printed successfully");
        } catch (Exception ex){
            apiResponse.setResponseCode("01");
            apiResponse.setResponseMessage(ex.getMessage());
        }
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<?>deleteAll(){
        apiResponse = new ApiResponse();
        drivingLicenseRepository.deleteAll();
        apiResponse.setData("Deleted");
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/tax-amounts/{bdn-id-agent}")
    public Map<String,Object> taxAmounts(@PathVariable("bdn-id-agent") String agentId){
        return jdbcTemplate.queryForMap(
                "select " +
                        "(select sum(amount) from driving_licenses where (notefc is null or noteusd is null) and currency='FC' and bdn_id_agent='" + agentId + "') as fc," +
                        "(select sum(amount) from driving_licenses where (notefc is null or noteusd is null) and currency='USD' and bdn_id_agent='" + agentId + "') as usd"
        );
    }

    @PostMapping("/update-notes-to-driving-license/{bdn-id-agent}/{note-fc}/{note-usd}")
    public ResponseEntity<?> updateDrivingLicense(@PathVariable("bdn-id-agent") String agentId,@PathVariable("note-fc") String noteFC,@PathVariable("note-usd") String noteUsd){
        apiResponse = new ApiResponse();
        try {
            jdbcTemplate.execute("update driving_licenses set notefc='" + noteFC + "',noteusd='" + noteUsd + "' where (notefc is null or noteusd is null) and bdn_id_agent='" + agentId + "'");
            apiResponse.setResponseCode("00");
            apiResponse.setResponseMessage("Mise à jour effectué avec succès");
        } catch (Exception ex){
            apiResponse.setResponseCode("01");
            apiResponse.setResponseMessage(ex.getMessage());
        }
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }
}
