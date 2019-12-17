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
    public ResponseEntity<?> create(@RequestBody Embarkment embarkment, @PathVariable Long agentId) {
        Agent agent = agentRepository.getOne(agentId);
        embarkment.setCreatedOn(new Date());
        embarkment.setAgent(agent);
        apiResponse.setResponseCode("00");
        apiResponse.setResponseMessage("Embarkement created");
        embarkementRepository.save(embarkment);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/find-all")
    public ResponseEntity<?> findAll() {

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
    public Map<String, Object> taxAmounts(@PathVariable("agent-id") Long agentId) {
        return jdbcTemplate.queryForMap(
                "select " +
                        "(select sum(amount) from embarkments where (notefc is null or noteusd is null) and currency='FC' and agent_id=" + agentId + ") as fc," +
                        "(select sum(amount) from embarkments where (notefc is null or noteusd is null) and currency='USD' and agent_id=" + agentId + ") as usd"
        );
    }

    @PostMapping("/update-notes-to-embarquement/{agent-id}/{note-fc}/{note-usd}")
    public ResponseEntity<?> updateEmbarquement(@PathVariable("agent-id") Long agentId, @PathVariable("note-fc") String noteFC, @PathVariable("note-usd") String noteUsd) {
        apiResponse = new ApiResponse();
        try {
            jdbcTemplate.execute("update embarkments set notefc='" + noteFC + "',noteusd='" + noteUsd + "' where (notefc is null or noteusd is null) and agent_id=" + agentId);
            apiResponse.setResponseCode("00");
            apiResponse.setResponseMessage("Mise à jour effectué avec succès");
        } catch (Exception ex) {
            apiResponse.setResponseCode("01");
            apiResponse.setResponseMessage(ex.getMessage());
        }
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


    @GetMapping("/find-by_id_agent/{agent-id}")
    public ResponseEntity<?> findBydIdAgent(@PathVariable("agent-id") Long agentId) {
        apiResponse = new ApiResponse();
        apiResponse.setResponseCode("00");
        apiResponse.setData(embarkementRepository.select_embarquement_byIdAgent(agentId));

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }



    /*@DeleteMapping("/deleteAll")
    public ResponseEntity<?>deleteAll(){
        apiResponse = new ApiResponse();
        embarkementRepository.deleteAll();
        apiResponse.setData("Deleted");
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }*/


    //POUR UNE MANIPULATION TEMPORAIRE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        apiResponse = new ApiResponse();
        if (embarkementRepository.getOne(id) != null) {
            embarkementRepository.deleteById(id);
            apiResponse.setResponseCode("00");
            apiResponse.setResponseMessage("Data deleted");
        } else {
            embarkementRepository.deleteById(id);
            apiResponse.setResponseCode("01");
            apiResponse.setResponseMessage("Data not found");
        }
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


    //POUR UNE MANIPULATION TEMPORAIRE
    @DeleteMapping("/delete/{id1}/{id2}")
    public ResponseEntity<?> deleteIdMultiple(@PathVariable Long id1, @PathVariable Long id2) {

        for (long i = id1; i <= id2; i++) {
            embarkementRepository.deleteById(i);
        }
        apiResponse.setResponseCode("00");
        apiResponse.setResponseMessage("Data deleted");

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/find-by_note_usd/{note_usd}/{id_agent}")
    public ResponseEntity<?> findBydNoteUsd(@PathVariable("note_usd") String noteusd, @PathVariable("id_agent") Long id_agent) {
        apiResponse = new ApiResponse();
        apiResponse.setResponseCode("00");
        apiResponse.setData(embarkementRepository.select_embarquement_noteusd(noteusd, id_agent));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/find-count_by_note_usd/{note_usd}/{id_agent}")
    public ResponseEntity<?> findcountBydNoteUsd(@PathVariable("note_usd") String noteusd, @PathVariable("id_agent") Long id_agent) {
        apiResponse = new ApiResponse();
        apiResponse.setResponseCode("00");
        apiResponse.setData(embarkementRepository.findCountnoteusd(noteusd, id_agent));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/update-notes-to-null/{id}")
    public ResponseEntity<?> updateEmbarquementnull(@PathVariable("id") Long id) {
        apiResponse = new ApiResponse();
        try {
            jdbcTemplate.execute("update embarkments set notefc= null,noteusd=null where id=" + id);
            apiResponse.setResponseCode("00");
            apiResponse.setResponseMessage("Mise à jour effectué avec succès");
        } catch (Exception ex) {
            apiResponse.setResponseCode("01");
            apiResponse.setResponseMessage(ex.getMessage());
        }
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/update-notes-to-embarquement_notes/{agent-id}/{note-usd}/{note-usd_new}")
    public ResponseEntity<?> updateEmbarquement_notes(@PathVariable("agent-id") Long agentId, @PathVariable("note-usd") String noteusd, @PathVariable("note-usd_new") String noteUsd_new) {
        apiResponse = new ApiResponse();
        try {
            jdbcTemplate.execute("update embarkments set noteusd='" + noteUsd_new + "' where noteusd = '" + noteusd + "' and agent_id=" + agentId);
            apiResponse.setResponseCode("00");
            apiResponse.setResponseMessage("Mise à jour effectué avec succès");
        } catch (Exception ex) {
            apiResponse.setResponseCode("01");
            apiResponse.setResponseMessage(ex.getMessage());
        }
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/find-embarquements-without-note/{agent-id}")
    public ResponseEntity<?> findEmbarquementWithoutNote(@PathVariable("agent-id") Long agentId) {
        apiResponse = new ApiResponse();
        apiResponse.setResponseCode("00");
        apiResponse.setData(embarkementRepository.select_embarquement_whitout_note(agentId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


}
