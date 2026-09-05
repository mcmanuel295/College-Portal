package com.mcmanuel.domain.student;

import com.mcmanuel.client.CourseClient;
import com.mcmanuel.exception.InvalidRoutingKeyException;
import com.mcmanuel.exception.StudentNotFoundException;
import com.mcmanuel.exception.UnknownCategoryHeaderException;
import com.mcmanuel.pojo.Grade;
import com.mcmanuel.pojo.Notification;
import com.mcmanuel.pojo.QueuePayLoad;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageHandlingService {
    private final StudentRepository studentRepo;
    private final CourseClient courseClient;


    @KafkaListener(topics = "{}",groupId = "${}")
    public QueuePayLoad handleIncomingNotification(String messageBody) {

        String key =jj ;//amqpMessage.getMessageProperties().getReceivedRoutingKey();

        if( key== null){
            throw new RuntimeException("Key is null");
        }

        String routingKey = key.toLowerCase();
        log.info("Processing notification received from routing key: {}", routingKey);

        String[] keyParts = routingKey.split("\\.");
        if (keyParts.length < 2) {
            log.error("Invalid routing key format received: {}", routingKey);
            throw new InvalidRoutingKeyException("Invalid routing key");
        }

        String targetType = keyParts[0];
        String targetValue = keyParts[1];

        List<String> targetStudentMatricNumbers = switch (targetType) {
            case "course" -> {
                log.info("Fetching students actively taking course: {}", targetValue);


                yield courseClient.getCourseStudents(targetValue);
            }
            case "department" -> {
                log.info("Fetching students belonging to department: {}", targetValue);
                yield studentRepo.findAllByDepartment(targetValue)
                        .stream().map(Student::getMatriculationNumber).toList();
            }
            case "school" -> {
                log.info("Broadcasting message across entire school registry");
                yield studentRepo.findAll()
                        .stream().map(Student::getMatriculationNumber).toList();
            }
            default -> {
                log.warn("Unknown targeting category header: {}", targetType);
                throw new UnknownCategoryHeaderException("Unknown targeting category header");
            }
        };

              return new QueuePayLoad(messageBody,targetStudentMatricNumbers);
    }



    @KafkaListener()
    public QueuePayLoad handleIncomingResult(Grade messageBody) {

        String key = "";//amqpMessage.getMessageProperties().getReceivedRoutingKey();
        if( key== null){
            throw new RuntimeException("Key is null");
        }

        String routingKey = key.toLowerCase();
        log.info("Processing message received from routing key: {}", routingKey);

        String[] keyParts = routingKey.split("\\.");
        if (keyParts.length < 2) {
            log.error("Invalid routing key format for grade received: {}", routingKey);
            throw new InvalidRoutingKeyException("Invalid routing key");
        }

        String targetType = keyParts[0];
        String targetValue = keyParts[1];
        String targetStudentMatricNumbers;

        if(targetType.equalsIgnoreCase("student")){
            log.info("Fetching student matriculation number belonging to department: {}", targetValue);
            Student student = studentRepo.findByMatriculationNumber(targetValue).orElseThrow(()-> new StudentNotFoundException("Student with matric number "+targetType+" not found for grading"));
            targetStudentMatricNumbers =student.getMatriculationNumber();

        }
        else {
            log.warn("Unknown targeting category header for result: {}", targetType);
            throw new UnknownCategoryHeaderException("Unknown targeting category header");
        }

        return new QueuePayLoad(messageBody,List.of(targetStudentMatricNumbers));
    }
}
