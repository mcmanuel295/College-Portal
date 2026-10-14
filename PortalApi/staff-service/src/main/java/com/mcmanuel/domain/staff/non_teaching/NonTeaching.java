package com.mcmanuel.domain.staff.non_teaching;

import com.mcmanuel.domain.staff.Staff;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@SuperBuilder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class NonTeaching extends Staff {
    @Id
    @UuidGenerator
    private UUID nonTeachingId;
}
