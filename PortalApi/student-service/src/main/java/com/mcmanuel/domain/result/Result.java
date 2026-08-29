package com.mcmanuel.domain.result;

import com.mcmanuel.pojo.Grade;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID resultId;

    private String semester;

    private String studentMatriculationNumber;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "result_grades", joinColumns = @JoinColumn(name = "result_id"))
    private List<Grade> grades;
}
