package com.mcmanuel.pojo;

import com.mcmanuel.exception.InvalidScoreException;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
//@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Grade {
    private String matriculationNumber;
    private String courseCode;
    private double score;
    private char scoreGrade;

    public Grade(String matriculationNumber,String courseCode,double score){
        this.matriculationNumber = matriculationNumber;
        this.courseCode=courseCode;
        this.score= score;
        System.out.println("grade constructor");
    }



    public void setScore(double score) {
        this.score = score;
        this.scoreGrade =setScoreGrade();
    }

    private char setScoreGrade() {
        return grading(this.score);
    }

    public static char grading(double score){
        char grade;

        if (score < 0 || score > 100) {
            throw new InvalidScoreException("Invalid score");
        }

        if (score >=70){
            grade= 'A';
        }
        else if(score >60 && score < 69){
            grade= 'B';
        }
        else if(score >50 && score < 59){
            grade= 'C';
        }
        else if(score > 45 && score < 50){
            grade= 'D';
        }
        else if(score > 40 && score < 45){
            grade= 'E';
        }
        else grade= 'F';

        return grade;
    }

}
