package com.mcmanuel.domain.student;

import com.mcmanuel.domain.result.Result;
import com.mcmanuel.pojo.Course;
import com.mcmanuel.pojo.LoginRequest;
import com.mcmanuel.pojo.Notification;
import jakarta.mail.MessagingException;

import java.util.List;
import java.util.Set;

public interface StudentService {
    StudentDto registerStudent(String email, RegisterRequest request);
    StudentDto getStudentByMatricNumber(String MatricNumber);
    StudentDto getStudentByEmail(String email);

    List<StudentDto> getAllStudents( int pageNo,int pageSize);
    List<String> getAllStudentsByCourse(String courseCode, int pageNo,int pageSize);
    List<String> getAllStudentsByDepartment(String department, int pageNo,int pageSize);

    StudentDto updateBio(String matricNumber,String email,String phoneNumber);
    boolean deleteStudent(String matricNumber);

    void sendUserEmail(String email) throws MessagingException;
    boolean verifyOtp(String email,String otp);

    Set<String> registerCourses(String matricNumber, Set<Course> courseSet);
    Set<String> viewRegisteredCourses(String matricNumber);
//    boolean viewResult(String matricNumber);
    StudentDto getStudentProfile(String matricNumber);
    Result getResult(String matriculationNumber, String semester);
    List<Notification> getNotifications(String matriculationNumber);
    String getCGPA(String matriculationNumber);
    String getGPA(String matriculationNumber);

    List<String> matriculationNumberList(String department);

    String login(LoginRequest loginRequest);
}
