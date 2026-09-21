package com.mcmanuel.domain.course;

import com.mcmanuel.client.LecturerClient;
import com.mcmanuel.client.StudentClient;
import com.mcmanuel.configuration.ApplicationConfiguration;
import com.mcmanuel.domain.grade.Grade;
import com.mcmanuel.domain.grade.GradeRepository;
import com.mcmanuel.pojo.Student;
import com.mcmanuel.enums.Level;
import com.mcmanuel.exception.CourseNotFoundException;
import com.mcmanuel.pojo.Lecturer;
import jakarta.persistence.PrePersist;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;


@Service
@RequiredArgsConstructor
@Slf4j
public class CourseServiceImp implements CourseService {
    private final CourseRepository courseRepo;
    private final KafkaTemplate<String,Object> template;
    private final StudentClient studentClient;
    private final LecturerClient lecturerClient;
    private final GradeRepository gradeRepo;



    @Override
    public CourseDto createCourse(CourseRequest dto) {
        Course course=Course.builder()
                .unit(dto.unit())
                .level(dto.level())
                .build();
        course.setCourseCode(dto.courseCode());
        course.setCourseTitle(dto.courseTitle());

        return Mapper.toDto(courseRepo.save(course));
    }


    @Override
    public List<CourseDto> getAllCourses() {
        return courseRepo.findAll().stream().map(Mapper::toDto).toList();
    }

    @Override
    public CourseDto getCourseByTitle(String courseTitle) {
        return Mapper.toDto(
                courseRepo.findByCourseTitle(courseTitle.toUpperCase()).orElseThrow(()->new CourseNotFoundException("Course Not Found"))
        );
    }

    @Override
    public CourseDto getCourseByCode(String courseCode) {
        return Mapper.toDto(
                courseRepo.findByCourseCode(courseCode.toUpperCase()).orElseThrow(()-> new CourseNotFoundException("Course Not Found"))
        );
    }


    @Override
    public CourseDto updateCourse(String courseTitle, CourseRequest courseRequest) {
        Course course = courseRepo.findByCourseTitle(courseTitle.toUpperCase()).orElseThrow(()-> new CourseNotFoundException("Course Not Found"));

        course.setCourseTitle(courseRequest.courseTitle().toUpperCase());
        course.setCourseCode(courseRequest.courseCode().toUpperCase());
        course.setLevel(courseRequest.level());
        course.setUnit(courseRequest.unit());

        return Mapper.toDto(courseRepo.save(course));
    }

    @Override
    public boolean deleteCourseByTitle(String courseTitle) {
        Course course = courseRepo.findByCourseTitle(courseTitle.toUpperCase()).orElseThrow(()->new CourseNotFoundException("Course Not Found"));
        courseRepo.delete(course);
        return true;
    }

    @Override
    public boolean deleteCourseByCode(String courseCode) {
        Course course = courseRepo.findByCourseCode(courseCode.toUpperCase()).orElseThrow(()->new CourseNotFoundException("Course Not Found"));
        courseRepo.delete(course);
        return true;
    }

    @Override
    public List<String> getCourseStudents(String courseCode ) {
        Course course = courseRepo.findByCourseCode(courseCode.toUpperCase()).orElseThrow(()-> new CourseNotFoundException("Course Not Found"));
        return studentClient.getAllStudentsByCourse(course.getCourseCode()).stream().map(Student::getMatriculationNumber).toList();
    }


    @Override
    public String gradeStudents(String courseCode, Map<String,Double> grades){
        CourseDto dto = getCourseByCode(courseCode);

//        Prevent action if any Matriculation number String is invalid
        Optional<String> invalidMatricNumber = grades.keySet().stream()
                .filter(matricNumber -> !dto.studentList().contains(matricNumber))
                .findAny();
        if (invalidMatricNumber.isPresent()) {
            throw new RuntimeException("one or more invalid matriculation number");
        }

        dto.studentList()
                .forEach(matricNumber ->{
                    Grade grade = new Grade();
                    grade.setMatriculationNumber( grades.keySet().stream().filter(key -> key.equalsIgnoreCase(matricNumber)).toString());
                    grade.setScore(grades.get(matricNumber));
                    sendGrade(courseCode.toUpperCase(),grade);
                    gradeRepo.save(grade);
                });
        return "student graded";
    }

    @Override
    public  List<Lecturer> getAssignedLecturers(String courseCode){
        CourseDto dto= Mapper.toDto(courseRepo.findByCourseTitle(courseCode.toUpperCase()).orElseThrow(()->new CourseNotFoundException("Course Not Found")));
        return dto.assignedLecturers().stream()
                .map(lecturerClient::findLecturerByStaffId).toList();
    }

    @Override
    public String assignedLecturers(String courseCode,List<String> staffNumbers) {
        Optional<String> invalidStaffId = staffNumbers.stream()
                .filter(staffNumber -> lecturerClient.findLecturerByStaffId(staffNumber) == null)
                .findAny();
        if (invalidStaffId.isPresent()) {
            throw new RuntimeException("one or more invalid staff Id");
        }

        Course course= courseRepo.findByCourseTitle(courseCode.toUpperCase()).orElseThrow(()->new CourseNotFoundException("Course Not Found"));
        course.setAssignedLecturers(staffNumbers);
        courseRepo.save(course);
        return "lecturers assigned";
    }


  @Override
    public String unAssignedLecturers(String courseCode,List<String> staffNumbers) {
      Optional<String> invalidStaffId = staffNumbers.stream()
              .filter(staffNumber -> lecturerClient.findLecturerByStaffId(staffNumber) == null)
              .findAny();
      if (invalidStaffId.isPresent()) {
          throw new RuntimeException("one or more invalid staff Id");
      }
      Course course= courseRepo.findByCourseTitle(courseCode.toUpperCase()).orElseThrow(()->new CourseNotFoundException("Course Not Found"));

      if(course.getAssignedLecturers().stream().anyMatch((id)-> lecturerClient.findLecturerByStaffId(id) !=null) ){
         course.getAssignedLecturers().forEach((staffId) -> course.getAssignedLecturers().remove(staffId));
      }
      courseRepo.save(course);
      return "lecturers assigned";
    }

    @Override
    public void sendCourseNotification(String courseCode, String message) {
        CourseDto dto = getCourseByCode(courseCode);
        String key = "course/" + dto.courseCode();

        template.send("notification-topic", key, message);
        log.info("Notification published via key: {}", key);
    }


    @Override
    public void sendGrade(String courseCode,Grade grade) {

        CourseDto dto = getCourseByCode(courseCode);
         CompletableFuture<SendResult<String, Object>> future =template.send("grade-topic","student/"+dto.courseCode(),grade);

         future.whenComplete((result,error)->{
             if (error == null) {
                 log.error("Error sending {} grade {} ",courseCode,grade);
             }
             else {
                 log.info("sending {} grade {}",courseCode,grade);
             }
         });

        log.info("Grade sent for course {}",dto.courseCode());
    }
}
