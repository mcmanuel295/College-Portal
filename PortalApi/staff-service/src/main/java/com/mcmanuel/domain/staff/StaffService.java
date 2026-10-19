package com.mcmanuel.domain.staff;

import com.mcmanuel.pojo.LoginRequest;
import com.mcmanuel.pojo.RegisterRequest;
import org.springframework.messaging.MessagingException;

import java.util.List;

public interface StaffService<T> {
    String login(LoginRequest loginRequest);
    T registerStaff(String email,  RegisterRequest request);
    T findStaffByStaffId(String staffId);
    T findStaffByEmail(String email);
    List<T> getAllStaffs(int pageNo, int pageSize);
    T updateBio(String staffId,String email,String phoneNumber);
    boolean deleteStaff(String staffId);
    void sendUserEmail(String email) throws MessagingException, jakarta.mail.MessagingException;
    boolean verifyOtp(String email,String otp);
    List<String> getStaffList(String department);

    String makeAdmin(String staffId);
    String removeAdmin(String staffId);
}
