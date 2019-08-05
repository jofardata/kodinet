package net.kodinet.kodinet.controllers;

import net.kodinet.kodinet.entities.Admin;
import net.kodinet.kodinet.entities.Agent;
import net.kodinet.kodinet.entities.Entite;
import net.kodinet.kodinet.entities.FiscalEntity;
import net.kodinet.kodinet.models.ApiResponse;
import net.kodinet.kodinet.models.LoginObject;
import net.kodinet.kodinet.repositories.AdminRepository;
import net.kodinet.kodinet.repositories.AgentRepository;
import net.kodinet.kodinet.repositories.EntityRepository;
import net.kodinet.kodinet.repositories.FiscalEntityRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agents")
@CrossOrigin(origins = "*")
public class AgentController {

    @Autowired
    AgentRepository agentRepository;
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    EntityRepository entityRepository;

    @Autowired
    FiscalEntityRepository fiscalEntityRepository;

    ApiResponse apiResponse = new ApiResponse();
    Logger LOGGER = LogManager.getLogger();
    @PostMapping("/create/{adminId}/{entityId}")
    public ResponseEntity<?>create(@RequestBody Agent agent,
                                   @PathVariable Long adminId,
                                   @PathVariable Long entityId){

        Admin admin = adminRepository.getOne(adminId);
//        Entite entite = entityRepository.getOne(entityId);
//        agent.setEntite(entite);
        FiscalEntity fiscalEntity = fiscalEntityRepository.getOne(entityId);
        agent.setEntite(fiscalEntity);
        agent.setCreatedBy(admin);
        agent.setPassword(bCryptPasswordEncoder.encode(agent.getPassword()));
        agentRepository.save(agent);
        apiResponse.setResponseCode("00");
        apiResponse.setResponseMessage("Agent Created");
        apiResponse.setData(agent);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<?>findAll(){

        apiResponse.setResponseCode("00");
        apiResponse.setData(agentRepository.findAll());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/findAllAsc")
    public ResponseEntity<?>findAllAsc(){

        apiResponse.setResponseCode("00");
        apiResponse.setData(agentRepository.findAllByOrderByNameAsc());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?>login(@RequestBody LoginObject loginObject){

        LOGGER.info("LOGIN_REQUEST "+loginObject.toString());
        Agent agent = agentRepository.findByUsername(loginObject.getUsername());

        if (agent!=null){
            if (bCryptPasswordEncoder.matches(loginObject.getPassword(), agent.getPassword())){
                apiResponse.setResponseCode("00");
                apiResponse.setResponseMessage("welcome");
                apiResponse.setData(agent);
            }else{
                apiResponse.setResponseCode("01");
                apiResponse.setResponseMessage("Wrong username or password");
            }
        }else{
            apiResponse.setResponseCode("01");
            apiResponse.setResponseMessage("Wrong username or password");
        }

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/findCount")
    public ResponseEntity<?>findCount(){
        apiResponse.setResponseCode("00");
        apiResponse.setData(agentRepository.findCount());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
