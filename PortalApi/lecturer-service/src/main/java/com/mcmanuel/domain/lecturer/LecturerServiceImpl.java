package com.mcmanuel.domain.lecturer;

import com.mcmanuel.client.CourseClient;
import com.mcmanuel.enums.Department;
import com.mcmanuel.pojo.Grade;
import com.mcmanuel.pojo.RegisterRequest;
import com.mcmanuel.domain.token.TokenService;
import com.mcmanuel.email.EmailService;
import com.mcmanuel.enums.Role;
import com.mcmanuel.exception.LecturerNotFoundException;
import com.mcmanuel.exception.DepartmentNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class LecturerServiceImpl implements LecturerService{
    private final LecturerRepository lecturerRepo;
    private final EmailService emailService;
    private final TokenService tokenService;
    private CourseClient courseClient;
    @Override
    public LecturerDto registerLecturer(String email, RegisterRequest request) {
        Lecturer lecturer = Lecturer.builder()
                .staffNumber(generateStaffId(request.getDepartment().name()))
                .email(email)
                .phoneNumber(request.getPhoneNumber())
                .department(request.getDepartment())
                .dateCreated(LocalDateTime.now())
                .role(Role.LECTURER)
                .department(request.getDepartment())
                .imageUrl(request.getFile().getOriginalFilename())
                .build();
        lecturer.setFirstname(request.getFirstname());
        lecturer.setLastname(request.getLastname());
        return Mapper.toDto(lecturerRepo.save(lecturer));
    }

    private String generateStaffId(String department) {
//        CSC0805008
        if ( Arrays.stream(Department.values()).noneMatch(dept ->
                dept.name().equalsIgnoreCase(department))) {
            throw new DepartmentNotFoundException("Department "+department+" Not Found");
        }

        Department verifiedDepartment = Department.valueOf(department);
        String end ;

        int last_index = Integer.parseInt( getLecturerList(department).getLast().substring(7));
        if(last_index <=9) {
            end = "00"+(last_index+1);
        }
        else if (last_index <=99) {
            end = "0"+(last_index+1);
        }
        else end = String.valueOf(last_index+1);

        return verifiedDepartment.getCode()+verifiedDepartment.getFaculty().getCode()+verifiedDepartment.getCode()+end;
    }


    @Override
    public LecturerDto findLecturerByStaffNumber(String staffNumber) {
        return Mapper.toDto(
                lecturerRepo.findByStaffNumber(staffNumber).orElseThrow(()-> new LecturerNotFoundException("Lecturer Not Found"))
        );
    }

    @Override
    public List<LecturerDto> getAllLecturers(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize, Sort.by("staffId"));
        return lecturerRepo.findAll(pageable).stream()
                .map(Mapper::toDto)
                .toList();
    }

    @Override
    public LecturerDto updateBio(String staffNumber, LecturerDto updatedDto) {
        Lecturer lecturer= lecturerRepo.findByStaffNumber(staffNumber).orElseThrow(()-> new LecturerNotFoundException("Lecturer Not Found"));
        Lecturer updatedLecturer= Mapper.toLecturer(updatedDto);
        updatedLecturer.setLecturerId(lecturer.getLecturerId());
        return Mapper.toDto(updatedLecturer);
    }

    @Override
    public boolean deleteLecturer(String staffNumber) {
        Lecturer lecturer =lecturerRepo.findByStaffNumber(staffNumber).orElseThrow(()-> new LecturerNotFoundException("Lecturer Not Found"));
        lecturerRepo.delete(lecturer);
        return true;
    }

    @Override
    public void sendUserEmail(String email) throws MessagingException, jakarta.mail.MessagingException {
        emailService.sendEmail(email,tokenService.generateToken(email).getToken());
    }

    @Override
    public boolean verifyOtp(String email,String otp) {
        return tokenService.verifyOtp(email,otp);
    }

    @Override
    public List<String> getLecturerList(String department) {

        if ( Arrays.stream(Department.values()).noneMatch(dept ->
                dept.name().equalsIgnoreCase(department))) {
            throw new DepartmentNotFoundException("Department "+department+" Not Found");
        }

        return lecturerRepo.findAll().stream()
                .filter(lecturer -> lecturer.getDepartment().equals(Department.valueOf(department)))
                .map(Lecturer::getStaffNumber)
                .toList();
    }

    @Override
    public List<String> getCourseStudents(String courseCode, int pageNo, int pageSize) {
        return courseClient.getCourseStudents(courseCode,0,10);
    }

    @Override
    public String sendNotification(String courseCode, String message) {
        return courseClient.sendNotification(courseCode,message);
    }

    @Override
    public String sendGrade(String courseCode, Grade grade) {
        return courseClient.sendGrade(courseCode,grade);
    }

    @Override
    public String gradeStudents(String courseCode, Map<String, Double> grades) {
        return courseClient.gradeStudents(courseCode,grades);
    }


}
