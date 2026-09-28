package com.mcmanuel.web;

import com.mcmanuel.pojo.RegisterRequest;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface StaffController <T>{
    ResponseEntity<T> registerStaff(@RequestParam String email, @RequestBody RegisterRequest request);
    ResponseEntity<List<T>> findAllStaffs(@RequestParam(required = false,defaultValue = "0") int pageNo, @RequestParam(defaultValue = "10",required = false) int pageSize);

    ResponseEntity<T> findStaffByStaffId(@PathVariable String staffNumber);

    ResponseEntity<T> updateBio(@RequestParam String staffNumber,@RequestParam String email,@RequestParam String phoneNumber);

    ResponseEntity<Boolean> deleteStaff(String staffNumber);

    ResponseEntity<String> activateProfile(@RequestParam String email) throws jakarta.mail.MessagingException ;

    ResponseEntity<String> verifyOtp(@RequestParam String email, @Valid String otp) throws MessagingException ;
}
