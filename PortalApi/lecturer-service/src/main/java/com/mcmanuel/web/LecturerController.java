package com.mcmanuel.web;

import com.mcmanuel.domain.lecturer.LecturerDto;
import com.mcmanuel.domain.lecturer.LecturerService;
import com.mcmanuel.pojo.RegisterRequest;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.sqm.EntityTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/lecturers")
@RequiredArgsConstructor
@Slf4j
public class LecturerController {
    private final LecturerService service;

    @PostMapping("/")
    public ResponseEntity<LecturerDto> registerLecturer(@RequestParam String email, @RequestBody RegisterRequest request){
        try{
            return new ResponseEntity<>(service.registerLecturer(email,request),HttpStatus.CREATED);
        }
        catch (EntityTypeException ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (Exception ex){
            log.error(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/")
    public ResponseEntity<List<LecturerDto>> findAllLecturer(@RequestParam(required = false,defaultValue = "0") int pageNo, @RequestParam(defaultValue = "10",required = false) int pageSize){
        return new ResponseEntity<>( service.getAllLecturers(pageNo,pageSize), HttpStatus.OK);
    }

    @PostMapping("/{staffNumber}")
    ResponseEntity<LecturerDto> findLecturerByStaffId(@PathVariable String staffNumber){
        LecturerDto lecturerDto =service.findLecturerByStaffNumber(staffNumber);

        if(lecturerDto!=null){
            return new ResponseEntity<>(lecturerDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping("/update")
    public ResponseEntity<LecturerDto> updateBio(@RequestParam String staffNumber,@RequestBody LecturerDto updatedDto){
        return new ResponseEntity<>(service.updateBio(staffNumber,updatedDto), HttpStatus.OK);
    }

    @DeleteMapping("/{staffNumber}")
    public ResponseEntity<Boolean> deleteLecturer(String staffNumber){
        return new ResponseEntity<>(service.deleteLecturer(staffNumber),HttpStatus.OK);
    }

    @PostMapping("/activate")
    public ResponseEntity<String> activateProfile(@RequestParam String email) throws jakarta.mail.MessagingException {
        service.sendUserEmail(email);
        return new ResponseEntity<>("activated",HttpStatus.OK);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestParam String email, @Valid String otp) throws MessagingException {
        if(service.verifyOtp(email,otp)){
            return new ResponseEntity<>("VERIFIED",HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }
}
