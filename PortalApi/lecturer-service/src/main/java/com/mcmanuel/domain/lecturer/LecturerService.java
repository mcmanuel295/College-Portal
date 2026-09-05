package com.mcmanuel.domain.lecturer;

import com.mcmanuel.pojo.Grade;
import com.mcmanuel.pojo.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessagingException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

public interface LecturerService {

    LecturerDto registerLecturer(String email, RegisterRequest request);
    LecturerDto findLecturerByStaffNumber(String staffNumber);
    List<LecturerDto> getAllLecturers(int pageNo, int pageSize);
    LecturerDto updateBio(String staffNumber,LecturerDto updatedDto);
    boolean deleteLecturer(String staffNumber);
    void sendUserEmail(String email) throws MessagingException, jakarta.mail.MessagingException;
    boolean verifyOtp(String email,String otp);
    List<String> getLecturerList(String department);

    //Course operation

    List<String> getCourseStudents(String courseCode, int pageNo, int pageSize);
    String sendNotification(String courseCode,String message);
    String sendGrade(String courseCode, Grade grade);
    String gradeStudents(String courseCode, Map<String,Double> grades);
}

