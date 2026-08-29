package com.mcmanuel.domain.student;

import com.mcmanuel.client.CourseClient;
import com.mcmanuel.enums.Level;
import com.mcmanuel.domain.result.Result;
import com.mcmanuel.domain.result.ResultRepository;
import com.mcmanuel.email.EmailService;
import com.mcmanuel.enums.Department;
import com.mcmanuel.enums.Role;
import com.mcmanuel.exception.CourseNotRegisteredException;
import com.mcmanuel.exception.DepartmentNotFoundException;
import com.mcmanuel.exception.InvalidScoreException;
import com.mcmanuel.pojo.Course;
import com.mcmanuel.domain.token.TokenService;
import com.mcmanuel.exception.StudentNotFoundException;
import com.mcmanuel.pojo.Grade;
import com.mcmanuel.pojo.Notification;
//import com.mcmanuel.pojo.QueuePayLoad;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class StudentServiceImp implements StudentService {
    private final StudentRepository studentRepo;
    private final EmailService emailService;
    private final TokenService tokenService;
    private final ResultRepository resultRepo;
    private final CourseClient courseClient;
//    private final MessageHandlingService messageService;



    @Override
    public void sendUserEmail(String email) throws MessagingException {
        emailService.sendEmail(email,tokenService.generateToken(email).getToken());
    }


    @Override
    public boolean verifyOtp(String email,String otp) {
        return tokenService.verifyOtp(email,otp);
    }

    @Override
    public StudentDto registerStudent(@Valid String email,@Valid RegisterRequest request) {

        if (!request.getCreatePassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Password doesn't match");
        }
        Student student= Student.builder()
                .email(email)
                .faculty(request.getFaculty())
                .matriculationNumber(generateMatriculationNumber(request.getDepartment().name()))
                .phoneNumber(request.getPhoneNumber())
                .faculty(request.getFaculty())
                .department(request.getDepartment())
                .level(Level.LEVEL100)
                .role(Role.STUDENT)
                .CGPA(0.00)
                .GPA(0.00)
                .password(request.getConfirmPassword())
                .dateCreated(LocalDateTime.now())
//                .imageUrl(request.getFile().getOriginalFilename())
                .build();

                student.setFirstname(request.getFirstname());
                student.setLastname(request.getLastname());
                student.setLastname(request.getLastname());

        return Mapper.toDto(studentRepo.save(student));
    }

    private String generateMatriculationNumber(String department){
            String year;
        Department dept;
        if(Arrays.stream(Department.values()).anyMatch((each)->each.name().equalsIgnoreCase(department))){
            dept = Department.valueOf(department);
            year =  String.valueOf( Calendar.getInstance().get(Calendar.YEAR)).substring(2);

            List<String>list = matriculationNumberList(department);
            list.sort(String::compareTo);



            if (list.isEmpty()) {
                return year+dept.getFaculty().getCode()+dept.getCode()+"001";
            }
            String lastMatricNumber = list.getLast();
            int value =Integer.parseInt(lastMatricNumber.substring(6));
            value++ ;
            String end;

            if (value <= 9) {
                end="00"+value;
                return "170805"+end;
            }
            else if (value <= 99) {
                end="0"+value;
                return year+" "+dept.getFaculty().getCode()+dept.getCode()+end;
            }
            else{
S
                return year+" "+dept.getFaculty().getCode()+dept.getCode()+value;
            }
        }
        throw new DepartmentNotFoundException("Invalid department");
    }


    @Override
    public StudentDto getStudentByMatricNumber(String matricNumber) {
        return Mapper.toDto(
                studentRepo.findByMatriculationNumber(matricNumber).orElseThrow(()-> new StudentNotFoundException("Student with matriculation number "+matricNumber+" not found"))
        );
    }

    @Override
    public StudentDto getStudentByEmail(String email) {
        return Mapper.toDto(
                studentRepo.findByEmail(email).orElseThrow(()-> new StudentNotFoundException("Student with matriculation number "+email+" not found"))
        );
    }

    @Override
    public List<StudentDto> getAllStudents(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize, Sort.by("matriculationNumber"));
        return studentRepo.findAll(pageable).stream()
                .map(Mapper::toDto)
                .toList();
    }

    @Override
    public List<String> getAllStudentsByCourse(String courseCode, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize,Sort.by("matriculationNumber"));

        return studentRepo.findAll(pageable).stream()
                .filter(student -> student.getCourseCodes().stream()
                                .anyMatch(course -> course.equalsIgnoreCase(courseCode)))
                .map(Student::getMatriculationNumber).toList();
    }

    @Override
    public StudentDto updateBio(String matricNumber, StudentDto studentDto) {
        Student student =studentRepo.findByMatriculationNumber(matricNumber).orElseThrow(()-> new StudentNotFoundException("Student with matriculation number "+matricNumber+" not found"));

        Student dto = Mapper.toStudent(studentDto);
        dto.setStudentId(student.getStudentId());
        return Mapper.toDto(studentRepo.save(dto));
    }

    @Override
    public List<Notification> getNotifications(String matriculationNumber) {
        Student student=studentRepo.findByMatriculationNumber(matriculationNumber).orElseThrow(()-> new StudentNotFoundException("Student with matriculation number "+matriculationNumber+" not found"));
        student.getNotification().sort(Comparator.comparing(Notification::getTimeReceived));
        return student.getNotification();
    }

//    @Override
//    public Result getResult(String matriculationNumber,String semester) {
//        Student student =studentRepo.findByMatriculationNumber(matriculationNumber).orElseThrow(()-> new StudentNotFoundException("Student with matriculation number "+matriculationNumber+" not found"));
//        if (!semester.equalsIgnoreCase("first") && !semester.equalsIgnoreCase("second")) {
//            throw new RuntimeException("Invalid Semester");
//        }
//        Result result =Result.builder()
//                .semester(semester)
//                .grades(new ArrayList<>(student.getSemesterGrades()))
//                .studentMatriculationNumber(matriculationNumber)
//                .build();
//        return resultRepo.save(result);
//    }


    @Override
    public boolean deleteStudent(String matricNumber) {
        Student student =studentRepo.findByMatriculationNumber(matricNumber).orElseThrow(()-> new StudentNotFoundException("Student with matriculation number "+matricNumber+" not found"));
        studentRepo.delete(student);
        return true;
    }


    @Override
    public boolean registerCourses(String matricNumber, Set<Course> courseSet) throws StudentNotFoundException{
        StudentDto studentDto = getStudentByMatricNumber(matricNumber);
        courseSet.forEach((course)-> studentDto.courseCodes().add(course.getCourseCode()));
        return true;
    }


    @Override
    public boolean viewRegisteredCourses(String matricNumber) {
        return false;
    }

//    @Override
//    public boolean viewResult(String matricNumber) {
//        return false;
//    }

    @Override
    public boolean getStudentProfile(String matricNumber) {
        return false;
    }


    @KafkaListener(topics = "170805008",groupId = "group1")
    private void getResultFromQueue(@Payload Grade grade) {
        Result result;
        log.info("new result {}",grade);


        String incomingMatricNumber = grade.getMatriculationNumber() ;
        StudentDto dto = getStudentByMatricNumber(incomingMatricNumber);

        if(!dto.courseCodes().contains(grade.getCourseCode())){
            throw new CourseNotRegisteredException("The course was not registered");
        }

        dto.semesterGrades().add(grade);

        if (dto.semesterGrades().size() == dto.courseCodes().size()) {
            double gpa =computeResult(dto.matriculationNumber());
            studentRepo.saveGPA(dto.matriculationNumber(),gpa);
            result =new Result();
            result.setGrades(dto.semesterGrades().stream().toList());
            result.setStudentMatriculationNumber(dto.matriculationNumber());
            result.setSemester("first");

            resultRepo.save(result);
        }
    }

//    private void getNotificationFromTopic(String payload) {
//        log.info("new message {}",payload);
//        Student student =studentRepo.findByMatriculationNumber().orElseThrow(()-> new StudentNotFoundException("Student with matric number "++" not found"));
//        student.getNotification().add(new Notification(payload,));
//        log.info("notification added ");
//    }


    private double computeResult(String matricNumber) {
        var dto = getStudentByMatricNumber(matricNumber);
        double value;

        int totalUnit = dto.semesterGrades().stream().mapToInt(grade -> {
            Course course = courseClient.getCourseByCode(grade.getCourseCode());
            return course.getUnit()* gradeEquivalent(grade.getScoreGrade() );
        }).sum();

        int score = dto.semesterGrades().stream().mapToInt(each -> (int) each.getScore()).sum();

        value= (double) totalUnit /score;
        return value;
    }

/**
 * compute the integer grade equivalent for each course
 */

    private int gradeEquivalent(char grade){
        if (grade >'F'|| grade< 'A') {
            throw new InvalidScoreException("Invalid grade");
        }

        return switch (grade) {
            case 'A' -> 5;
            case 'B' -> 4;
            case 'C' -> 3;
            case 'D' -> 2;
            case 'E' -> 1;
            default -> 0;
        };
    }

//    @Override
//    public String getCGPA(String matriculationNumber) {
//        return getStudentByMatricNumber(matriculationNumber).CGPA()+"";
//    }
//
//    @Override
//    public String getGPA(String matriculationNumber) {
//        return getStudentByMatricNumber(matriculationNumber).GPA()+"";
//    }

    @Override
    public List<String> matriculationNumberList(String department) {
        if(Arrays.stream(Department.values()).anyMatch((one)-> one.name().equalsIgnoreCase(department))){
            return studentRepo.getMatriculationNumberList(Department.valueOf(department));
        }
        else throw new DepartmentNotFoundException("Department Not Found"+department);
    }


}
