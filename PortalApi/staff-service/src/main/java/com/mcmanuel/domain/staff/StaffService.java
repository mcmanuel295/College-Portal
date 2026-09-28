package com.mcmanuel.domain.staff;

import com.mcmanuel.pojo.RegisterRequest;
import org.springframework.messaging.MessagingException;

import java.util.List;

public interface StaffService<T> {
     T registerStaff(String email, RegisterRequest request);
     T findStaffByStaffNumber(String staffNumber);
     T findStaffByEmail(String email);
     List<T> getAllStaffs(int pageNo, int pageSize);
     T updateBio(String staffNumber,String email,String phoneNumber);
    boolean deleteStaff(String staffNumber);
    void sendUserEmail(String email) throws MessagingException, jakarta.mail.MessagingException;
    boolean verifyOtp(String email,String otp);
    List<String> getStaffList(String department);
}
