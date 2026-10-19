package com.mcmanuel.domain.staff.lecturer;

import com.mcmanuel.client.CourseClient;
import com.mcmanuel.domain.staff.JwtService;
import com.mcmanuel.domain.staff.Staff;
import com.mcmanuel.domain.staff.StaffServiceImpl;
import com.mcmanuel.enums.Department;
import com.mcmanuel.enums.Status;
import com.mcmanuel.pojo.Grade;
import com.mcmanuel.domain.token.TokenService;
import com.mcmanuel.email.EmailService;
import com.mcmanuel.enums.Role;
import com.mcmanuel.exception.StaffNotFoundException;
import com.mcmanuel.exception.DepartmentNotFoundException;
import com.mcmanuel.pojo.RegisterRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class LecturerServiceImpl extends StaffServiceImpl<LecturerDto> implements LecturerService {
    private final LecturerRepository lecturerRepo;
    private final CourseClient courseClient;
    private final PasswordEncoder passwordEncoder;

    public LecturerServiceImpl(LecturerRepository lecturerRepo, EmailService emailService, TokenService tokenService, CourseClient courseClient,
                               JwtService jwtService, AuthenticationManager manager, PasswordEncoder passwordEncoder) {
        super(jwtService, manager,emailService,tokenService);
        this.lecturerRepo = lecturerRepo;
        this.courseClient = courseClient;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public LecturerDto registerStaff(String email, RegisterRequest request) {
        if (!request.getCreatePassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Password mismatch");
        }
        if(request.getStatus()== null || (request.getStatus()!= Status.TEACHING && request.getStatus()!= Status.NON_TEACHING )){
            throw new RuntimeException("Empty or Invalid category: Either teaching or non-teaching");
        }

        Lecturer lecturer = Lecturer.builder()
                .staffId(generateStaffId(request.getDepartment().name()))
                .email(email)
                .roles(new ArrayList<>())
                .phoneNumber(request.getPhoneNumber())
                .dateCreated(LocalDateTime.now())
                .department(request.getDepartment())
//                .imageUrl(request.getFile().getOriginalFilename())
                .build();

        if (request.getStatus() == Status.TEACHING){
            lecturer.setRoles(List.of(Role.LECTURER));
        }

        lecturer.setFirstname(request.getFirstname());
        lecturer.setLastname(request.getLastname());
        lecturer.setPassword(passwordEncoder.encode(request.getConfirmPassword()));
        lecturer.setEnabled(true);
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

        getStaffList(department).forEach(System.out::println);
        int last_index = Integer.parseInt(getStaffList(department).isEmpty()?"00": getStaffList(department).getLast().substring(7));
        if(last_index <=9) {
            end = "00"+(last_index+1);
        }
        else if (last_index <=99) {
            end = "0"+(last_index+1);
        }
        else end = String.valueOf(last_index+1);

        return verifiedDepartment.getCode()+verifiedDepartment.getFaculty().getCode()+verifiedDepartment.getNumber()+end;
    }


    @Override
    public LecturerDto findStaffByStaffId(String staffNumber) {
        return Mapper.toDto(
                (Lecturer) lecturerRepo.findByStaffId(staffNumber).orElseThrow(()-> new StaffNotFoundException("Lecturer Not Found"))
        );
    }


    @Override
    public LecturerDto findStaffByEmail(String email) {
        return Mapper.toDto(
                (Lecturer) lecturerRepo.findByEmail(email).orElseThrow(()-> new StaffNotFoundException("Lecturer Not Found"))
        );
    }

    @Override
    public List<LecturerDto> getAllStaffs(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize, Sort.by("staffId"));
        return lecturerRepo.findAll(pageable).stream()
                .map(Mapper::toDto)
                .toList();
    }

    @Override
    public LecturerDto updateBio(String staffId, String email,String phoneNumber) {
        Lecturer lecturer= (Lecturer) lecturerRepo.findByStaffId(staffId).orElseThrow(()-> new StaffNotFoundException("Lecturer Not Found"));
        lecturer.setEmail(email);
        lecturer.setPhoneNumber(phoneNumber);
        return Mapper.toDto(lecturerRepo.save(lecturer));
    }

    @Override
    public boolean deleteStaff(String staffNumber) {
        Lecturer lecturer = (Lecturer) lecturerRepo.findByStaffId(staffNumber).orElseThrow(()-> new StaffNotFoundException("Lecturer Not Found"));
        lecturerRepo.delete(lecturer);
        return true;
    }

    @Override
    public List<String> getStaffList(String department) {

        if ( Arrays.stream(Department.values()).noneMatch(dept ->
                dept.name().equalsIgnoreCase(department))) {
            throw new DepartmentNotFoundException("Department "+department+" Not Found");
        }

        return lecturerRepo.findAll().stream()
                .filter(lecturer -> lecturer.getDepartment().equals(Department.valueOf(department)))
                .map(Staff::getStaffId)
                .toList();
    }

    @Override
    public String makeAdmin(String staffId) {
        Lecturer lecturer = (Lecturer) lecturerRepo.findByStaffId(staffId).orElseThrow(() -> new StaffNotFoundException("Lecturer with Id Not Found "+staffId));

        if (lecturer.getRoles().contains(Role.ADMIN)){
            return "Already an Admin";
        }
        lecturer.getRoles().add(Role.ADMIN);

        lecturerRepo.save(lecturer);
        return staffId+"'s role Updated to Admin";
    }

    @Override
    public String removeAdmin(String staffId) {
        Lecturer lecturer = (Lecturer) lecturerRepo.findByStaffId(staffId).orElseThrow(() -> new StaffNotFoundException("Lecturer with Id Not Found "+staffId));

        if (lecturer.getRoles().contains(Role.ADMIN)){
            return "Not an Admin";
        }
        lecturer.getRoles().add(Role.ADMIN);

        lecturerRepo.save(lecturer);
        return staffId+"'s role Updated to Admin";
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


//    @Override
//    public String login(LoginRequest loginRequest) {
//        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword());
//        Authentication authentication =manager.authenticate(token);
//        if(authentication.isAuthenticated()) {
//
//            String jwtToken = jwtService.generateToken(loginRequest.getEmail());
//            System.out.println("JWT Token :"+jwtToken);
//            return jwtToken;
//        }
//        else throw new RuntimeException("Invalid Login request");
//    }
}
