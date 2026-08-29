package com.mcmanuel.pojo;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QueuePayLoad  {
    private Object payLoad ;
    private List<String> matricNumberList;

}
