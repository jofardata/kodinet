package net.kodinet.kodinet.controllers;

import net.kodinet.kodinet.entities.Agent;
import net.kodinet.kodinet.models.ApiResponse;
import net.kodinet.kodinet.models.LoginObject;
import net.kodinet.kodinet.repositories.AgentRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agents")
public class AgentController {

    @Autowired
    AgentRepository agentRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    ApiResponse apiResponse = new ApiResponse();
    Logger LOGGER = LogManager.getLogger();
    @PostMapping("/create")
    public ResponseEntity<?>create(Agent agent){

        agent.setPassword(bCryptPasswordEncoder.encode(agent.getPassword()));
        agentRepository.save(agent);
        return new ResponseEntity<>("Agent created", HttpStatus.OK);
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
}
