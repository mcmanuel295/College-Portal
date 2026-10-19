package com.mcmanuel.web;

import com.mcmanuel.domain.staff.lecturer.LecturerDto;
import com.mcmanuel.domain.staff.non_teaching.NonTeachingDto;
import com.mcmanuel.domain.staff.non_teaching.NonTeachingService;
import com.mcmanuel.pojo.RegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping
@Slf4j
public class NonTeachingController implements StaffController<NonTeachingDto> {
    private final NonTeachingService service;


    @Override
    public ResponseEntity<NonTeachingDto> registerStaff(String email, RegisterRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<List<NonTeachingDto>> findAllStaffs(int pageNo, int pageSize) {
        return null;
    }

    @Override
    public ResponseEntity<NonTeachingDto> findStaffByStaffId(String staffId) {
        return null;
    }

    @Override
    public ResponseEntity<NonTeachingDto> findStaffByStaffByEmail(String staffId) {
        return null;
    }

    @Override
    public ResponseEntity<NonTeachingDto> updateBio(String staffId, String email, String phoneNumber) {
        return null;
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(description = "Make an Admin",summary ="Make an Admin" )
    @Override
    @PostMapping("/{staffId}/make-admin")
    public ResponseEntity<String> makeAdmin(@PathVariable String staffId){
        String admin =service.makeAdmin(staffId);

        if(admin!=null){
            return new ResponseEntity<>(admin, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @Override
    public ResponseEntity<String> removeAdmin(String staffId) {
        return null;
    }

    @Override
    public ResponseEntity<Boolean> deleteStaff(String staffId) {
        return null;
    }

    @Override
    public ResponseEntity<String> activateProfile(String email) throws MessagingException {
        return null;
    }

    @Override
    public ResponseEntity<String> verifyOtp(String email, String otp) throws MessagingException {
        return null;
    }

    @Override
    public ResponseEntity<String> sendUserEmail(String email) throws MessagingException {
        return null;
    }
}
