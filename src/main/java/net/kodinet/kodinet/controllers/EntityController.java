package net.kodinet.kodinet.controllers;

import net.kodinet.kodinet.entities.Entite;
import net.kodinet.kodinet.models.ApiResponse;
import net.kodinet.kodinet.repositories.EntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/entities")
public class EntityController {

    @Autowired
    EntityRepository entityRepository;
    ApiResponse apiResponse = new ApiResponse();

    @PostMapping("/create")
    public ResponseEntity<?>save(@RequestBody Entite entite){

        Entite entite1 = entityRepository.save(entite);
        apiResponse.setResponseCode("00");
        apiResponse.setResponseMessage("Entity saved");
        apiResponse.setData(entite1);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/read-all")
    public ResponseEntity<?>findAll(){
        apiResponse.setResponseCode("00");
        apiResponse.setData(entityRepository.findAll());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}

