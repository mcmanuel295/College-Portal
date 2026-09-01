package com.mcmanuel.web;

import com.mcmanuel.domain.result.Result;
import com.mcmanuel.pojo.Course;
import com.mcmanuel.domain.student.RegisterRequest;
import com.mcmanuel.domain.student.StudentDto;
import com.mcmanuel.domain.student.StudentService;
import com.mcmanuel.pojo.Notification;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.sqm.EntityTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/students")
@Slf4j
public class StudentController {
    private final StudentService service;


    @PostMapping("/")
    public ResponseEntity<StudentDto> registerStudent(@RequestParam String email, @RequestBody RegisterRequest request){
        try{
            return new ResponseEntity<>(service.registerStudent(email,request),HttpStatus.CREATED);
        }
        catch (EntityTypeException ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (Exception ex){
            log.error(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<StudentDto>> findAllStudents(@RequestParam(required = false, defaultValue = "0") int pageNo, @RequestParam(defaultValue = "10",required = false) int pageSize
    ){
        return new ResponseEntity<>(service.getAllStudents(pageNo,pageSize),HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<String>> findStudentsByCategory(@RequestParam (required =false) String courseCode,
                                                               @RequestParam (required =false) String department,
                                                               @RequestParam(required = false,defaultValue = "0") int pageNo,
                                                               @RequestParam(defaultValue = "10",required = false) int pageSize){

        if( (courseCode==null || courseCode.isEmpty()) && (department==null || department.isEmpty()) ){
            return new ResponseEntity<>( List.of("Invalid Input"),HttpStatus.BAD_REQUEST);
        }
        else {
            if(courseCode !=null && !courseCode.isEmpty()){
                return new ResponseEntity<>( service.getAllStudentsByCourse(courseCode,pageNo,pageSize),HttpStatus.OK);
            }
            else{
                List<String> list =service.getAllStudentsByDepartment(department,pageNo,pageSize) ;
                if (list == null) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
                return new ResponseEntity<>(list ,HttpStatus.OK);
            }
        }
    }


    @GetMapping("/search")
    ResponseEntity<StudentDto> findStudentByMatricNumber(@RequestParam(required = false) String matricNumber,
                                                         @RequestParam(required = false) String email){
        if( (matricNumber==null || matricNumber.isEmpty()) && (email==null || email.isEmpty()) ){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else{
            if(matricNumber!=null &&!matricNumber.isEmpty()){
                StudentDto studentDto=service.getStudentByMatricNumber(matricNumber);
                if(studentDto!=null){
                    return new ResponseEntity<>(studentDto, HttpStatus.OK);
                }
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            }
            else {
                StudentDto studentDto=service.getStudentByEmail(email);

                if(studentDto!=null){
                    return new ResponseEntity<>(studentDto, HttpStatus.OK);
                }
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            }
        }
    }


    @PutMapping("/update")
    public ResponseEntity<StudentDto> updateBio(@RequestParam String matricNumber,@RequestBody StudentDto studentDto){
        return new ResponseEntity<>(service.updateBio(matricNumber,studentDto), HttpStatus.OK);
    }

    @DeleteMapping("/{matricNumber}")
    public ResponseEntity<String> deleteStudent(@PathVariable String matricNumber) {
        boolean deleted =service.deleteStudent(matricNumber);
        if (!deleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }

    @PostMapping("/activate")
    public ResponseEntity<String> activateProfile(@RequestParam String email) throws MessagingException {
        try{
            service.sendUserEmail(email);
            return new ResponseEntity<>("Email Sent",HttpStatus.OK);
        }
        catch (Exception ex){
            return new ResponseEntity<>("Internal Error",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestParam String email, @Valid String otp) throws MessagingException {
        if(service.verifyOtp(email,otp)){
            return new ResponseEntity<>("VERIFIED",HttpStatus.OK);
        }
        else return new ResponseEntity<>("Invalid OTP",HttpStatus.NOT_ACCEPTABLE);
    }


    @PostMapping("/{matricNumber}/register-courses")
    public ResponseEntity<String> registerCourses(@PathVariable String matricNumber,@RequestBody Set<Course> courseSet){
        if(service.registerCourses(matricNumber,courseSet)){
            return new ResponseEntity<>("Registered",HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @PostMapping("/{matricNumber}/view-registration")
    public ResponseEntity<String> viewRegisteredCourses(@PathVariable String matricNumber){
        if(service.viewRegisteredCourses(matricNumber)){
            return new ResponseEntity<>("VERIFIED",HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

    }

//    @PostMapping("/view-result")
//    public ResponseEntity<String> viewReslut(String matricNumber){
//        if(service.viewResult( matricNumber)){
//            return new ResponseEntity<>("VERIFIED",HttpStatus.OK);
//        }
//        else return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
//    }

    @PostMapping("/{matricNumber}/student-profile")
    public ResponseEntity<String> getStudentProfile(@PathVariable String matricNumber){
        if(service.getStudentProfile(matricNumber)){
            return new ResponseEntity<>("VERIFIED",HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @GetMapping("/{department}")
    ResponseEntity<List<String>> matriculationNumberList(@PathVariable String department){
        List<String> matricList = service.matriculationNumberList(department);
        if (matricList == null) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(matricList,HttpStatus.OK);
    }

//    @GetMapping("/{matriculationNumber}/result")
//    ResponseEntity<Result> getResult(String matriculationNumber, String semester){
//        Result result  = service.getResult(matriculationNumber,semester);
//        if (result == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
//        }
//        return new ResponseEntity<>(result,HttpStatus.OK);
//    }

//    @GetMapping("/{matriculationNumber}/notification")
//    ResponseEntity<List<Notification>> getNotification(String matriculationNumber){
//        List<Notification> notifications = service.getNotifications(matriculationNumber);
//        if (notifications == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
//        }
//        return new ResponseEntity<>(notifications,HttpStatus.OK);
//    }

//    @GetMapping("/{matriculationNumber}/cgpa")
//    ResponseEntity<String> getCGPA(String matriculationNumber){
//        var cgpa =service.getCGPA(matriculationNumber);
//        if(cgpa != null ){
//            return new ResponseEntity<>(cgpa,HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

//    @GetMapping("/{matriculationNumber}/gpa")
//    ResponseEntity<String> getGPA(String matriculationNumber){
//        var gpa =service.getGPA(matriculationNumber);
//        if(gpa != null){
//            return new ResponseEntity<>(gpa,HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
}
