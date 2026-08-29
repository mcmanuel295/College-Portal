package com.mcmanuel.domain.grade;

import com.mcmanuel.exception.InvalidScoreException;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID gradeId;

    @Column(unique = true,nullable = false)
    private String matriculationNumber;

    @Column(nullable = false)
    private double score;

    @Min(value = 0)
    @Max(value = 100)
    @Column(nullable = false)
    private char scoreGrade;

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

    public void setScore(double score) {
        this.score = score;
        this.scoreGrade =setScoreGrade();
    }

    private char setScoreGrade() {
        return grading(this.score);
    }
}
