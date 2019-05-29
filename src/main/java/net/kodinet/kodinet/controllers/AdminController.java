package net.kodinet.kodinet.controllers;

import net.kodinet.kodinet.entities.Admin;
import net.kodinet.kodinet.models.ApiResponse;
import net.kodinet.kodinet.models.LoginObject;
import net.kodinet.kodinet.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/admins")
public class AdminController {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    AdminRepository adminRepository;
    ApiResponse apiResponse = new ApiResponse();

    @PostMapping("/create")
    public ResponseEntity<?>create(@RequestBody Admin admin){
        admin.setPassword(bCryptPasswordEncoder.encode(admin.getPassword()));
        apiResponse.setResponseCode("00");
        apiResponse.setResponseMessage("Admin created");
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<?>findAll(){
        Collection<Admin>admins = adminRepository.findAll();
        apiResponse.setResponseCode("00");
        apiResponse.setData(admins);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?>login(@RequestBody LoginObject loginObject){

        Admin admin = adminRepository.findByUsername(loginObject.getUsername());
        if (admin==null){
            apiResponse.setResponseCode("01");
            apiResponse.setResponseMessage("Admin not found");
        }else{
            if (!bCryptPasswordEncoder.matches(loginObject.getPassword(), admin.getPassword())){
                apiResponse.setResponseCode("01");
                apiResponse.setResponseMessage("Wrong username or password");
            }else{
                apiResponse.setResponseCode("00");
                apiResponse.setResponseMessage("Login Succesful");
                apiResponse.setData(admin);
            }
        }
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
