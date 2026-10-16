package com.mcmanuel.web;

import com.mcmanuel.domain.staff.Staff;
import com.mcmanuel.domain.staff.StaffService;
import com.mcmanuel.domain.staff.lecturer.LecturerDto;
import com.mcmanuel.domain.staff.lecturer.LecturerService;
import com.mcmanuel.domain.staff.non_teaching.NonTeachingDto;
import com.mcmanuel.domain.staff.non_teaching.NonTeachingService;
import com.mcmanuel.pojo.LecturerRegisterRequest;
import com.mcmanuel.pojo.LoginRequest;
import com.mcmanuel.pojo.NonTeachingRegisterRequest;
import com.mcmanuel.pojo.RegisterRequest;
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
    private final LecturerService lecturerService;
    private final NonTeachingService nonTeachingService;

    @Operation(description = "Login Endpoint",summary = "Staff Login")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest){
        String jwtToken =lecturerService.login(loginRequest);
        if(jwtToken != null){
            return ResponseEntity.ok(jwtToken);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @Operation(description = "Register staff Endpoint",summary ="Register non-teaching staff" )
    @PostMapping("/non-teaching")
    public ResponseEntity<NonTeachingDto> registerStaff(@RequestParam String email, @RequestBody NonTeachingRegisterRequest request){
        try{
            return new ResponseEntity<>(nonTeachingService.registerStaff(email,request),HttpStatus.CREATED);
        }
        catch (EntityTypeException ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (Exception ex){
            log.error(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(description = "Register staff Endpoint",summary ="Register staff" )
    @PostMapping("/lecturer")
    public ResponseEntity<LecturerDto> registerStaff(@RequestParam String email, @RequestBody LecturerRegisterRequest request){
        try{
            return new ResponseEntity<>(lecturerService.registerStaff(email,request),HttpStatus.CREATED);
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
            lecturerService.sendUserEmail(email);
            return new ResponseEntity<>("Email Sent",HttpStatus.OK);
        }
        catch (Exception ex){
            return new ResponseEntity<>("Internal Error",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(description = "Verify OTP",summary = "Verify OTP")
    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestParam String email, @Valid String otp) throws MessagingException {
        if(lecturerService.verifyOtp(email,otp)){
            return new ResponseEntity<>("VERIFIED",HttpStatus.OK);
        }
        else return new ResponseEntity<>("Invalid OTP",HttpStatus.NOT_ACCEPTABLE);
    }
}
