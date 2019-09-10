package net.kodinet.kodinet.controllers;

import net.kodinet.kodinet.entities.Admin;
import net.kodinet.kodinet.entities.Agent;
import net.kodinet.kodinet.entities.Entite;
import net.kodinet.kodinet.entities.FiscalEntity;
import net.kodinet.kodinet.models.ApiResponse;
import net.kodinet.kodinet.models.ChangePassword;
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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

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

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
        agent.setHasChangedPassword(false);
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

    @PostMapping("/change-id/{old-id}/{new-id}")
    public ResponseEntity<?> changeId(@PathVariable("old-id") Long oldId, @PathVariable("new-id") Long newId){
        apiResponse = new ApiResponse();
        try{
            jdbcTemplate.execute("update agents set id=" + newId +" where id=" + oldId);
            apiResponse.setResponseCode("00");
        }catch (Exception ex){
            apiResponse.setResponseCode("01");
            apiResponse.setResponseMessage(ex.getMessage());
        }
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    //CHANGEMENT DE MOT DE PASSE
    //==========================
    @PostMapping("/change-password")
    public ResponseEntity<?> ChangePassword(@RequestBody ChangePassword changePassword){
        apiResponse = new ApiResponse();
//        Agent user = userLoginRepository.findUserLoginByBdnId(changePassword.getBdnId());
        Agent agent = agentRepository.findByUsername(changePassword.getUsername());
        //Verifier si l'utilisateur existe
        if(agent==null){
            apiResponse.setResponseCode("01");
            apiResponse.setResponseMessage("user not found");
        }else {
            //Verifier si le nouveau mot de passe = à la confirmation
            if(changePassword.getNewPassword().equals(changePassword.getConfirmPassword())) {
                //Verifier si l'ancien mot de passe correspond au mot de passe qui se trouve dans la base
                if (!bCryptPasswordEncoder.matches(changePassword.getOldPassword(), agent.getPassword())) {
                    //S'il ne correspond pas
                    apiResponse.setResponseCode("01");
                    apiResponse.setResponseMessage("Wrong password");
                } else {
                    //S'il correspond on fait la modification
                    //userLoginRepository.changePassword(user.getId(), bCryptPasswordEncoder.encode(changePassword.getNewPassword()));
                    agent.setPassword(bCryptPasswordEncoder.encode(changePassword.getNewPassword()));
                    agent.setHasChangedPassword(true);
//                    userLoginRepository.save(user);
                    agentRepository.save(agent);

//                    RestTemplate restTemplate = new RestTemplate();
//                    restTemplate.getForObject("http://dstr.connectbind.com:8080/sendsms?username=kod-guage&password=KOD2019&type=0&dlr=1&destination=" + telephone + "&source=KODINET&message=KODINET\nvotre mot de passe a ete modifie ",String.class);

                    apiResponse.setResponseCode("00");
                    apiResponse.setResponseMessage("votre mot de passe a été modifié avec succès");
                }
            }else{
                //Nouveau mot de passe <> Confirmation
                apiResponse.setResponseCode("01");
                apiResponse.setResponseMessage("Le nouveau mot de passe ne correspond pas à la confirmation");
            }
        }
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/agent-get-id-old/{user_name}")
    public ResponseEntity<?>getpa_ol_id_agent(@PathVariable("user_name") String username){
        apiResponse.setResponseCode("00");
        apiResponse.setData(agentRepository.getId_Old(username));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


}
