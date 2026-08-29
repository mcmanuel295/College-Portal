package com.mcmanuel.domain.lecturer;

import com.mcmanuel.pojo.RegisterRequest;
import org.springframework.messaging.MessagingException;

import java.util.List;

public interface LecturerService {
    LecturerDto registerLecturer(String email, RegisterRequest request);
    LecturerDto findLecturerByStaffNumber(String staffNumber);
    List<LecturerDto> getAllLecturers(int pageNo, int pageSize);
    LecturerDto updateBio(String staffNumber,LecturerDto updatedDto);
    boolean deleteLecturer(String staffNumber);
    void sendUserEmail(String email) throws MessagingException, jakarta.mail.MessagingException;
    boolean verifyOtp(String email,String otp);
}
