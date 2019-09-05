package net.kodinet.kodinet.controllers;

import net.kodinet.kodinet.entities.Agent;
import net.kodinet.kodinet.entities.Airline;
import net.kodinet.kodinet.entities.Airport;
import net.kodinet.kodinet.entities.Embarkment;
import net.kodinet.kodinet.models.ApiResponse;
import net.kodinet.kodinet.repositories.AgentRepository;
import net.kodinet.kodinet.repositories.AirlineRepository;
import net.kodinet.kodinet.repositories.AirportRepository;
import net.kodinet.kodinet.repositories.EmbarkementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/embarkements")
public class EmbarkementController {

    @Autowired
    EmbarkementRepository embarkementRepository;
    @Autowired
    AgentRepository agentRepository;
    ApiResponse apiResponse = new ApiResponse();
    @Autowired
    AirportRepository airportRepository;
    @Autowired
    AirlineRepository airlineRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/create/{agentId}")
    public ResponseEntity<?>create(@RequestBody Embarkment embarkment,@PathVariable Long agentId)
                                   {
        Agent agent = agentRepository.getOne(agentId);
        embarkment.setCreatedOn(new Date());
        embarkment.setAgent(agent);
        apiResponse.setResponseCode("00");
        apiResponse.setResponseMessage("Embarkement created");
        embarkementRepository.save(embarkment);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/find-all")
    public ResponseEntity<?>findAll(){

        apiResponse.setResponseCode("00");
        apiResponse.setData(embarkementRepository.findAll());

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/pages")
    public ResponseEntity<?> findPagedData(@RequestParam int page, @RequestParam int size) {

        PageRequest pageable = PageRequest.of(page, size);
        apiResponse.setData(embarkementRepository.findPagedData(pageable));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/pages/dates")
    public ResponseEntity<?> findBetweenDates(@RequestParam Long date1,
                                              @RequestParam Long date2,
                                              @RequestParam int page,
                                              @RequestParam int size) {
        PageRequest pageable = PageRequest.of(page, size);
        apiResponse.setData(embarkementRepository.findBetweenDates(new Date(date1),
                new Date(date2), pageable));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/tax-amounts/{agent-id}")
    public Map<String,Object> taxAmounts(@PathVariable("agent-id") Long agentId){
        return jdbcTemplate.queryForMap(
                "select " +
                        "(select sum(amount) from embarkments where (notefc is null or noteusd is null) and currency='FC' and agent_id=" + agentId + ") as fc," +
                        "(select sum(amount) from embarkments where (notefc is null or noteusd is null) and currency='USD' and agent_id=" + agentId + ") as usd"
        );
    }

    @PostMapping("/update-notes-to-embarquement/{agent-id}/{note-fc}/{note-usd}")
    public ResponseEntity<?> updateEmbarquement(@PathVariable("agent-id") Long agentId,@PathVariable("note-fc") String noteFC,@PathVariable("note-usd") String noteUsd){
        apiResponse = new ApiResponse();
        try {
            jdbcTemplate.execute("update embarkments set notefc='" + noteFC + "',noteusd='" + noteUsd + "' where (notefc is null or noteusd is null) and agent_id=" + agentId);
            apiResponse.setResponseCode("00");
            apiResponse.setResponseMessage("Mise à jour effectué avec succès");
        } catch (Exception ex){
            apiResponse.setResponseCode("01");
            apiResponse.setResponseMessage(ex.getMessage());
        }
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }



    @GetMapping("/countleo/{agent-id}")
    public Map<String,Object> countleo(@PathVariable("agent-id") Long agentId){
        return jdbcTemplate.queryForMap(
                "select " +
                        "(select count (id) from embarkments where notefc is null  and currency='FC' and agent_id=" + agentId + ") as countfc," +
                        "(select count (id) from embarkments where  noteusd is null and currency='USD' and agent_id=" + agentId + ") as countusd"
        );
    }


    @DeleteMapping("/delete_lelo_id/{agent-id}")
    public ResponseEntity<?> delete_lelo_id(@PathVariable("agent-id") Long agentId){
        apiResponse = new ApiResponse();
        try {
            apiResponse = new ApiResponse();
            jdbcTemplate.execute("delete from embarkments WHERE id in (select * from embarkments where noteusd is null and  currency='USD' and agent_id = " + agentId + " limit 171)");
            apiResponse.setData("Supression reussie");
        } catch (Exception ex){
            apiResponse.setResponseCode("01");
            apiResponse.setResponseMessage(ex.getMessage());
        }
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }


    /*@DeleteMapping("/deleteAll")
    public ResponseEntity<?>deleteAll(){
        apiResponse = new ApiResponse();
        embarkementRepository.deleteAll();
        apiResponse.setData("Deleted");
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }*/

   /* @DeleteMapping("/delete/{id}")
    public ResponseEntity<?>delete(@PathVariable Long id){

        apiResponse = new ApiResponse();
        if (embarkementRepository.getOne(id)!=null){
            embarkementRepository.deleteById(id);
            apiResponse.setResponseCode("00");
            apiResponse.setResponseMessage("Data deleted");
        }else {
            embarkementRepository.deleteById(id);
            apiResponse.setResponseCode("01");
            apiResponse.setResponseMessage("Data not found");
        }

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }*/

}
