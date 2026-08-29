package com.mcmanuel.pojo;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Notification {
    private String matriculationNumber;
    private String message;
    private LocalDateTime timeReceived;

    public Notification(String message,String matricNumber){
        this.matriculationNumber=matricNumber;
        this.message= message;
        this.timeReceived = LocalDateTime.now();
    }

}
