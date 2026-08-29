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
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class CourseServiceImp implements CourseService {
    private final CourseRepository courseRepo;
    private final ApplicationConfiguration appConfig;
    private final KafkaTemplate<String,Object > template;
    private final StudentClient studentClient;
    private final LecturerClient lecturerClient;
    private final GradeRepository gradeRepo;

    @PrePersist
    private void init(){
        createCourse(
                new CourseDto(
                        "INTRODUCTION TO PROGRAMMING", "CSC 101", null, 3, Level.LEVEL100, null,null)
        );
    }

    @Override
    public CourseDto createCourse(CourseDto dto) {
        Course course=Course.builder()
                .unit(dto.unit())
                .level(dto.level())
                .build();
        course.setCourseCode(dto.courseCode());
        course.setCourseTitle(dto.courseTitle());

        return Mapper.toDto(courseRepo.save(course));
    }


    @Override
    public List<CourseDto> getAllCourses(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize, Sort.by("courseCode"));
        return courseRepo.findAll(pageable).stream().map(Mapper::toDto).toList();
    }

    @Override
    public CourseDto getCourseByTitle(String courseTitle) {
        return Mapper.toDto(
                courseRepo.findByCourseTitle(courseTitle).orElseThrow(()->new CourseNotFoundException("Course Not Found"))
        );
    }

    @Override
    public CourseDto getCourseByCode(String courseCode) {
        return Mapper.toDto(
                courseRepo.findByCourseCode(courseCode).orElseThrow(()-> new CourseNotFoundException("Course Not Found"))
        );
    }


    @Override
    public CourseDto updateCourse(String courseTitle, CourseDto updatedDto) {
        Course course = courseRepo.findByCourseTitle(courseTitle).orElseThrow(()-> new CourseNotFoundException("Course Not Found"));

        Course updatedCourse =Mapper.toCourse(updatedDto);
        updatedCourse.setCourseId(course.getCourseId());
        return Mapper.toDto(courseRepo.save(updatedCourse));
    }

    @Override
    public boolean deleteCourseByTitle(String courseTitle) {
        Course course = courseRepo.findByCourseTitle(courseTitle).orElseThrow(()->new CourseNotFoundException("Course Not Found"));
        courseRepo.delete(course);
        return true;
    }

    @Override
    public boolean deleteCourseByCode(String courseCode) {
        Course course = courseRepo.findByCourseCode(courseCode).orElseThrow(()->new CourseNotFoundException("Course Not Found"));
        courseRepo.delete(course);
        return true;
    }

    @Override
    public List<String> getCourseStudents(String courseCode,int pageNo, int pageSize) {
        Course course = courseRepo.findByCourseCode(courseCode).orElseThrow(()-> new CourseNotFoundException("Course Not Found"));
        return studentClient.getAllStudentsByCourse(course.getCourseCode(),pageNo,pageSize).stream().map(Student::getMatriculationNumber).toList();
    }


    @Override
    public String gradeStudents(String courseCode, Map<String,Double> grades){
        CourseDto dto = getCourseByCode(courseCode);
        List<String> studentMatricList =getCourseStudents(dto.courseCode(),0,dto.studentList().size());
        studentMatricList
                .forEach(matricNumber ->{
                    Grade grade = new Grade();
                    grade.setMatriculationNumber( grades.keySet().stream().filter(key -> key.equalsIgnoreCase(matricNumber)).toString());
                    grade.setScore(grades.get(matricNumber));
                    sendGrade(courseCode,grade);
                    gradeRepo.save(grade);
                });
        return "student graded";
    }

    @Override
    public  List<Lecturer> getAssignedLecturers(String courseCode){
        CourseDto dto= Mapper.toDto(courseRepo.findByCourseTitle(courseCode).orElseThrow(()->new CourseNotFoundException("Course Not Found")));
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

        CourseDto dto= Mapper.toDto(courseRepo.findByCourseTitle(courseCode).orElseThrow(()->new CourseNotFoundException("Course Not Found")));
        CourseDto.builder()
                .courseCode(dto.courseCode())
                .courseTitle(dto.courseTitle())
                .fullTitle(dto.fullTitle())
                .studentList(dto.studentList())
                .unit(dto.unit())
                .level(dto.level())
                .assignedLecturers(staffNumbers)
        .build();

        return "lecturers assigned";
    }

    @Override
    public void sendNotification(String courseCode, String message) {
        CourseDto dto = getCourseByCode(courseCode);
        String routingKey = "course." + dto.courseCode().replaceAll("\\s+", "").toLowerCase();

        template.send(appConfig.exchangeName(), routingKey, message);
        log.info("Notification published via routing key: {}", routingKey);
    }


    @Override
    public void sendGrade(String courseCode,Grade grade) {
        CourseDto dto = getCourseByCode(courseCode);
        template.send(appConfig.exchangeName(),"course."+dto.courseCode(),grade);
        log.info("notification sent for course {}",dto.courseCode());
    }
}
