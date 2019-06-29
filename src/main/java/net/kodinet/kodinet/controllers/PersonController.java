package net.kodinet.kodinet.controllers;

import com.machinezoo.sourceafis.FingerprintMatcher;
import com.machinezoo.sourceafis.FingerprintTemplate;
import net.kodinet.kodinet.constants.ConstantsVariables;
import net.kodinet.kodinet.entities.Agent;
import net.kodinet.kodinet.entities.Person;
import net.kodinet.kodinet.models.ApiResponse;
import net.kodinet.kodinet.models.FingerprintObject;
import net.kodinet.kodinet.repositories.AgentRepository;
import net.kodinet.kodinet.repositories.PersonRepository;
import net.kodinet.kodinet.utils.GenerateRandomStuff;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;

@RestController
@RequestMapping("/persons")
@CrossOrigin(origins = "*")
public class PersonController {

    @Autowired
    PersonRepository personRepository;
    @Autowired
    AgentRepository agentRepository;

    ApiResponse apiResponse = new ApiResponse();

    @PostMapping("/create/{agentId}")
    public ResponseEntity<?> create(@RequestBody Person person, @PathVariable Long agentId) throws ParseException, UnsupportedEncodingException {


        person.setBdnId(GenerateRandomStuff.getRandomString(10));
        Agent agent = agentRepository.getOne(agentId);
        person.setCreatedBy(agent);
        if (person.getBirthday()!=null){
            person.setDob(new SimpleDateFormat("dd/MM/yyyy").parse(person.getBirthday()));
        }
        if (person.getFPrint()!=null){
            byte [] bytes =Base64.decodeBase64(new String(person.getFPrint()).getBytes("UTF-8"));
            person.setFingerprint(bytes);
        }
        Person person1 = personRepository.save(person);
        apiResponse.setResponseCode(ConstantsVariables.successCode);
        apiResponse.setResponseMessage("user registered");
        apiResponse.setData(person1);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/read-all")
    public ResponseEntity<?>read(@RequestParam int page,@RequestParam int size){
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC,"id"));
        Pageable pageable = new PageRequest(page,size,sort);
        Collection<Person>people = personRepository.findAll(pageable).getContent();
        apiResponse.setResponseCode(ConstantsVariables.successCode);
        apiResponse.setResponseMessage("data found");
        apiResponse.setData(people);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/read-one/{id}")
    public ResponseEntity<?>readOne(@PathVariable Long id){
        Person person = personRepository.getOne(id);
        apiResponse.setResponseCode(ConstantsVariables.successCode);
        apiResponse.setResponseMessage("data found");
        apiResponse.setData(person);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/read-by-input/{input}")
    public ResponseEntity<?>readByInput(@PathVariable String input){

        Person person = personRepository.findByBdnIdOrNationalIdOrPhoneOrRfid(input,input,input,input);
        if (person!=null){
            apiResponse.setResponseCode(ConstantsVariables.successCode);
            apiResponse.setResponseMessage("data found");
            apiResponse.setData(person);
        }else{
            apiResponse.setResponseCode(ConstantsVariables.errorCode);
            apiResponse.setResponseMessage("data not found");
            apiResponse.setData(null);

        }
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?>update(Person person,@PathVariable Long id){

        try
        {
            Person existingPerson = personRepository.getOne(id);
            existingPerson.setNationalId(person.getNationalId());
            existingPerson.setCompanyName(person.getCompanyName());
            existingPerson.setEmail(person.getEmail());
            existingPerson.setInitials(person.getInitials());
            existingPerson.setFirstName(person.getFirstName());
            existingPerson.setMiddleName(person.getMiddleName());
            existingPerson.setLastName(person.getLastName());
            personRepository.save(existingPerson);
            apiResponse.setResponseCode(ConstantsVariables.successCode);
            apiResponse.setResponseMessage("data updated");
            apiResponse.setData(existingPerson);
        }catch (Exception e){
            apiResponse.setResponseCode(ConstantsVariables.errorCode);
            apiResponse.setResponseMessage("Error "+e.getMessage());
        }

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?>delete(@PathVariable Long id){

        if (personRepository.getOne(id)!=null){
            personRepository.deleteById(id);
            apiResponse.setResponseCode("00");
            apiResponse.setResponseMessage("Data deleted");
        }else {
            personRepository.deleteById(id);
            apiResponse.setResponseCode("01");
            apiResponse.setResponseMessage("Data not found");
        }

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/fingerprints/save")
    public ResponseEntity<?>fingerPrintSaving(@RequestBody FingerprintObject fingerprintObject
                                              ) throws UnsupportedEncodingException {
        String personId = fingerprintObject.getPersonId();
        Person person = personRepository.
                findByBdnIdOrNationalIdOrPhoneOrRfid
                        (personId,personId,personId,personId);
        if (person!=null){
            byte [] bytes = Base64.decodeBase64(new String(fingerprintObject.getFingerprint()).getBytes("UTF-8"));
            person.setFingerprint(bytes);
        }

        personRepository.save(person);

        apiResponse.setResponseCode("00");
        apiResponse.setResponseMessage("Fingerprint saved");
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/fingerprints/validate")
    public ResponseEntity<?>validateFingerprint(@RequestBody FingerprintObject fingerprintObject) throws IOException {

        String personId = fingerprintObject.getPersonId();
        Person person = personRepository.findByBdnIdOrNationalIdOrPhoneOrRfid(personId,personId,personId,personId);
        byte[] probeImage = person.getFingerprint();
        byte[] candidateImage = Base64.decodeBase64(new String(fingerprintObject.getFingerprint()).getBytes("UTF-8"));
        FingerprintTemplate probe = new FingerprintTemplate()
                .dpi(500)
                .create(probeImage);
        FingerprintTemplate candidate = new FingerprintTemplate()
                .dpi(500)
                .create(candidateImage);
        double score = new FingerprintMatcher()
                .index(probe)
                .match(candidate);
        boolean matches = score >= 40;
        if (matches){
            apiResponse.setResponseCode("00");
            apiResponse.setResponseMessage("Fingerprint validation succesful");
        }else{
            apiResponse.setResponseCode("01");
            apiResponse.setResponseMessage("Fingerprint validation failed");
        }
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
