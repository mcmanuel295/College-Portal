package com.mcmanuel.domain.staff.lecturer;

import com.mcmanuel.domain.staff.StaffService;
import com.mcmanuel.domain.staff.StaffServiceImpl;
import com.mcmanuel.pojo.Grade;
import com.mcmanuel.pojo.LecturerRegisterRequest;
import com.mcmanuel.pojo.RegisterRequest;
import org.springframework.messaging.MessagingException;

import java.util.List;
import java.util.Map;

public interface LecturerService extends StaffService<LecturerDto, LecturerRegisterRequest> {

    @Override
    LecturerDto registerStaff(String email, LecturerRegisterRequest request);

    @Override
    LecturerDto findStaffByStaffNumber(String staffNumber);

    @Override
    LecturerDto findStaffByEmail(String email);

    @Override
    List<LecturerDto> getAllStaffs(int pageNo, int pageSize);

    @Override
    LecturerDto updateBio(String staffNumber,String email,String phoneNumber);

    @Override
    boolean deleteStaff(String staffNumber);

    @Override
    void sendUserEmail(String email) throws MessagingException, jakarta.mail.MessagingException;

    @Override
    boolean verifyOtp(String email,String otp);

    @Override
    List<String> getStaffList(String department);

    //Course operation

    List<String> getCourseStudents(String courseCode, int pageNo, int pageSize);
    String sendNotification(String courseCode,String message);
    String sendGrade(String courseCode, Grade grade);
    String gradeStudents(String courseCode, Map<String,Double> grades);
}

