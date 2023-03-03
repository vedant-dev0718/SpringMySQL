package spring.restapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.restapi.exception.ResourceNotFoundException;
import spring.restapi.model.Developer;
import spring.restapi.repository.DeveloperRepository;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/")
public class DeveloperController {

    @Autowired
    private DeveloperRepository developerRepository;

    @GetMapping("/dev")
    public List<Developer> getAllDevelopers(){
        return developerRepository.findAll();
    }

    @GetMapping("/dev/{id}")
    public ResponseEntity<Developer> getDeveloperById(@PathVariable(value = "id") Long devId) throws ResourceNotFoundException{
        Developer developer = developerRepository.findById(devId)
                .orElseThrow(()-> new ResourceNotFoundException("Developer could not find on::" + devId));
        return ResponseEntity.ok().body(developer);
    }

    @PostMapping("/dev")
    public Developer createDev(@Valid @RequestBody Developer developer){
        return developerRepository.save(developer);
    }

    @PutMapping("/dev/{id}")
    public ResponseEntity<Developer> updateDeveloper (
            @PathVariable(value = "id") Long devId,
            @Valid @RequestBody Developer developerDetails) throws ResourceNotFoundException{
        Developer developer = developerRepository.findById(devId)
                .orElseThrow(() -> new ResourceNotFoundException("Developer not found on:: " + devId));

        developer.setEmailId(developerDetails.getEmailId());
        developer.setLastName(developerDetails.getLastName());
        developer.setFirstName(developerDetails.getFirstName());
        developer.setCreateAt(developerDetails.getCreateAt());

        final Developer updateDev = developerRepository.save(developer);
        return ResponseEntity.ok(updateDev);
    }

    @DeleteMapping("/dev/{id}")
    public Map<String, Boolean> deleteDev(
            @PathVariable(value = "id") Long devId) throws Exception{
        Developer developer = developerRepository.findById(devId)
                .orElseThrow(()-> new ResourceNotFoundException("Developer cannot found on:: " + devId));

        developerRepository.delete(developer);
        Map<String, Boolean> response = new HashMap<>();
        response.put("delete", Boolean.TRUE);
        return response;
    }

}
