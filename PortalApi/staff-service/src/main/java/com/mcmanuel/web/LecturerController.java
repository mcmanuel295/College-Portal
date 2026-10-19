package com.mcmanuel.web;

import com.mcmanuel.domain.staff.lecturer.LecturerDto;
import com.mcmanuel.domain.staff.lecturer.LecturerService;
import com.mcmanuel.pojo.Grade;
import com.mcmanuel.pojo.RegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.sqm.EntityTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/lecturers")
@RequiredArgsConstructor
@Slf4j
public class LecturerController implements StaffController<LecturerDto>{
    private final LecturerService service;

    @Override
    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description="Register Staff Endpoint",summary="Staff registration")
    public ResponseEntity<LecturerDto> registerStaff(@RequestParam String email, @RequestBody RegisterRequest request){
        try{
            return new ResponseEntity<>(service.registerStaff(email,request),HttpStatus.CREATED);
        }
        catch (EntityTypeException ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (Exception ex){
            log.error(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "Find  All Staff Endpoint",summary = "Find all staff")
    @Override
    @GetMapping("/")
    public ResponseEntity<List<LecturerDto>> findAllStaffs(@RequestParam(required = false,defaultValue = "0") int pageNo, @RequestParam(defaultValue = "10",required = false) int pageSize){
        return new ResponseEntity<>( service.getAllStaffs(pageNo,pageSize), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','LECTURER')")
    @Operation(description = "Find Staff By StaffId Endpoint",summary ="Find Staff By StaffId" )
    @Override
    @PostMapping("/{staffId}")
    public ResponseEntity<LecturerDto> findStaffByStaffId(@PathVariable String staffId){
        LecturerDto lecturerDto =service.findStaffByStaffId(staffId);

        if(lecturerDto!=null){
            return new ResponseEntity<>(lecturerDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(description = "Make an Admin",summary ="Make an Admin" )
    @Override
    @PostMapping("/{staffId}/make-admin")
    public ResponseEntity<String> makeAdmin(@PathVariable String staffId){
        String lecturerDto =service.makeAdmin(staffId);

        if(lecturerDto!=null){
            return new ResponseEntity<>(lecturerDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasAnyRole('ADMIN','LECTURER')")
    @Operation(description = "Find Staff By StaffId Endpoint",summary ="Find Staff By StaffId" )
    @Override
    @PostMapping("/{staffId}/remove-admin")
    public ResponseEntity<String> removeAdmin(@PathVariable String staffId){
        String admin =service.removeAdmin(staffId);

        if(admin!=null){
            return new ResponseEntity<>(admin, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasAnyRole('ADMIN','LECTURER')")
    @Override
    public ResponseEntity<LecturerDto> findStaffByStaffByEmail(String email) {
        LecturerDto lecturerDto =service.findStaffByEmail(email);

        if(lecturerDto!=null){
            return new ResponseEntity<>(lecturerDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @Operation(description = "Update Staff Bio Endpoint",summary = "Update staff bio")
    @Override
    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('ADMIN','LECTURER')")
    public ResponseEntity<LecturerDto> updateBio(@RequestParam String staffId,@RequestParam String email,@RequestParam String phoneNumber){
        return new ResponseEntity<>(service.updateBio(staffId,email,phoneNumber), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "Delete Staff Endpoint", summary = "Delete staff")
    @Override
    @DeleteMapping("/{staffId}")
    public ResponseEntity<Boolean> deleteStaff(String staffId){
        return new ResponseEntity<>(service.deleteStaff(staffId),HttpStatus.OK);
    }

    @Operation(description = "Activate Profile",summary = "Activate Profile")
    @Override
    @PostMapping("/activate")
    @PreAuthorize("hasAnyRole('ADMIN','LECTURER')")
    public ResponseEntity<String> activateProfile(@RequestParam String email) throws jakarta.mail.MessagingException {
        service.sendUserEmail(email);
        return new ResponseEntity<>("activated",HttpStatus.OK);
    }

    @Operation(description = "Verify Otp",summary = "verify Otp")
    @Override
    @PostMapping("/verify-otp")
    @PreAuthorize("hasAnyRole('ADMIN','LECTURER')")
    public ResponseEntity<String> verifyOtp(@RequestParam String email, @Valid String otp) throws MessagingException {
        if(service.verifyOtp(email,otp)){
            return new ResponseEntity<>("VERIFIED",HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN','LECTURER')")
    public ResponseEntity<String> sendUserEmail(String email) throws MessagingException {
        return null;
    }


    @PreAuthorize("hasAnyRole('ADMIN','LECTURER')")
    @Operation(description = "get Course Students",summary = "get Course Students")
    @GetMapping("/{courseCode}/students")
    public ResponseEntity<List<String>> getCourseStudents(@PathVariable String courseCode, @RequestParam(required = false, defaultValue = "0") int pageNo,@RequestParam(required = false,defaultValue = "10") int pageSize){
        List<String> studentList = service.getCourseStudents(courseCode,pageNo,pageSize);
        if(studentList !=null){
            return ResponseEntity.ok().body(studentList);
        }
        return ResponseEntity.notFound().build();
    }


    @PreAuthorize("hasAnyRole('ADMIN','LECTURER')")
    @Operation(description = "send Notification", summary = "send Notification")
    @PostMapping("/{courseCode}/send-notification")
    public ResponseEntity<String> sendNotification(@PathVariable String courseCode,@RequestParam String message){
        String mess = service.sendNotification(courseCode,message);
        if(mess !=null){
            return ResponseEntity.ok().body(mess);
        }
        return ResponseEntity.notFound().build();
    }


    @PreAuthorize("hasAnyRole('ADMIN','LECTURER')")
    @Operation(description = "send grade", summary = "send Grade")
    @PostMapping("/{courseCode}/send-grade")
    public ResponseEntity<String> sendGrade(String courseCode, Grade grade){
        String mess = service.sendGrade(courseCode,grade);
        if(mess !=null){
            return ResponseEntity.ok().body(mess);
        }
        return ResponseEntity.notFound().build();
    }


    @Operation()
    @PostMapping("/{courseCode}/grade")
    @PreAuthorize("hasAnyRole('ADMIN','LECTURER')")
    public ResponseEntity<String> gradeStudents(@PathVariable String courseCode, @RequestBody Map<String,Double> grades){
        String mess = service.gradeStudents(courseCode,grades);
        if(mess !=null){
            return ResponseEntity.ok().body(mess);
        }
        return ResponseEntity.notFound().build();
    }


}
