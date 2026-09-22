package com.mcmanuel.web;

import com.mcmanuel.domain.student.RegisterRequest;
import com.mcmanuel.domain.student.StudentDto;
import com.mcmanuel.domain.student.StudentService;
import com.mcmanuel.pojo.LoginRequest;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.sqm.EntityTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final StudentService service;

    @Operation(description = "Login Endpoint",summary = "Student Login")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest){
        String jwtToken =service.login(loginRequest);
        if(jwtToken != null){
            return ResponseEntity.ok(jwtToken);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @Operation(description = "Register Student Endpoint",summary ="Register student" )
    @PostMapping("/")
    public ResponseEntity<StudentDto> registerStudent(@RequestParam String email, @RequestBody RegisterRequest request){
        try{
            return new ResponseEntity<>(service.registerStudent(email,request),HttpStatus.CREATED);
        }
        catch (EntityTypeException ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (Exception ex){
            log.error(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(description = "Activate Profile Endpoint",summary ="Activate Profile" )
    @PostMapping("/activate")
    public ResponseEntity<String> activateProfile(@RequestParam String email) throws MessagingException {
        try{
            service.sendUserEmail(email);
            return new ResponseEntity<>("Email Sent",HttpStatus.OK);
        }
        catch (Exception ex){
            return new ResponseEntity<>("Internal Error",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(description = "Verify OTP",summary = "Verify OTP")
    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestParam String email, @Valid String otp) throws MessagingException {
        if(service.verifyOtp(email,otp)){
            return new ResponseEntity<>("VERIFIED",HttpStatus.OK);
        }
        else return new ResponseEntity<>("Invalid OTP",HttpStatus.NOT_ACCEPTABLE);
    }
}
