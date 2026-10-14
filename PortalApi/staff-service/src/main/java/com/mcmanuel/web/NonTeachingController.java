package com.mcmanuel.web;

import com.mcmanuel.domain.staff.non_teaching.NonTeachingDto;
import com.mcmanuel.domain.staff.non_teaching.NonTeachingService;
import com.mcmanuel.pojo.RegisterRequest;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<NonTeachingDto> findStaffByStaffId(String staffNumber) {
        return null;
    }

    @Override
    public ResponseEntity<NonTeachingDto> findStaffByStaffByEmail(String staffNumber) {
        return null;
    }

    @Override
    public ResponseEntity<NonTeachingDto> updateBio(String staffNumber, String email, String phoneNumber) {
        return null;
    }

    @Override
    public ResponseEntity<Boolean> deleteStaff(String staffNumber) {
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
