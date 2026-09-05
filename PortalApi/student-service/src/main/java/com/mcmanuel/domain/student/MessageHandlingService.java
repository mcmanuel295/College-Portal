package com.mcmanuel.domain.student;

import com.mcmanuel.client.CourseClient;
import com.mcmanuel.exception.StudentNotFoundException;
import com.mcmanuel.exception.UnknownCategoryHeaderException;
import com.mcmanuel.pojo.Grade;
import com.mcmanuel.pojo.QueuePayLoad;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageHandlingService {
    private final StudentRepository studentRepo;
    private final CourseClient courseClient;

    public QueuePayLoad handleIncomingNotification(String messageBody,String key) {
        if( key== null){
            throw new RuntimeException("Key is null");
        }

        String category;

        if (key.contains("course")){
            category = "course";
        }
        else if(key.contains("department")){
            category="department";
        }
        else category ="school";


//        Get the recipient matriculation numbers list
        List<String> targetStudentMatricNumbers = switch (category) {
            case "course" -> {
                String course = key.substring(key.indexOf("/")+1);

                log.info("Fetching students actively taking course: {}", course);
                yield courseClient.getCourseStudents(course);
            }

            case "department" -> {
                log.info("Fetching students belonging to department: {}", key);

                String department = key.substring(key.lastIndexOf("/")+1).toLowerCase();
                yield studentRepo.findAllByDepartment(department)
                        .stream().map(Student::getMatriculationNumber).toList();
            }

            case "school" -> {
                log.info("Broadcasting message across entire school registry");
                yield studentRepo.findAll()
                        .stream().map(Student::getMatriculationNumber).toList();
            }

            default -> throw new RuntimeException("Unexpected value: " + category);
        };

              return new QueuePayLoad(messageBody,targetStudentMatricNumbers);
    }


    public QueuePayLoad handleIncomingResult(Grade messageBody,String key) {
        if( key== null){
            throw new RuntimeException("Key is null");
        }

        String matricNumber ="";
        if (key.contains("student") ) {
            matricNumber = key.substring(key.lastIndexOf("/"));

            log.info("Fetching student matriculation number belonging to department: {}", matricNumber);
            Student student = studentRepo.findByMatriculationNumber(matricNumber).orElseThrow(()-> new StudentNotFoundException("Student with matric number not found for grading"));
            matricNumber =student.getMatriculationNumber();

        }
        else {
            log.warn("Unknown targeting category header for result: {}", matricNumber);
            throw new UnknownCategoryHeaderException("Unknown targeting category header");
        }

        return new QueuePayLoad(messageBody,List.of(matricNumber));
    }
}
