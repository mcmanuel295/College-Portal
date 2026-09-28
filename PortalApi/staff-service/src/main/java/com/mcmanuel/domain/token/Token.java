package com.mcmanuel.domain.token;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID tokenId;

    @Column(unique = true, nullable = false, length = 6)
    private String token;

    @Column(nullable = false)
    private String email;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt ;

    @Column(nullable = false)
    private LocalDateTime expiredAt ;

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        setExpiredAt(createdAt.plusMinutes(5));
    }

    public boolean isExpired(){
        return LocalDateTime.now().isAfter(expiredAt);
    }

}
